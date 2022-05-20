package com.github.mariakohnen.packbag.backend.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

class UtilServiceTest {

    private final UtilService utilService = new UtilService();

    @Test
    void dateStringToInstant_whenDateAsStringIsValid_ShouldReturnDateAsInstant() {
        //GIVEN
        String dateAsString = "2022-10-02";
        //WHEN
        Instant dateAsInstant = utilService.dateStringToInstant(dateAsString);
        String actual = dateAsInstant.toString();
        //THEN
        String expected = "2022-10-01T22:00:00Z";
        Assertions.assertEquals(expected, actual);
    }
}