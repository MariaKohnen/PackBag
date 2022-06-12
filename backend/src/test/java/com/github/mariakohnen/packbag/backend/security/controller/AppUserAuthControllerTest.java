package com.github.mariakohnen.packbag.backend.security.controller;

import com.github.mariakohnen.packbag.backend.security.dto.AppUserLoginDto;
import com.github.mariakohnen.packbag.backend.security.model.AppUser;
import com.github.mariakohnen.packbag.backend.security.repository.AppUserRepository;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
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

    @Test
    void postNewAppUser_whenUsernameAndPasswordAreValid_shouldReturnJwtToken() {
        //GIVEN
        AppUserLoginDto newUserDto = AppUserLoginDto.builder()
                .username("nameOfUser")
                .password("m)84n%5Bl")
                .build();
        //WHEN
        String jwt = webTestClient.post()
                .uri("/auth/registration")
                .bodyValue(newUserDto)
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
        assertEquals(expectedUsername, newUserDto.getUsername());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void postNewAppUser_whenUsernameIsNotValid_shouldThrowIllegalArgumentException(String input) {
        //GIVEN
        AppUserLoginDto newUserDto = AppUserLoginDto.builder()
                .username(input)
                .password("m)84n%5Bl")
                .build();
        //WHEN //THEN
        webTestClient.post()
                .uri("/auth/login/registration")
                .bodyValue(newUserDto)
                .exchange()
                .expectStatus().isEqualTo(404);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"Hk3b&8", "Hk(kDb&%)", "HK3B&8=(O_9", "hk3b&8=(0_9", "hk3b8OdKl", "hk/ 83&dKl"})
    void postNewAppUser_whenPasswordIsNotValid_shouldThrowIllegalArgumentException(String input) {
        //GIVEN
        AppUserLoginDto newUserDto = AppUserLoginDto.builder()
                .username("")
                .password("input")
                .build();
        //WHEN //THEN
        webTestClient.post()
                .uri("/auth/login/registration")
                .bodyValue(newUserDto)
                .exchange()
                .expectStatus().isEqualTo(404);
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