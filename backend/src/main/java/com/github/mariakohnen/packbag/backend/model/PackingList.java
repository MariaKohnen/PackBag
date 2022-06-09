package com.github.mariakohnen.packbag.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "packingLists")
public class PackingList {

    @Id
    private String id;
    private String destination;
    private LocalDate dateOfArrival;
    private String color;
    private List<PackingItem> packingItemList;
}
