package com.github.mariakohnen.packbag.backend.security.controller;

import com.github.mariakohnen.packbag.backend.security.dto.AppUserLoginDto;
import com.github.mariakohnen.packbag.backend.security.service.AppUserDetailsService;
import com.github.mariakohnen.packbag.backend.security.service.AppUserService;
import com.github.mariakohnen.packbag.backend.security.service.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AppUserAuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtilService jwtUtilService;
    private final AppUserService appUserService;

    @Autowired
    public AppUserAuthController(AuthenticationManager authenticationManager, JwtUtilService jwtUtilService, AppUserDetailsService appUserDetailsService, AppUserService appUserService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtilService = jwtUtilService;
        this.appUserService = appUserService;
    }

    @PostMapping("/login")
    public String login(@RequestBody AppUserLoginDto appUserLoginDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUserLoginDto.getUsername(), appUserLoginDto.getPassword()));
        return jwtUtilService.createToken(appUserLoginDto.getUsername());
    }

    @PostMapping("/registration")
    public String postNewAppUser(@RequestBody AppUserLoginDto appUserLoginDto) {
        appUserService.createNewAppUser(appUserLoginDto);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUserLoginDto.getUsername(), appUserLoginDto.getPassword()));
        return jwtUtilService.createToken(appUserLoginDto.getUsername());
    }
}

