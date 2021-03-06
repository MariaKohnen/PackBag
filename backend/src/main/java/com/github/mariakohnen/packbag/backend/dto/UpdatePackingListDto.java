package com.github.mariakohnen.packbag.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePackingListDto {

    private String destination;
    private LocalDate dateOfArrival;
}
