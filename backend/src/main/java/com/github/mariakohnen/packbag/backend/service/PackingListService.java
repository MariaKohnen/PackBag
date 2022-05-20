package com.github.mariakohnen.packbag.backend.service;

import com.github.mariakohnen.packbag.backend.dto.PackingListDto;
import com.github.mariakohnen.packbag.backend.model.PackingList;
import com.github.mariakohnen.packbag.backend.repository.PackingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackingListService {

    private final PackingListRepository packingListRepository;

    private final UtilService utilService;

    @Autowired
    public PackingListService(PackingListRepository packingListRepository, UtilService utilService) {
        this.packingListRepository = packingListRepository;
        this.utilService = utilService;
    }

    public List<PackingList> getAllPackingLists() {
        return packingListRepository.findAll();
    }

    public PackingList addNewPackingList(PackingListDto packingListDto) {
        if (packingListDto.getName() == null) {
            throw new IllegalArgumentException("Name of the given packing list was null");
        }
        PackingList newPackingList = PackingList.builder()
                .name(packingListDto.getName())
                .dateOfArrival(utilService.dateStringToInstant(packingListDto.getDateOfArrival()))
                .build();
        return packingListRepository.insert(newPackingList);
    }

}
