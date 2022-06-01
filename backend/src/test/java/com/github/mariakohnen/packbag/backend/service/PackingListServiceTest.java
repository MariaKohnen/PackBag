package com.github.mariakohnen.packbag.backend.service;

import com.github.mariakohnen.packbag.backend.dto.CreatePackingItemDto;
import com.github.mariakohnen.packbag.backend.dto.PackingListDto;
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


    @Test
    void getAllPackingLists() {
        //GIVEN
        PackingList packingList1 = PackingList.builder()
                .id("1")
                .dateOfArrival(LocalDate.parse("2022-10-02"))
                .destination("Bayreuth")
                .packingItemList(List.of(PackingItem.builder()
                        .name("swimwear")
                        .build()))
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
                        .packingItemList(List.of(PackingItem.builder()
                                .name("swimwear")
                                .build()))
                        .build()),
                PackingList.builder()
                        .id("2")
                        .dateOfArrival(LocalDate.parse("2022-10-03"))
                        .destination("Frankfurt")
                        .build());
        assertEquals(expected, actual);
    }

    @Test
    void getPackingListById_whenIdIsValid_shouldReturnPackingList() {
        //GIVEN
        PackingList packingList1 = PackingList.builder()
                .id("1")
                .destination("Bayreuth")
                .build();
        when(packingListRepository.findById("1")).thenReturn(Optional.ofNullable(packingList1));
        //WHEN
        PackingList actual = packingListService.getPackingListById("1");
        //THEN
        PackingList expected = PackingList.builder()
                .id("1")
                .destination("Bayreuth")
                .build();
        verify(packingListRepository).findById("1");
        assertEquals(expected, actual);
    }

    @Test
    void getPackingListById_whenIdIsNotValid_shouldThrowException() {
        //GIVEN
        String invalidId = "123";
        when(packingListRepository.findById(invalidId)).thenReturn(Optional.empty());
        //WHEN//THEN
        assertThrows(NoSuchElementException.class, () -> packingListService.getPackingListById(invalidId));
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

    @Test
    void updatePackingListById_whenIdIsValid_shouldReturnUpdatedPackingList() {
        //GIVEN
        String pathId = "123";
        List<PackingItem> packingItemList = List.of(PackingItem.builder()
                        .id("1")
                        .name("swimwear")
                        .build(),
                PackingItem.builder()
                        .id("2")
                        .name("passport")
                        .build());
        PackingListDto editedPackingListDto = PackingListDto.builder()
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .packingItemList(packingItemList)
                .build();
        when(packingListRepository.findById(pathId)).thenReturn(Optional.ofNullable(
                PackingList.builder()
                        .id("123")
                        .destination("Bayreuth")
                        .packingItemList(List.of(PackingItem.builder()
                                        .id("1")
                                        .name("swimwear")
                                        .build(),
                                PackingItem.builder()
                                        .id("2")
                                        .name("passport")
                                        .build()))
                        .build()));
        PackingList updatedPackingList = PackingList.builder()
                .id("123")
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .packingItemList(packingItemList)
                .build();

        when(packingListRepository.save(updatedPackingList)).thenReturn(PackingList.builder()
                .id("123")
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .packingItemList(List.of(PackingItem.builder()
                                .id("1")
                                .name("swimwear")
                                .build(),
                        PackingItem.builder()
                                .id("2")
                                .name("passport")
                                .build()))
                .build());
        //WHEN
        PackingList actual = packingListService.updatePackingListById(pathId, editedPackingListDto);
        //THEN
        PackingList expected = PackingList.builder()
                .id("123")
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .packingItemList(List.of(PackingItem.builder()
                        .id("1")
                        .name("swimwear")
                        .build(),
                PackingItem.builder()
                        .id("2")
                        .name("passport")
                        .build()))
                .build();
        assertEquals(expected, actual);
        verify(packingListRepository).save(updatedPackingList);
    }

    @Test
    void updatePackingListById_whenIdIsNotValid_shouldThrowNoSuchElementException() {
        //GIVEN
        String pathId = "122";
        PackingListDto editedPackingListDto = PackingListDto.builder()
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-10-02"))
                .build();
        when(packingListRepository.findById(pathId)).thenReturn(Optional.empty());
        //WHEN //THEN
        assertThrows(NoSuchElementException.class, () -> packingListService.updatePackingListById(pathId, editedPackingListDto));
        verify(packingListRepository).findById(pathId);
    }

    @Test
    void deletePackingListById_whenIdIsValid() {
        //GIVEN
        PackingList packingList = PackingList.builder()
                .id("123")
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .build();
        //WHEN
        packingListService.deletePackingListById(packingList.getId());
        //THEN
        verify(packingListRepository).deleteById("123");
    }

    @Test
    void addNewPackingItem_whenNameIsGivenAndActualListIsNotNull_ShouldReturnUpdatedPackingListWithAllItems() {
        //GIVEN
        String pathId = "123";
        CreatePackingItemDto createPackingItemDto = CreatePackingItemDto.builder()
                .name("swimwear")
                .build();
        when(idService.generateId()).thenReturn("2");

        when(packingListRepository.findById(pathId)).thenReturn(Optional.ofNullable(
                PackingList.builder()
                        .id("123")
                        .destination("Tokyo")
                        .dateOfArrival(LocalDate.parse("2022-10-03"))
                        .packingItemList(List.of(PackingItem.builder()
                                        .id("1")
                                        .name("passport")
                                        .build()))
                        .build()));

        PackingList updatedPackingList = PackingList.builder()
                .id("123")
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .packingItemList(List.of(PackingItem.builder()
                                .id("1")
                                .name("passport")
                                .build(),
                        PackingItem.builder()
                                .id("2")
                                .name("swimwear")
                                .build()))
                .build();

        when(packingListRepository.save(updatedPackingList)).thenReturn(PackingList.builder()
                .id("123")
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .packingItemList(List.of(PackingItem.builder()
                                .id("1")
                                .name("passport")
                                .build(),
                        PackingItem.builder()
                                .id("2")
                                .name("swimwear")
                                .build()))
                .build());
        //WHEN
        PackingList actual = packingListService.addNewPackingItem(pathId, createPackingItemDto);
        //THEN
        PackingList expected = PackingList.builder()
                .id("123")
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .packingItemList(List.of(PackingItem.builder()
                                .id("1")
                                .name("passport")
                                .build(),
                        PackingItem.builder()
                                .id("2")
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
        String pathId = "123";
        CreatePackingItemDto createPackingItemDto = CreatePackingItemDto.builder()
                .name("swimwear")
                .build();
        when(idService.generateId()).thenReturn("2");

        when(packingListRepository.findById(pathId)).thenReturn(Optional.ofNullable(
                PackingList.builder()
                        .id("123")
                        .destination("Tokyo")
                        .dateOfArrival(LocalDate.parse("2022-10-03"))
                        .build()));

        PackingList updatedPackingList = PackingList.builder()
                .id("123")
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .packingItemList(List.of(
                        PackingItem.builder()
                                .id("2")
                                .name("swimwear")
                                .build()))
                .build();

        when(packingListRepository.save(updatedPackingList)).thenReturn(PackingList.builder()
                .id("123")
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .packingItemList(List.of(
                        PackingItem.builder()
                                .id("2")
                                .name("swimwear")
                                .build()))
                .build());
        //WHEN
        PackingList actual = packingListService.addNewPackingItem(pathId, createPackingItemDto);
        //THEN
        PackingList expected = PackingList.builder()
                .id("123")
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .packingItemList(List.of(
                        PackingItem.builder()
                                .id("2")
                                .name("swimwear")
                                .build()))
                .build();
        assertEquals(expected, actual);
        verify(packingListRepository).save(updatedPackingList);
        verify(idService).generateId();
    }

    @Test
    void addNewPackingItem_whenNameIsNull_ShouldThrowIllegalArgumentException() {
        //GIVEN
        String pathId = "123";
        CreatePackingItemDto createPackingItemDto = CreatePackingItemDto.builder()
                .build();

        when(packingListRepository.findById(pathId)).thenReturn(Optional.ofNullable(
                PackingList.builder()
                        .id("123")
                        .destination("Tokyo")
                        .dateOfArrival(LocalDate.parse("2022-10-03"))
                        .build()));

        //WHEN//THEN
        assertThrows(IllegalArgumentException.class, () -> packingListService.addNewPackingItem(pathId, createPackingItemDto));
    }

    @Test
    void generateNewItem_whenNameIsGiven_ShouldReturnPackingItem() {
        //GIVEN
        CreatePackingItemDto packingListDto = CreatePackingItemDto.builder()
                .name("Tokyo")
                .build();
        when(idService.generateId()).thenReturn("2");
        //WHEN
        PackingItem actual = packingListService.generateNewItem(packingListDto);
        //THEN
        PackingItem expected = PackingItem.builder()
                .id("2")
                .name("Tokyo")
                .build();
        assertEquals(expected, actual);
        verify(idService).generateId();
    }

    @Test
    void generateNewItem_whenNameIsNotGiven_ShouldThrowIllegalArgumentException() {
        //GIVEN
        CreatePackingItemDto packingListDto = CreatePackingItemDto.builder()
                .name(null)
                .build();
        //WHEN//THEN
        assertThrows(IllegalArgumentException.class, () -> packingListService.generateNewItem(packingListDto));
    }

    @Test
    void deleteItemFromPackingList_whenIdOfListAndItemAreValid() {
        //GIVEN
        PackingList packingList = PackingList.builder()
                .id("123")
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .packingItemList(List.of(PackingItem.builder()
                                .id("1")
                                .name("passport")
                                .build()))
                .build();
        when(packingListRepository.findById("123")).thenReturn(Optional.ofNullable(packingList));
        PackingList updatedPackingList = PackingList.builder()
                .id("123")
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .packingItemList(List.of())
                .build();
        when(packingListRepository.save(updatedPackingList)).thenReturn(PackingList.builder()
                .id("123")
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .packingItemList(List.of())
                .build());
        //WHEN
        assertNotNull(packingList);
        assertNotNull(packingList.getId());
        PackingList actual = packingListService.deleteItemFromPackingList(packingList.getId(), "1");
        //THEN
        verify(packingListRepository).save(updatedPackingList);
        PackingList expected = PackingList.builder()
                .id("123")
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .packingItemList(List.of())
                .build();
        assertEquals(expected, actual);
    }

    @Test
    void deleteItemFromPackingList_whenIdOfListIsNotValid_ShouldThrowNoSuchElementException() {
        //GIVEN
        when(packingListRepository.findById("123")).thenReturn(Optional.empty());
        //WHEN //THEN
        assertThrows(NoSuchElementException.class, () -> packingListService.deleteItemFromPackingList("123", "1"));
    }

    @Test
    void deleteItemFromPackingList_whenListOfItemsIsNull_ShouldThrowNoSuchElementException() {
        //GIVEN
        PackingList packingList = PackingList.builder()
                .id("123")
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .build();
        when(packingListRepository.findById("123")).thenReturn(Optional.ofNullable(packingList));
        //WHEN //THEN
        assertThrows(NoSuchElementException.class, () -> packingListService.deleteItemFromPackingList("123", "1"));
    }

    @Test
    void updatePackingItem_whenIdOfListAndItemIsValid_ShouldReturnUpdatedList() {
        //GIVEN
        String listId = "123";
        String itemId = "2";
        CreatePackingItemDto itemToUpdate = CreatePackingItemDto.builder()
                .name("passport")
                .build();
        PackingList existingPackingList = PackingList.builder()
                .id("123")
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .packingItemList(List.of(
                        PackingItem.builder()
                                .id("2")
                                .name("swimwear")
                                .build()))
                .build();
        when(packingListRepository.findById(existingPackingList.getId())).thenReturn(Optional.of(existingPackingList));
        PackingList updatedPackingList = PackingList.builder()
                .id("123")
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .packingItemList(List.of(
                        PackingItem.builder()
                                .id("2")
                                .name("passport")
                                .build()))
                .build();
        when(packingListRepository.save(updatedPackingList)).thenReturn(PackingList.builder()
                .id("123")
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .packingItemList(List.of(
                        PackingItem.builder()
                                .id("2")
                                .name("passport")
                                .build()))
                .build());
        //WHEN
        PackingList actual = packingListService.updatePackingItem(listId, itemId, itemToUpdate);
        //THEN
        PackingList expected = PackingList.builder()
                .id("123")
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .packingItemList(List.of(
                        PackingItem.builder()
                                .id("2")
                                .name("passport")
                                .build()))
                .build();
        assertEquals(expected, actual);
        verify(packingListRepository).findById(listId);
        verify(packingListRepository).save(updatedPackingList);
    }

    @Test
    void updatePackingItem_whenIdOfItemIsNotValid_ShouldThrowNoSuchElementException() {
        //GIVEN
        String listId = "123";
        String itemId = "1";
        CreatePackingItemDto itemToUpdate = CreatePackingItemDto.builder()
                .name("passport")
                .build();
        PackingList existingPackingList = PackingList.builder()
                .id("123")
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .packingItemList(List.of(
                        PackingItem.builder()
                                .id("2")
                                .name("swimwear")
                                .build()))
                .build();
        assertNotNull(existingPackingList);
        when(packingListRepository.findById(existingPackingList.getId())).thenReturn(Optional.of(existingPackingList));
        //WHEN //THEN
        assertThrows(NoSuchElementException.class, () -> packingListService.updatePackingItem(listId, itemId, itemToUpdate));
        verify(packingListRepository).findById(listId);
    }
}