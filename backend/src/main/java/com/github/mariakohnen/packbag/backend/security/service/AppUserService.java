package com.github.mariakohnen.packbag.backend.security.service;

import com.github.mariakohnen.packbag.backend.security.dto.AppUserLoginDto;
import com.github.mariakohnen.packbag.backend.security.model.AppUser;
import com.github.mariakohnen.packbag.backend.security.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createNewAppUser(AppUserLoginDto appUserLoginDto) throws IllegalArgumentException {
        AppUser newUser = new AppUser();
        if (appUserLoginDto.getUsername() == null || appUserLoginDto.getUsername().trim().equals("")) {
            throw new IllegalArgumentException("The name of the user is not given, please enter a valid name.");
        }
        if (appUserRepository.findByUsername(appUserLoginDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("The name already exist, please choose another one.");
        }
        if (!validatePassword(appUserLoginDto.getPassword())) {
            throw new IllegalArgumentException("The password is not valid, please enter a valid one.");
        }
        newUser.setUsername(appUserLoginDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(appUserLoginDto.getPassword()));
        appUserRepository.save(newUser);
    }

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
