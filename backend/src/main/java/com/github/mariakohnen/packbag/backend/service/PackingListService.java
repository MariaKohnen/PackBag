package com.github.mariakohnen.packbag.backend.service;

import com.github.mariakohnen.packbag.backend.dto.PackingListDto;
import com.github.mariakohnen.packbag.backend.model.PackingList;
import com.github.mariakohnen.packbag.backend.repository.PackingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PackingListService {

    private final PackingListRepository packingListRepository;

    @Autowired
    public PackingListService(PackingListRepository packingListRepository) {
        this.packingListRepository = packingListRepository;
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
        updatedPackingList.setDestination(packingListDto.getDestination());
        updatedPackingList.setDateOfArrival(packingListDto.getDateOfArrival());
        return packingListRepository.save(updatedPackingList);
    }
}
