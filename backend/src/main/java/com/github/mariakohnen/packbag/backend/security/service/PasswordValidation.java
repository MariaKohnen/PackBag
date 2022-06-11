package com.github.mariakohnen.packbag.backend.security.service;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PasswordValidation {

    public boolean validatePassword(String userPassword) {
        String regex = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        Pattern p = Pattern.compile(regex);
        if (userPassword == null) {
            throw new IllegalArgumentException("The password is not given, please enter a valid password.");
        }
        Matcher m = p.matcher(userPassword);
        return m.matches();
    }
}
