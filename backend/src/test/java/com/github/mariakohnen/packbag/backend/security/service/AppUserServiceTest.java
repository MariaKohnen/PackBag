package com.github.mariakohnen.packbag.backend.security.service;

import com.github.mariakohnen.packbag.backend.security.dto.AppUserLoginDto;
import com.github.mariakohnen.packbag.backend.security.model.AppUser;
import com.github.mariakohnen.packbag.backend.security.repository.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class AppUserServiceTest {

    private final AppUserRepository appUserRepository = mock(AppUserRepository.class);
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final PasswordValidation passwordValidation = mock(PasswordValidation.class);

    private final AppUserService appUserService = new AppUserService(appUserRepository, passwordEncoder, passwordValidation);

    @Test
    void createNewAppUser_whenUsernameAndPasswordIsValid_repoShouldContainUser() {
        //GIVEN
        AppUserLoginDto newUserDto = AppUserLoginDto.builder()
                .username("nameOfUser")
                .password("m)84n%5Bl")
                .build();
        //WHEN
        when(passwordEncoder.encode(newUserDto.getPassword())).thenReturn("$argon2id$m");
        when(passwordValidation.validatePassword(newUserDto.getPassword())).thenReturn(true);
        appUserService.createNewAppUser(newUserDto);
        //THEN
        verify(passwordEncoder).encode(newUserDto.getPassword());
        verify(appUserRepository).findByUsername("nameOfUser");
        verify(appUserRepository).save(AppUser.builder()
                .username(newUserDto.getUsername())
                .password("$argon2id$m")
                .build());
    }

    @Test
    void createNewAppUser_whenUsernameIsNull_shouldThrowIllegalArgumentException() {
        //GIVEN
        AppUserLoginDto userWithoutName = AppUserLoginDto.builder()
                .password("m)84n%5Bl")
                .build();
        //WHEN //THEN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> appUserService.createNewAppUser(userWithoutName));
        assertEquals("The name of the user is not given, please enter a valid name.", exception.getMessage());
    }

    @Test
    void createNewAppUser_whenUsernameIsEmtpy_shouldThrowIllegalArgumentException() {
        //GIVEN
        AppUserLoginDto userWithoutName = AppUserLoginDto.builder()
                .username("")
                .password("m)84n%5Bl")
                .build();
        //WHEN //THEN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> appUserService.createNewAppUser(userWithoutName));
        assertEquals("The name of the user is not given, please enter a valid name.", exception.getMessage());
    }

    @Test
    void createNewAppUser_whenUsernameAlreadyExist_shouldThrowIllegalArgumentException() {
        //GIVEN
        when(appUserRepository.findByUsername("existingUser")).thenReturn(Optional.ofNullable(AppUser.builder()
                .id("1")
                .username("existingUser")
                .password("geheim123")
                .build()));
        AppUserLoginDto newUserDto = AppUserLoginDto.builder()
                .username("existingUser")
                .password("m)84n%5Bl")
                .build();
        //WHEN //THEN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> appUserService.createNewAppUser(newUserDto));
        assertEquals("The name already exist, please choose another one.", exception.getMessage());
        verify(appUserRepository).findByUsername("existingUser");
    }

    @Test
    void createNewAppUser_whenPasswordIsNotValid_shouldThrowIllegalArgumentException() {
        //GIVEN
        AppUserLoginDto newUserDto = AppUserLoginDto.builder()
                .username("userName")
                .password("geheim123")
                .build();
        when(appUserRepository.findByUsername("existingUser")).thenReturn(Optional.empty());
        when(passwordValidation.validatePassword(newUserDto.getPassword())).thenReturn(false);
        //WHEN

        //THEN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> appUserService.createNewAppUser(newUserDto));
        assertEquals("The password is not valid, please enter a valid one.", exception.getMessage());
        verify(appUserRepository).findByUsername("userName");
        verify(passwordValidation).validatePassword("geheim123");
    }

}