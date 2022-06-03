package com.github.mariakohnen.packbag.backend.service;

import com.github.mariakohnen.packbag.backend.dto.NewPackingListDto;
import com.github.mariakohnen.packbag.backend.dto.UpdatePackingListDto;
import com.github.mariakohnen.packbag.backend.model.PackingItem;
import com.github.mariakohnen.packbag.backend.model.PackingList;
import com.github.mariakohnen.packbag.backend.repository.PackingListRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PackingListServiceTest {

    final PackingListRepository packingListRepository = mock(PackingListRepository.class);
    final IdService idService = mock(IdService.class);

    final PackingListService packingListService = new PackingListService(packingListRepository, idService);

    NewPackingListDto newPackingListDto = NewPackingListDto.builder()
            .destination("Kyoto")
            .build();

    UpdatePackingListDto updatePackingListDto = UpdatePackingListDto.builder()
            .destination("Tokyo")
            .dateOfArrival(LocalDate.parse("2022-09-24"))
            .build();

    PackingList packingListWithOneItem = PackingList.builder()
            .id("1")
            .dateOfArrival(LocalDate.parse("2022-09-03"))
            .destination("Kyoto")
            .packingItemList(List.of(PackingItem.builder()
                    .id("01")
                    .name("passport")
                    .build()))
            .build();

    PackingList packingListWithTwoItems = PackingList.builder()
            .id("1")
            .dateOfArrival(LocalDate.parse("2022-09-03"))
            .destination("Kyoto")
            .packingItemList(List.of(PackingItem.builder()
                            .id("01")
                            .name("passport")
                            .build(),
                    PackingItem.builder()
                            .id("02")
                            .name("swimwear")
                            .build()))
            .build();

    String listId = "1";
    String invalidId = "123";

    @Test
    void getAllPackingLists() {
        //GIVEN
        PackingList packingList2 = PackingList.builder()
                .id("2")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .destination("Nagasaki")
                .build();
        when(packingListRepository.findAll()).thenReturn(List.of(packingListWithOneItem, packingList2));
        //WHEN
        List<PackingList> actual = packingListService.getAllPackingLists();
        //THEN
        verify(packingListRepository).findAll();
        List<PackingList> expected = List.of((PackingList.builder()
                        .id("1")
                        .dateOfArrival(LocalDate.parse("2022-09-03"))
                        .destination("Kyoto")
                        .packingItemList(List.of(PackingItem.builder()
                                .id("01")
                                .name("passport")
                                .build()))
                        .build()),
                PackingList.builder()
                        .id("2")
                        .dateOfArrival(LocalDate.parse("2022-10-03"))
                        .destination("Nagasaki")
                        .build());
        assertEquals(expected, actual);
    }

    @Test
    void getPackingListById_whenIdIsValid_shouldReturnPackingList() {
        //GIVEN
        when(packingListRepository.findById(listId)).thenReturn(Optional.ofNullable(packingListWithOneItem));
        //WHEN
        PackingList actual = packingListService.getPackingListById("1");
        //THEN
        PackingList expected = PackingList.builder()
                .id("1")
                .dateOfArrival(LocalDate.parse("2022-09-03"))
                .destination("Kyoto")
                .packingItemList(List.of(PackingItem.builder()
                        .id("01")
                        .name("passport")
                        .build()))
                .build();
        verify(packingListRepository).findById("1");
        assertEquals(expected, actual);
    }

    @Test
    void getPackingListById_whenIdIsNotValid_shouldThrowException() {
        //GIVEN
        when(packingListRepository.findById(invalidId)).thenReturn(Optional.empty());
        //WHEN//THEN
        assertThrows(NoSuchElementException.class, () -> packingListService.getPackingListById(invalidId));
    }

    @Test
    void addNewPackingList_whenNameIsGiven_shouldReturnNewPackingList() {
        //GIVEN
        PackingList packingListToAdd = PackingList.builder()
                .destination("Kyoto")
                .build();
        when(packingListRepository.insert(packingListToAdd)).thenReturn(PackingList.builder()
                .id("1")
                .destination("Kyoto")
                .build());
        //WHEN
        PackingList actual = packingListService.addNewPackingList(newPackingListDto);
        //THEN
        PackingList expected = PackingList.builder()
                .id("1")
                .destination("Kyoto")
                .build();
        verify(packingListRepository).insert(packingListToAdd);
        assertEquals(expected, actual);
    }

    @Test
    void addNewPackingList_whenNameNull_shouldThrowException() {
        //GIVEN
        NewPackingListDto emptyList = NewPackingListDto.builder()
                .build();
        //WHEN//THEN
        assertThrows(IllegalArgumentException.class, () -> packingListService.addNewPackingList(emptyList));

    }

    @Test
    void updatePackingListById_whenIdIsValid_shouldReturnUpdatedPackingList() {
        //GIVEN
        when(packingListRepository.findById(listId)).thenReturn(Optional.ofNullable(packingListWithOneItem));
        PackingList updatedPackingList = PackingList.builder()
                .id("1")
                .dateOfArrival(LocalDate.parse("2022-09-24"))
                .destination("Tokyo")
                .packingItemList(List.of(PackingItem.builder()
                        .id("01")
                        .name("passport")
                        .build()))
                .build();

        when(packingListRepository.save(updatedPackingList)).thenReturn(PackingList.builder()
                .id("1")
                .dateOfArrival(LocalDate.parse("2022-09-24"))
                .destination("Tokyo")
                .packingItemList(List.of(PackingItem.builder()
                        .id("01")
                        .name("passport")
                        .build()))
                .build());
        //WHEN
        PackingList actual = packingListService.updatePackingListById(listId, updatePackingListDto);
        //THEN
        PackingList expected = PackingList.builder()
                .id("1")
                .dateOfArrival(LocalDate.parse("2022-09-24"))
                .destination("Tokyo")
                .packingItemList(List.of(PackingItem.builder()
                        .id("01")
                        .name("passport")
                        .build()))
                .build();
        assertEquals(expected, actual);
        verify(packingListRepository).findById("1");
        verify(packingListRepository).save(updatedPackingList);
    }

    @Test
    void updatePackingListById_whenIdIsNotValid_shouldThrowNoSuchElementException() {
        //GIVEN
        when(packingListRepository.findById(invalidId)).thenReturn(Optional.empty());
        //WHEN //THEN
        assertThrows(NoSuchElementException.class, () -> packingListService.updatePackingListById("123", updatePackingListDto));
        verify(packingListRepository).findById(invalidId);
    }

    @Test
    void deletePackingListById_whenIdIsValid() {
        //WHEN
        packingListService.deletePackingListById(packingListWithOneItem.getId());
        //THEN
        verify(packingListRepository).deleteById("1");
    }


}
