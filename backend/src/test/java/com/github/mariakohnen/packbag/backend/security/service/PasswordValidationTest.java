package com.github.mariakohnen.packbag.backend.security.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PasswordValidationTest {

    private final PasswordValidation passwordValidation = new PasswordValidation();

    @Test
    void validatePassword_whenPasswordIsValid_shouldReturnTrue() {
        //GIVEN
        String validPassword = "Hk3b&8=(O_9";
        //WHEN
        boolean actual = passwordValidation.validatePassword(validPassword);
        //THEN
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    void validatePassword_whenPasswordIsNull_shouldThrowException() {
        //WHEN //THEN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> passwordValidation.validatePassword(null));
        assertEquals("The password is not given, please enter a valid password.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hk3b&8", "Hk(kDb&%)", "HK3B&8=(O_9", "hk3b&8=(0_9", "hk3b8OdKl", "hk/ 83&dKl"})
    void validatePassword_whenPasswordIsToShort_shouldReturnFalse(String input) {
        //WHEN
        boolean actual = passwordValidation.validatePassword(input);
        //THEN
        boolean expected = false;
        assertEquals(expected, actual);
    }
}
