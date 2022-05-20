package com.github.mariakohnen.packbag.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackingListDto {

    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfArrival;
}
