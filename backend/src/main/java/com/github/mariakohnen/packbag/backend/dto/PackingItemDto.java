package com.github.mariakohnen.packbag.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackingItemDto {

    private String name;
    private String status;
    private String category;
}
