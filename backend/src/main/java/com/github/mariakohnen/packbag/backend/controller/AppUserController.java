package com.github.mariakohnen.packbag.backend.controller;

import com.github.mariakohnen.packbag.backend.dto.AppUserDto;
import com.github.mariakohnen.packbag.backend.security.model.AppUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class AppUserController {

    @GetMapping("me")
    public AppUserDto getLoggedInUser(Principal principal) {
        return new AppUserDto(principal.getName());
    }
}
