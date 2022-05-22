package com.github.mariakohnen.packbag.backend.service;

import com.github.mariakohnen.packbag.backend.dto.PackingListDto;
import com.github.mariakohnen.packbag.backend.model.PackingList;
import com.github.mariakohnen.packbag.backend.repository.PackingListRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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
                .id("1")
                .dateOfArrival(LocalDate.parse("2022-10-02"))
                .destination("Bayreuth")
                .build();
        PackingList packingList2 = PackingList.builder()
                .id("2")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .destination("Frankfurt")
                .build();
        when(packingListRepository.findAll()).thenReturn(List.of(packingList1, packingList2));
        //WHEN
        List<PackingList> actual = packingListService.getAllPackingLists();
        //THEN
        verify(packingListRepository).findAll();
        List<PackingList> expected = List.of((PackingList.builder()
                        .id("1")
                        .dateOfArrival(LocalDate.parse("2022-10-02"))
                        .destination("Bayreuth")
                        .build()),
                PackingList.builder()
                        .id("2")
                        .dateOfArrival(LocalDate.parse("2022-10-03"))
                        .destination("Frankfurt")
                        .build());
        assertEquals(expected, actual);
    }

    @Test
    void addNewPackingList_whenNameIsGiven_shouldReturnNewPackingList() {
        //GIVEN
        PackingListDto packingListDto = PackingListDto.builder()
                .destination("Bayreuth")
                .build();
        PackingList packingListToAdd = PackingList.builder()
                .destination("Bayreuth")
                .build();
        when(packingListRepository.insert(packingListToAdd)).thenReturn(PackingList.builder()
                .id("123")
                .destination("Bayreuth")
                .build());
        //WHEN
        PackingList actual = packingListService.addNewPackingList(packingListDto);
        //THEN
        PackingList expected = PackingList.builder()
                .id("123")
                .destination("Bayreuth")
                .build();
        verify(packingListRepository).insert(packingListToAdd);
        assertEquals(expected, actual);
    }

    @Test
    void addNewPackingList_whenNameNull_shouldThrowException() {
        //GIVEN
        PackingListDto packingListDto = PackingListDto.builder()
                .destination(null)
                .build();
        //WHEN//THEN
        assertThrows(IllegalArgumentException.class, () -> packingListService.addNewPackingList(packingListDto));

    }

}