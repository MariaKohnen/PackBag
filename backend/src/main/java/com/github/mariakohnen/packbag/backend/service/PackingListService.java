package com.github.mariakohnen.packbag.backend.service;

import com.github.mariakohnen.packbag.backend.dto.PackingItemDto;
import com.github.mariakohnen.packbag.backend.dto.PackingListDto;
import com.github.mariakohnen.packbag.backend.model.PackingItem;
import com.github.mariakohnen.packbag.backend.model.PackingList;
import com.github.mariakohnen.packbag.backend.repository.PackingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new NoSuchElementException("Shopping with id: " + id + " was not found!"));
        List<PackingItem> updatedItemList = generateNewList(packingListDto.getPackingItemDto(), updatedPackingList.getPackingItemList());
        updatedPackingList.setDestination(packingListDto.getDestination());
        updatedPackingList.setDateOfArrival(packingListDto.getDateOfArrival());
        updatedPackingList.setPackingItemList(updatedItemList);
        return packingListRepository.save(updatedPackingList);
    }

    public void deletePackingListById(String id) throws NullPointerException {
        packingListRepository.deleteById(id);
    }

    public List<PackingItem> generateNewList(PackingItemDto packingItemDto, List<PackingItem> actualItemList) {
        if (packingItemDto.getName() == null) {
            throw new IllegalArgumentException("Name of the given packing item was not given.");
        }
        PackingItem newItem = PackingItem.builder()
                .id(idService.generateId())
                .name(packingItemDto.getName())
                .build();
        actualItemList.add(newItem);
        return actualItemList;
    }
}

