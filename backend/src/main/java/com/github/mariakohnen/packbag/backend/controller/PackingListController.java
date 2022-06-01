package com.github.mariakohnen.packbag.backend.controller;

import com.github.mariakohnen.packbag.backend.dto.CreatePackingItemDto;
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
    public List<PackingList> getAllPackingLists() {
        return packingListService.getAllPackingLists();
    }

    @GetMapping("{id}")
    public PackingList getPackingListById(@PathVariable String id) {
        return packingListService.getPackingListById(id);
    }

    @PostMapping
    public PackingList postNewPackingList(@RequestBody PackingListDto packingListDto) {
        return packingListService.addNewPackingList(packingListDto);
    }

    @PutMapping("{id}")
    public PackingList updateExistingPackingListById(@PathVariable String id, @RequestBody PackingListDto packingListDto) {
        return packingListService.updatePackingListById(id, packingListDto);
    }

    @PutMapping("/{id}/packingitems")
    public PackingList addPackingItemToPackingList(@PathVariable String id, @RequestBody CreatePackingItemDto createPackingItemDto) {
        return packingListService.addNewPackingItem(id, createPackingItemDto);
    }

    @DeleteMapping("{id}")
    public void deletePackingListByID(@PathVariable String id) {
        packingListService.deletePackingListById(id);
    }
}
