package com.github.mariakohnen.packbag.backend.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
public class UtilService {

    public Instant dateStringToInstant(String dateAsString) {
            LocalDate date = LocalDate.parse(dateAsString);
            return date.atStartOfDay(ZoneId.of("Europe/Paris")).toInstant();
    }
}
