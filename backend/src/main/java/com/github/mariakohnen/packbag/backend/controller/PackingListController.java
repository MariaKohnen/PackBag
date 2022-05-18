package com.github.mariakohnen.packbag.backend.controller;

import com.github.mariakohnen.packbag.backend.model.PackingList;
import com.github.mariakohnen.packbag.backend.service.PackingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/packinglists")
public class PackingListController {

    private final PackingListService packingListService;

    @Autowired
    public PackingListController(PackingListService packingListService) {
        this.packingListService = packingListService;
    }

    @GetMapping
    public List<PackingList> getAllPackingLists () {
        return packingListService.getAllPackingLists();
    }
}
