package com.github.mariakohnen.packbag.backend.service;

import com.github.mariakohnen.packbag.backend.dto.NewPackingItemDto;
import com.github.mariakohnen.packbag.backend.dto.NewPackingListDto;
import com.github.mariakohnen.packbag.backend.dto.PackingListDto;
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

    private final PackingListRepository packingListRepository = mock(PackingListRepository.class);
    private final IdService idService = mock(IdService.class);

    private final PackingListService packingListService = new PackingListService(packingListRepository, idService);

    NewPackingListDto newPackingListDto = NewPackingListDto.builder()
            .destination("Kyoto")
            .build();

    UpdatePackingListDto updatePackingListDto = UpdatePackingListDto.builder()
            .destination("Tokyo")
            .dateOfArrival(LocalDate.parse("2022-09-24"))
            .build();

    PackingList existingList = PackingList.builder()
            .id("1")
            .dateOfArrival(LocalDate.parse("2022-09-03"))
            .destination("Kyoto")
            .packingItemList(List.of(PackingItem.builder()
                    .id("01")
                    .name("passport")
                    .build()))
            .build();

    PackingList existingListWithItemList = PackingList.builder()
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

    NewPackingItemDto newPackingItemDto = NewPackingItemDto.builder()
            .name("swimwear")
            .build();

    NewPackingItemDto newPackingItemDto2 = NewPackingItemDto.builder()
            .name("passport")
            .build();

    String listId = "1";
    String itemId = "01";
    String invalidId = "123";

    @Test
    void getAllPackingLists() {
        //GIVEN
        PackingList packingList2 = PackingList.builder()
                .id("2")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .destination("Nagasaki")
                .build();
        when(packingListRepository.findAll()).thenReturn(List.of(existingList, packingList2));
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
        when(packingListRepository.findById(listId)).thenReturn(Optional.ofNullable(existingList));
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
        when(packingListRepository.findById(listId)).thenReturn(Optional.ofNullable(existingList));
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
        packingListService.deletePackingListById(existingList.getId());
        //THEN
        verify(packingListRepository).deleteById("1");
    }

    @Test
    void addNewPackingItem_whenNameIsGivenAndActualListIsNotNull_ShouldReturnUpdatedPackingListWithAllItems() {
        //GIVEN
        when(idService.generateId()).thenReturn("02");

        when(packingListRepository.findById(listId)).thenReturn(Optional.ofNullable(existingList));

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

        when(packingListRepository.save(updatedPackingList)).thenReturn(existingListWithItemList);
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

        when(packingListRepository.save(existingList)).thenReturn(PackingList.builder()
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
        verify(packingListRepository).save(existingList);
        verify(idService).generateId();
    }

    @Test
    void addNewPackingItem_whenNameIsNull_ShouldThrowIllegalArgumentException() {
        //GIVEN
        NewPackingItemDto emptyItem = NewPackingItemDto.builder()
                .build();
        when(packingListRepository.findById(listId)).thenReturn(Optional.ofNullable(existingList));
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
        when(packingListRepository.findById("1")).thenReturn(Optional.ofNullable(existingListWithItemList));
        when(packingListRepository.save(existingList)).thenReturn(PackingList.builder()
                .id("01")
                .destination("Kyoto")
                .dateOfArrival(LocalDate.parse("2022-09-03"))
                .packingItemList(List.of(PackingItem.builder()
                        .id("01")
                        .name("passport")
                        .build()))
                .build());
        //WHEN
        assertNotNull(existingList);
        PackingList actual = packingListService.deleteItemFromPackingList(listId, "02");
        //THEN
        verify(packingListRepository).save(existingList);
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
        when(packingListRepository.findById(listId)).thenReturn(Optional.of(existingList));
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
        when(packingListRepository.findById(listId)).thenReturn(Optional.of(existingList));
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
