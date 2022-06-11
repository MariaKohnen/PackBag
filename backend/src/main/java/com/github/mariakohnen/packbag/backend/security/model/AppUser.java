package com.github.mariakohnen.packbag.backend.security.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document (collection = "appUsers")
public class AppUser {

    @Id
    private String id;

    private String username;
    private String password;
}
