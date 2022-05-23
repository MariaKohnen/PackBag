package com.github.mariakohnen.packbag.backend.controller;

import com.github.mariakohnen.packbag.backend.dto.PackingListDto;
import com.github.mariakohnen.packbag.backend.model.PackingList;
import com.github.mariakohnen.packbag.backend.service.PackingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("{id}")
    public PackingList getPackingListById (@PathVariable String id) {
        return packingListService.getPackingListById(id);
    }

    @PostMapping
    public PackingList postNewPackingList(@RequestBody PackingListDto packingListDto) {
        return packingListService.addNewPackingList(packingListDto);
    }
}
