package com.github.mariakohnen.packbag.backend.security.service;

import com.github.mariakohnen.packbag.backend.controller.status.NameAlreadyExistException;
import com.github.mariakohnen.packbag.backend.security.dto.AppUserLoginDto;
import com.github.mariakohnen.packbag.backend.security.model.AppUser;
import com.github.mariakohnen.packbag.backend.security.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordValidation passwordValidation;

    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, PasswordValidation passwordValidation) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordValidation = passwordValidation;
    }

    public void createNewAppUser(AppUserLoginDto appUserLoginDto) {
        AppUser newUser = new AppUser();
        if (appUserLoginDto.getUsername() == null || appUserLoginDto.getUsername().trim().equals("")) {
            throw new IllegalArgumentException("The name of the user is not given, please enter a valid name.");
        }
        if (appUserRepository.findByUsername(appUserLoginDto.getUsername()).isPresent()) {
            throw new NameAlreadyExistException("The name already exist, please choose another one.");
        }
        if (!passwordValidation.validatePassword(appUserLoginDto.getPassword())) {
            throw new IllegalArgumentException("The password is not valid, please enter a valid one.");
        }
        newUser.setUsername(appUserLoginDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(appUserLoginDto.getPassword()));
        appUserRepository.save(newUser);
    }

}
