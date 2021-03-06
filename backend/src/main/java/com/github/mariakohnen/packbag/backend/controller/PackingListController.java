package com.github.mariakohnen.packbag.backend.controller;

import com.github.mariakohnen.packbag.backend.dto.PackingItemDto;
import com.github.mariakohnen.packbag.backend.dto.NewPackingListDto;
import com.github.mariakohnen.packbag.backend.dto.UpdatePackingListDto;
import com.github.mariakohnen.packbag.backend.model.PackingItem;
import com.github.mariakohnen.packbag.backend.model.PackingList;
import com.github.mariakohnen.packbag.backend.service.PackingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public List<PackingList> getAllPackingListsByUser(Principal principal) {
        String username = principal.getName();
        return packingListService.getAllPackingListsByUser(username);
    }

    @GetMapping("{id}")
    public PackingList getPackingListById(@PathVariable String id) {
        return packingListService.getPackingListById(id);
    }

    @GetMapping("/{id}/packingitems/{itemId}")
    public PackingItem getPackingItemById(@PathVariable String id, @PathVariable String itemId) {
        return packingListService.getPackingItemById(id, itemId);
    }

    @PostMapping
    public PackingList postNewPackingList(@RequestBody NewPackingListDto newPackingListDto, Principal principal) {
        String username = principal.getName();
        return packingListService.addNewPackingList(newPackingListDto, username);
    }

    @PutMapping("{id}")
    public PackingList updateExistingPackingListById(@PathVariable String id, @RequestBody UpdatePackingListDto updatePackingListDto) {
        return packingListService.updatePackingListById(id, updatePackingListDto);
    }

    @PutMapping("/{id}/packingitems")
    public PackingList addPackingItemToPackingList(@PathVariable String id, @RequestBody PackingItemDto packingItemDto) {
        return packingListService.addNewPackingItem(id, packingItemDto);
    }

    @PutMapping("/{id}/packingitems/{itemId}")
    public PackingList updateExistingPackingItemOfList(@PathVariable String id, @PathVariable String itemId, @RequestBody PackingItemDto packingItemDto) {
        return packingListService.updatePackingItem(id, itemId, packingItemDto);
    }

    @DeleteMapping("{id}")
    public void deletePackingListByID(@PathVariable String id) {
        packingListService.deletePackingListById(id);
    }

    @DeleteMapping("/{id}/packingitems/{itemId}")
    public PackingList deleteItemFromPackingList(@PathVariable String id, @PathVariable String itemId) {
        return packingListService.deleteItemFromPackingList(id, itemId);
    }
}
