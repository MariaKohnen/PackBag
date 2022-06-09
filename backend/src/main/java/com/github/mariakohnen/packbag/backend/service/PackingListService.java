package com.github.mariakohnen.packbag.backend.service;

import com.github.mariakohnen.packbag.backend.dto.PackingItemDto;
import com.github.mariakohnen.packbag.backend.dto.NewPackingListDto;
import com.github.mariakohnen.packbag.backend.dto.UpdatePackingListDto;
import com.github.mariakohnen.packbag.backend.model.PackingItem;
import com.github.mariakohnen.packbag.backend.model.PackingList;
import com.github.mariakohnen.packbag.backend.repository.PackingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PackingListService {

    private final PackingListRepository packingListRepository;
    private final IdService idService;

    @Autowired
    public PackingListService(PackingListRepository packingListRepository, IdService idService) {
        this.packingListRepository = packingListRepository;
        this.idService = idService;
    }

    public List<PackingList> getAllPackingLists() {
        return packingListRepository.findAll();
    }

    public PackingList addNewPackingList(NewPackingListDto newPackingListDto) {
        if (newPackingListDto.getDestination() == null || newPackingListDto.getDestination().trim().equals("")) {
            throw new IllegalArgumentException("Destination of the given packing list was not given.");
        }
        if (newPackingListDto.getColor() == null) {
            newPackingListDto.setColor("#5f8bc0");
        }
        PackingList newPackingList = PackingList.builder()
                .destination(newPackingListDto.getDestination())
                .color(newPackingListDto.getColor())
                .build();
        return packingListRepository.insert(newPackingList);
    }

    public PackingList getPackingListById(String id) {
        return packingListRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("A Packing list with id: " + id + " could not be found!"));
    }

    public PackingList updatePackingListById(String id, UpdatePackingListDto updatePackingListDto) {
        PackingList updatedPackingList = getPackingListById(id);
        if (updatePackingListDto.getDestination() == null || updatePackingListDto.getDestination().trim().equals("")) {
            throw new IllegalArgumentException("Packing list with id: " + id + " was not updated, because new name was not given.");
        } else {
            updatedPackingList.setDestination(updatePackingListDto.getDestination());
            updatedPackingList.setDateOfArrival(updatePackingListDto.getDateOfArrival());
        }
        return packingListRepository.save(updatedPackingList);
    }

    public void deletePackingListById(String id) throws NullPointerException {
        packingListRepository.deleteById(id);
    }

    public PackingList addNewPackingItem(String id, PackingItemDto packingItemDto) {
        PackingList updatedPackingList = getPackingListById(id);
        List<PackingItem> actualItemList = updatedPackingList.getPackingItemList();
        if (actualItemList != null) {
            List<PackingItem> updatedItemList = new ArrayList<>(actualItemList);
            updatedItemList.add(generateNewItem(packingItemDto));
            updatedPackingList.setPackingItemList(updatedItemList);
        } else {
            List<PackingItem> newItemList = new ArrayList<>();
            newItemList.add((generateNewItem(packingItemDto)));
            updatedPackingList.setPackingItemList(newItemList);
        }
        return packingListRepository.save(updatedPackingList);
    }

    public PackingItem generateNewItem(PackingItemDto packingItemDto) {
        if (packingItemDto.getName() == null || packingItemDto.getName().trim().equals("")) {
            throw new IllegalArgumentException("Name of the given packing item was not given.");
        }
        return PackingItem.builder()
                .id(idService.generateId())
                .name(packingItemDto.getName())
                .status(packingItemDto.getStatus())
                .category(packingItemDto.getCategory())
                .build();
    }

    public PackingList deleteItemFromPackingList(String id, String itemId) {
        PackingList updatedPackingList = getPackingListById(id);
        List<PackingItem> actualItemList = updatedPackingList.getPackingItemList();
        if (actualItemList != null) {
            List<PackingItem> updatedItemList = new ArrayList<>(actualItemList);
            updatedItemList.removeIf(item -> item.getId().equals(itemId));
            updatedPackingList.setPackingItemList(updatedItemList);
            return packingListRepository.save(updatedPackingList);
        } else throw new NoSuchElementException("There is no item with the id: " + itemId);
    }

    public PackingList updatePackingItem(String id, String itemId, PackingItemDto packingItemDto) {
        if (packingItemDto.getName() == null) {
            throw new IllegalArgumentException("The item was not updated. Name of the given item was null.");
        }
        PackingList listToEdit = getPackingListById(id);
        List<PackingItem> actualItemList = listToEdit.getPackingItemList();
        PackingItem itemToUpdate = actualItemList
                .stream()
                .filter(item -> itemId.equals(item.getId()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("The item was not updated. An item with the id: " + itemId + " was not found."));
        itemToUpdate.setName(packingItemDto.getName());
        itemToUpdate.setStatus(packingItemDto.getStatus());
        itemToUpdate.setCategory(packingItemDto.getCategory());
        return packingListRepository.save(listToEdit);
    }

    public PackingItem getPackingItemById(String id, String itemId) {
        PackingList updatedPackingList = getPackingListById(id);
        List<PackingItem> actualItemList = updatedPackingList.getPackingItemList();
        return actualItemList
                .stream()
                .filter(item -> itemId.equals(item.getId()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("The item was not updated. An item with the id: " + itemId + " was not found."));
    }
}
