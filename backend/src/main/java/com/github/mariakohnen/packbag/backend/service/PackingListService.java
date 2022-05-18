package com.github.mariakohnen.packbag.backend.service;

import com.github.mariakohnen.packbag.backend.model.PackingList;
import com.github.mariakohnen.packbag.backend.repository.PackingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackingListService {

    private final PackingListRepository packingListRepository;

    @Autowired
    public PackingListService(PackingListRepository packingListRepository) {
        this.packingListRepository = packingListRepository;
    }

    public List<PackingList> getAllPackingLists () {
        return packingListRepository.findAll();
    }
}
