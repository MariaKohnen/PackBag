package com.github.mariakohnen.packbag.backend.service;

import com.github.mariakohnen.packbag.backend.dto.NewPackingItemDto;
import com.github.mariakohnen.packbag.backend.model.PackingItem;
import com.github.mariakohnen.packbag.backend.model.PackingList;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PackingItemServiceTest extends PackingListServiceTest{

    NewPackingItemDto newPackingItemDto = NewPackingItemDto.builder()
            .name("swimwear")
            .build();

    NewPackingItemDto newPackingItemDto2 = NewPackingItemDto.builder()
            .name("passport")
            .build();

    String itemId = "01";

    @Test
    void addNewPackingItem_whenNameIsGivenAndActualListIsNotNull_ShouldReturnUpdatedPackingListWithAllItems() {
        //GIVEN
        when(idService.generateId()).thenReturn("02");

        when(packingListRepository.findById(listId)).thenReturn(Optional.ofNullable(packingListWithOneItem));

        PackingList updatedPackingList = PackingList.builder()
                .id("1")
                .destination("Kyoto")
                .dateOfArrival(LocalDate.parse("2022-09-03"))
                .packingItemList(List.of(PackingItem.builder()
                                .id("01")
                                .name("passport")
                                .build(),
                        PackingItem.builder()
                                .id("02")
                                .name("swimwear")
                                .build()))
                .build();

        when(packingListRepository.save(updatedPackingList)).thenReturn(packingListWithTwoItems);
        //WHEN
        PackingList actual = packingListService.addNewPackingItem(listId, newPackingItemDto);
        //THEN
        PackingList expected = PackingList.builder()
                .id("1")
                .destination("Kyoto")
                .dateOfArrival(LocalDate.parse("2022-09-03"))
                .packingItemList(List.of(PackingItem.builder()
                                .id("01")
                                .name("passport")
                                .build(),
                        PackingItem.builder()
                                .id("02")
                                .name("swimwear")
                                .build()))
                .build();
        assertEquals(expected, actual);
        verify(packingListRepository).save(updatedPackingList);
        verify(idService).generateId();
    }

    @Test
    void addNewPackingItem_whenNameIsGivenAndActualListIsNull_ShouldReturnUpdatedPackingListWithAllItems() {
        //GIVEN
        when(idService.generateId()).thenReturn("01");

        when(packingListRepository.findById(listId)).thenReturn(Optional.ofNullable(
                PackingList.builder()
                        .id("1")
                        .destination("Kyoto")
                        .dateOfArrival(LocalDate.parse("2022-09-03"))
                        .build()));

        when(packingListRepository.save(packingListWithOneItem)).thenReturn(PackingList.builder()
                .id("1")
                .destination("Kyoto")
                .dateOfArrival(LocalDate.parse("2022-09-03"))
                .packingItemList(List.of(
                        PackingItem.builder()
                                .id("01")
                                .name("passport")
                                .build()))
                .build());
        //WHEN
        PackingList actual = packingListService.addNewPackingItem(listId, newPackingItemDto2);
        //THEN
        PackingList expected = PackingList.builder()
                .id("1")
                .destination("Kyoto")
                .dateOfArrival(LocalDate.parse("2022-09-03"))
                .packingItemList(List.of(
                        PackingItem.builder()
                                .id("01")
                                .name("passport")
                                .build()))
                .build();
        assertEquals(expected, actual);
        verify(packingListRepository).save(packingListWithOneItem);
        verify(idService).generateId();
    }

    @Test
    void addNewPackingItem_whenNameIsNull_ShouldThrowIllegalArgumentException() {
        //GIVEN
        NewPackingItemDto emptyItem = NewPackingItemDto.builder()
                .build();
        when(packingListRepository.findById(listId)).thenReturn(Optional.ofNullable(packingListWithOneItem));
        //WHEN//THEN
        assertThrows(IllegalArgumentException.class, () -> packingListService.addNewPackingItem(listId, emptyItem));
    }

    @Test
    void generateNewItem_whenNameIsGiven_ShouldReturnPackingItem() {
        //GIVEN
        when(idService.generateId()).thenReturn("01");
        //WHEN
        PackingItem actual = packingListService.generateNewItem(newPackingItemDto);
        //THEN
        PackingItem expected = PackingItem.builder()
                .id("01")
                .name("swimwear")
                .build();
        assertEquals(expected, actual);
        verify(idService).generateId();
    }

    @Test
    void generateNewItem_whenNameIsNotGiven_ShouldThrowIllegalArgumentException() {
        //GIVEN
        NewPackingItemDto emptyItem = NewPackingItemDto.builder()
                .build();
        //WHEN//THEN
        assertThrows(IllegalArgumentException.class, () -> packingListService.generateNewItem(emptyItem));
    }

    @Test
    void deleteItemFromPackingList_whenIdOfListAndItemAreValid() {
        //GIVEN
        when(packingListRepository.findById("1")).thenReturn(Optional.ofNullable(packingListWithTwoItems));
        when(packingListRepository.save(packingListWithOneItem)).thenReturn(PackingList.builder()
                .id("01")
                .destination("Kyoto")
                .dateOfArrival(LocalDate.parse("2022-09-03"))
                .packingItemList(List.of(PackingItem.builder()
                        .id("01")
                        .name("passport")
                        .build()))
                .build());
        //WHEN
        assertNotNull(packingListWithOneItem);
        PackingList actual = packingListService.deleteItemFromPackingList(listId, "02");
        //THEN
        verify(packingListRepository).save(packingListWithOneItem);
        PackingList expected = PackingList.builder()
                .id("01")
                .destination("Kyoto")
                .dateOfArrival(LocalDate.parse("2022-09-03"))
                .packingItemList(List.of(PackingItem.builder()
                        .id("01")
                        .name("passport")
                        .build()))
                .build();
        assertEquals(expected, actual);
    }

    @Test
    void deleteItemFromPackingList_whenIdOfListIsNotValid_ShouldThrowNoSuchElementException() {
        //GIVEN
        when(packingListRepository.findById(listId)).thenReturn(Optional.empty());
        //WHEN //THEN
        assertThrows(NoSuchElementException.class, () -> packingListService.deleteItemFromPackingList(listId, itemId));
    }

    @Test
    void deleteItemFromPackingList_whenListOfItemsIsNull_ShouldThrowNoSuchElementException() {
        //GIVEN
        PackingList packingList = PackingList.builder()
                .id("2")
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .build();
        when(packingListRepository.findById(listId)).thenReturn(Optional.ofNullable(packingList));
        //WHEN //THEN
        assertThrows(NoSuchElementException.class, () -> packingListService.deleteItemFromPackingList("2", itemId));
    }

    @Test
    void updatePackingItem_whenIdOfListAndItemIsValid_ShouldReturnUpdatedList() {
        //GIVEN
        when(packingListRepository.findById(listId)).thenReturn(Optional.of(packingListWithOneItem));
        PackingList updatedPackingList = PackingList.builder()
                .id("1")
                .destination("Kyoto")
                .dateOfArrival(LocalDate.parse("2022-09-03"))
                .packingItemList(List.of(
                        PackingItem.builder()
                                .id("01")
                                .name("swimwear")
                                .build()))
                .build();
        when(packingListRepository.save(updatedPackingList)).thenReturn(PackingList.builder()
                .id("1")
                .destination("Kyoto")
                .dateOfArrival(LocalDate.parse("2022-09-03"))
                .packingItemList(List.of(
                        PackingItem.builder()
                                .id("01")
                                .name("swimwear")
                                .build()))
                .build());
        //WHEN
        PackingList actual = packingListService.updatePackingItem(listId, itemId, newPackingItemDto);
        //THEN
        PackingList expected = PackingList.builder()
                .id("1")
                .destination("Kyoto")
                .dateOfArrival(LocalDate.parse("2022-09-03"))
                .packingItemList(List.of(
                        PackingItem.builder()
                                .id("01")
                                .name("swimwear")
                                .build()))
                .build();
        assertEquals(expected, actual);
        verify(packingListRepository).findById(listId);
        verify(packingListRepository).save(updatedPackingList);
    }

    @Test
    void updatePackingItem_whenIdOfItemIsNotValid_ShouldThrowNoSuchElementException() {
        //GIVEN
        when(packingListRepository.findById(listId)).thenReturn(Optional.of(packingListWithOneItem));
        //WHEN //THEN
        assertThrows(NoSuchElementException.class, () -> packingListService.updatePackingItem(listId, "02", newPackingItemDto));
        verify(packingListRepository).findById(listId);
    }

    @Test
    void updatePackingItem_whenNameOfItemIsNotGiven_shouldThrowIllegalArgumentException() {
        //GIVEN
        NewPackingItemDto emptyItem = NewPackingItemDto.builder()
                .build();
        //WHEN //THEN
        assertThrows(IllegalArgumentException.class, () -> packingListService.updatePackingItem(listId, itemId, emptyItem));
    }
}
