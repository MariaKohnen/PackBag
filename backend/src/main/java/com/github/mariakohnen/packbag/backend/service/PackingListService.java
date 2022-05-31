package com.github.mariakohnen.packbag.backend.service;

import com.github.mariakohnen.packbag.backend.dto.CreatePackingItemDto;
import com.github.mariakohnen.packbag.backend.dto.PackingListDto;
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

    public PackingList addNewPackingList(PackingListDto packingListDto) {
        if (packingListDto.getDestination() == null) {
            throw new IllegalArgumentException("Destination of the given packing list was not given.");
        }
        PackingList newPackingList = PackingList.builder()
                .destination(packingListDto.getDestination())
                .build();
        return packingListRepository.insert(newPackingList);
    }

    public PackingList getPackingListById(String id) {
        return packingListRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Packing list with id: " + id + " was not found!"));
    }

    public PackingList updatePackingListById(String id, PackingListDto packingListDto) {
        PackingList updatedPackingList = packingListRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Packing list with the id: " + id + " was not found, please try again!"));
        updatedPackingList.setDestination(packingListDto.getDestination());
        updatedPackingList.setDateOfArrival(packingListDto.getDateOfArrival());
        return packingListRepository.save(updatedPackingList);
    }

    public void deletePackingListById(String id) throws NullPointerException {
        packingListRepository.deleteById(id);
    }

    public PackingList addNewPackingItem(String id, CreatePackingItemDto createPackingItemDto) {
        PackingList updatedPackingList = packingListRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Packing list with id: " + id + " was not found!"));
        List<PackingItem> actualItemList = updatedPackingList.getPackingItemList();
        if (actualItemList != null) {
            List<PackingItem> updatedItemList = new ArrayList<>(actualItemList);
            updatedItemList.add(generateNewItem(createPackingItemDto));
            updatedPackingList.setPackingItemList(updatedItemList);
        } else {
            List<PackingItem> newItemList = new ArrayList<>();
            newItemList.add((generateNewItem(createPackingItemDto)));
            updatedPackingList.setPackingItemList(newItemList);
        }
        return packingListRepository.save(updatedPackingList);
    }

    public PackingItem generateNewItem(CreatePackingItemDto createPackingItemDto) {
        if (createPackingItemDto.getName() == null) {
            throw new IllegalArgumentException("Name of the given packing item was not given.");
        }
        return PackingItem.builder()
                .id(idService.generateId())
                .name(createPackingItemDto.getName())
                .build();
    }
}

