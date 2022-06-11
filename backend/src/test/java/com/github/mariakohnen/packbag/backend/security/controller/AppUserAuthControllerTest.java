package com.github.mariakohnen.packbag.backend.security.controller;

import com.github.mariakohnen.packbag.backend.security.dto.AppUserLoginDto;
import com.github.mariakohnen.packbag.backend.security.model.AppUser;
import com.github.mariakohnen.packbag.backend.security.repository.AppUserRepository;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppUserAuthControllerTest {

    @Value("${packbag.app.jwt.secret}")
    private String jwtSecret;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    public void cleanUp() {
        appUserRepository.deleteAll();
    }

    @Test
    void login_whenCredentialsAreValid_shouldReturnValidJWT() {
        //GIVEN
        AppUser existingUser = createTestUserInRepoAndGet();
        //WHEN
        String jwt = webTestClient.post()
                .uri("/auth/login")
                .bodyValue(AppUserLoginDto.builder()
                        .username("nameOfUser")
                        .password("userPassword")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();
        //THEN
        String expectedUsername = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
        assertEquals(expectedUsername, existingUser.getUsername());

    }

    @Test
    void login_whenCredentialsAreNotValid_shouldReturnForbidden() {
        //GIVEN
        createTestUserInRepoAndGet();
        //WHEN //THEN
        webTestClient.post()
                .uri("/auth/login")
                .bodyValue(AppUserLoginDto.builder()
                        .username("nameOfUser")
                        .password("wrong-password")
                        .build())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.FORBIDDEN);
    }

    private AppUser createTestUserInRepoAndGet() {
        String hashedPassword = passwordEncoder.encode("userPassword");
        AppUser testUser = AppUser.builder()
                .id("111")
                .username("nameOfUser")
                .password(hashedPassword)
                .build();
        appUserRepository.save(testUser);
        return testUser;
    }
}