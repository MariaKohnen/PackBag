package com.github.mariakohnen.packbag.backend.service;

import com.github.mariakohnen.packbag.backend.model.PackingList;
import com.github.mariakohnen.packbag.backend.repository.PackingListRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PackingListServiceTest {

    private final PackingListRepository packingListRepository = mock(PackingListRepository.class);

    private final PackingListService packingListService = new PackingListService(packingListRepository);

    @Test
    void getAllPackingLists() {
        //GIVEN
        PackingList packingList1 = PackingList.builder()
                ._id("1")
                .name("Bayreuth")
                .build();
        PackingList packingList2 = PackingList.builder()
                ._id("2")
                .name("Frankfurt")
                .build();
        when(packingListRepository.findAll()).thenReturn(List.of(packingList1, packingList2));
        //WHEN
        List<PackingList> actual = packingListService.getAllPackingLists();
        //THEN
        verify(packingListRepository).findAll();
        List<PackingList> expected = List.of((PackingList.builder()
                        ._id("1")
                        .name("Bayreuth")
                        .build()),
                PackingList.builder()
                        ._id("2")
                        .name("Frankfurt")
                        .build());
        assertEquals(expected, actual);
    }
}