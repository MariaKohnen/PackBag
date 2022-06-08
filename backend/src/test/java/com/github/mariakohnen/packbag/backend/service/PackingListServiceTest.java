package com.github.mariakohnen.packbag.backend.service;

import com.github.mariakohnen.packbag.backend.dto.PackingItemDto;
import com.github.mariakohnen.packbag.backend.dto.NewPackingListDto;
import com.github.mariakohnen.packbag.backend.dto.UpdatePackingListDto;
import com.github.mariakohnen.packbag.backend.model.PackingItem;
import com.github.mariakohnen.packbag.backend.model.PackingList;
import com.github.mariakohnen.packbag.backend.repository.PackingListRepository;
import org.junit.jupiter.api.AfterEach;
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

    String listId = "1";
    String itemId = "01";
    String invalidId = "123";

    @AfterEach
    public void verifyInteractions() {
        verifyNoMoreInteractions(packingListRepository, idService);
    }

    @Test
    void getAllPackingLists() {
        //GIVEN
        when(packingListRepository.findAll()).thenReturn(List.of(packingListWithOneItem(), packingListWithoutItem()));
        //WHEN
        List<PackingList> actual = packingListService.getAllPackingLists();
        //THEN
        List<PackingList> expected = List.of((packingListWithOneItem()), packingListWithoutItem());
        assertEquals(expected, actual);
        verify(packingListRepository).findAll();
    }

    @Test
    void getPackingListById_whenIdIsValid_shouldReturnPackingList() {
        //GIVEN
        when(packingListRepository.findById(listId)).thenReturn(Optional.ofNullable(packingListWithOneItem()));
        //WHEN
        PackingList actual = packingListService.getPackingListById(listId);
        //THEN
        PackingList expected = packingListWithOneItem();
        assertEquals(expected, actual);
        verify(packingListRepository).findById(listId);
    }

    @Test
    void getPackingListById_whenIdIsNotValid_shouldThrowException() {
        //GIVEN
        when(packingListRepository.findById(invalidId)).thenReturn(Optional.empty());
        //WHEN//THEN
        assertThrows(NoSuchElementException.class, () -> packingListService.getPackingListById(invalidId));
        verify(packingListRepository).findById(invalidId);
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
        PackingList actual = packingListService.addNewPackingList(newPackingListDto());
        //THEN
        PackingList expected = PackingList.builder()
                .id("1")
                .destination("Kyoto")
                .build();
        assertEquals(expected, actual);
        verify(packingListRepository).insert(packingListToAdd);
    }

    @Test
    void addNewPackingList_whenNameIsNull_shouldThrowException() {
        //GIVEN
        NewPackingListDto emptyList = NewPackingListDto.builder()
                .build();
        //WHEN//THEN
        assertThrows(IllegalArgumentException.class, () -> packingListService.addNewPackingList(emptyList));
    }

    @Test
    void addNewPackingList_whenNameIsAnEmptyString_shouldThrowException() {
        //GIVEN
        NewPackingListDto itemWithEmptyString = NewPackingListDto.builder()
                .destination("   ")
                .build();
        //WHEN//THEN
        assertThrows(IllegalArgumentException.class, () -> packingListService.addNewPackingList(itemWithEmptyString));
    }

    @Test
    void updatePackingListById_whenIdIsValid_shouldReturnUpdatedPackingList() {
        //GIVEN
        when(packingListRepository.findById(listId)).thenReturn(Optional.ofNullable(packingListWithOneItem()));

        when(packingListRepository.save(updatedPackingList())).thenReturn(updatedPackingList());
        //WHEN
        PackingList actual = packingListService.updatePackingListById(listId, updatePackingListDto());
        //THEN
        PackingList expected = PackingList.builder()
                .id("1")
                .dateOfArrival(LocalDate.parse("2022-09-24"))
                .destination("Tokyo")
                .packingItemList(List.of(PackingItem.builder()
                        .id("01")
                        .name("passport")
                        .status("Open")
                        .category("no category")
                        .build()))
                .build();
        assertEquals(expected, actual);
        verify(packingListRepository).findById("1");
        verify(packingListRepository).save(updatedPackingList());
    }

    @Test
    void updatePackingListById_whenDestinationIsNull_shouldThrowNoSuchElementException() {
        //GIVEN
        UpdatePackingListDto updatedListWithoutName = UpdatePackingListDto.builder()
                .dateOfArrival(LocalDate.parse("2022-09-24"))
                .build();
        when(packingListRepository.findById(listId)).thenReturn(Optional.ofNullable(packingListWithOneItem()));
        //WHEN //THEN
        assertThrows(IllegalArgumentException.class, () -> packingListService.updatePackingListById(listId, updatedListWithoutName));
        verify(packingListRepository).findById(listId);
    }

    @Test
    void updatePackingListById_whenDestinationIsEmpty_shouldThrowNoSuchElementException() {
        //GIVEN
        UpdatePackingListDto updatedListWithoutName = UpdatePackingListDto.builder()
                .destination("   ")
                .dateOfArrival(LocalDate.parse("2022-09-24"))
                .build();
        when(packingListRepository.findById(listId)).thenReturn(Optional.ofNullable(packingListWithOneItem()));
        //WHEN //THEN
        assertThrows(IllegalArgumentException.class, () -> packingListService.updatePackingListById(listId, updatedListWithoutName));
        verify(packingListRepository).findById(listId);
    }

    @Test
    void updatePackingListById_whenIdIsNotValid_shouldThrowNoSuchElementException() {
        //GIVEN
        UpdatePackingListDto updatedPackingListDto = UpdatePackingListDto.builder()
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-09-24"))
                .build();
        when(packingListRepository.findById(invalidId)).thenReturn(Optional.empty());
        //WHEN //THEN
        assertNotNull(updatePackingListDto());
        assertThrows(NoSuchElementException.class, () -> packingListService.updatePackingListById(invalidId, updatedPackingListDto));
        verify(packingListRepository).findById(invalidId);
    }

    @Test
    void deletePackingListById_whenIdIsValid() {
        //WHEN
        packingListService.deletePackingListById(listId);
        //THEN
        verify(packingListRepository).deleteById(listId);
    }

    @Test
    void getPackingItemById_whenIdIsValid_shouldReturnItem() {
        //GIVEN
        when(packingListRepository.findById(listId)).thenReturn(Optional.ofNullable(packingListWithTwoItems()));
        //WHEN
        PackingItem actual = packingListService.getPackingItemById(listId, itemId);
        //THEN
        PackingItem expected = PackingItem.builder()
                .id("01")
                .name("passport")
                .status("Open")
                .category("no category")
                .build();
        assertEquals(expected, actual);
        verify(packingListRepository).findById(listId);
    }

    @Test
    void getPackingItemById_whenIdIsNotValid_shouldThrowNoSuchElementException() {
        //GIVEN
        when(packingListRepository.findById(listId)).thenReturn(Optional.ofNullable(packingListWithTwoItems()));
        //WHEN //THEN
        assertThrows(NoSuchElementException.class, () -> packingListService.getPackingItemById(listId, invalidId));
        verify(packingListRepository).findById(listId);
    }

    @Test
    void addNewPackingItem_whenNameIsGivenAndActualListIsNotNull_ShouldReturnUpdatedPackingListWithAllItems() {
        //GIVEN
        when(idService.generateId()).thenReturn("02");

        when(packingListRepository.findById(listId)).thenReturn(Optional.ofNullable(packingListWithOneItem()));

        when(packingListRepository.save(packingListWithOneItemWithAndOneWithoutCategory()))
                .thenReturn(packingListWithOneItemWithAndOneWithoutCategory());
        //WHEN
        PackingList actual = packingListService.addNewPackingItem(listId, packingItemDto());
        //THEN
        PackingList expected = packingListWithOneItemWithAndOneWithoutCategory();
        assertEquals(expected, actual);
        verify(packingListRepository).findById(listId);
        verify(packingListRepository).save(packingListWithOneItemWithAndOneWithoutCategory());
        verify(idService).generateId();
    }

    @Test
    void addNewPackingItem_whenNameIsGivenAndActualListIsNull_ShouldReturnUpdatedPackingListWithAllItems() {
        //GIVEN
        when(idService.generateId()).thenReturn("01");

        when(packingListRepository.findById(listId)).thenReturn(Optional.ofNullable(packingListWithoutItem()));

        when(packingListRepository.save(packingListWithOneItem())).thenReturn(packingListWithOneItem());
        //WHEN
        PackingList actual = packingListService.addNewPackingItem(listId, packingItemDto2());
        //THEN
        PackingList expected = packingListWithOneItem();
        assertEquals(expected, actual);
        verify(packingListRepository).findById(listId);
        verify(packingListRepository).save(packingListWithOneItem());
        verify(idService).generateId();
    }

    @Test
    void addNewPackingItem_whenNameIsNull_ShouldThrowIllegalArgumentException() {
        //GIVEN
        PackingItemDto emptyItem = PackingItemDto.builder()
                .build();
        when(packingListRepository.findById(listId)).thenReturn(Optional.ofNullable(packingListWithOneItem()));
        //WHEN//THEN
        assertThrows(IllegalArgumentException.class, () -> packingListService.addNewPackingItem(listId, emptyItem));
        verify(packingListRepository).findById(listId);
    }

    @Test
    void addNewPackingItem_whenNameIsAnEmptyString_ShouldThrowIllegalArgumentException() {
        //GIVEN
        PackingItemDto itemWithEmptyString = PackingItemDto.builder()
                .name("    ")
                .build();
        when(packingListRepository.findById(listId)).thenReturn(Optional.ofNullable(packingListWithOneItem()));
        //WHEN//THEN
        assertThrows(IllegalArgumentException.class, () -> packingListService.addNewPackingItem(listId, itemWithEmptyString));
        verify(packingListRepository).findById(listId);
    }

    @Test
    void generateNewItem_whenNameIsGiven_ShouldReturnPackingItem() {
        //GIVEN
        when(idService.generateId()).thenReturn("02");
        //WHEN
        PackingItem actual = packingListService.generateNewItem(packingItemDto2());
        //THEN
        PackingItem expected = PackingItem.builder()
                .id("02")
                .name("passport")
                .status("Open")
                .category("no category")
                .build();
        assertEquals(expected, actual);
        verify(idService).generateId();
    }

    @Test
    void generateNewItem_whenNameIsNotGiven_ShouldThrowIllegalArgumentException() {
        //GIVEN
        PackingItemDto emptyItem = PackingItemDto.builder()
                .build();
        //WHEN//THEN
        assertThrows(IllegalArgumentException.class, () -> packingListService.generateNewItem(emptyItem));
    }

    @Test
    void deleteItemFromPackingList_whenIdOfListAndItemAreValid() {
        //GIVEN
        when(packingListRepository.findById(listId)).thenReturn(Optional.ofNullable(packingListWithTwoItems()));
        when(packingListRepository.save(packingListWithOneItem())).thenReturn(packingListWithOneItem());
        //WHEN
        assertNotNull(packingListWithOneItem());
        PackingList actual = packingListService.deleteItemFromPackingList(listId, "02");
        //THEN
        PackingList expected = packingListWithOneItem();
        assertEquals(expected, actual);
        verify(packingListRepository).findById(listId);
        verify(packingListRepository).save(packingListWithOneItem());
    }

    @Test
    void deleteItemFromPackingList_whenIdOfListIsNotValid_ShouldThrowNoSuchElementException() {
        //GIVEN
        when(packingListRepository.findById(invalidId)).thenReturn(Optional.empty());
        //WHEN //THEN
        assertThrows(NoSuchElementException.class, () -> packingListService.deleteItemFromPackingList(invalidId, itemId));
        verify(packingListRepository).findById(invalidId);
    }

    @Test
    void deleteItemFromPackingList_whenListOfItemsIsNull_ShouldThrowNoSuchElementException() {
        when(packingListRepository.findById(listId)).thenReturn(Optional.ofNullable(packingListWithoutItem()));
        //WHEN //THEN
        assertThrows(NoSuchElementException.class, () -> packingListService.deleteItemFromPackingList(listId, itemId));
        verify(packingListRepository).findById(listId);
    }

    @Test
    void updatePackingItem_whenIdOfListAndItemIsValid_ShouldReturnUpdatedList() {
        //GIVEN
        when(packingListRepository.findById(listId)).thenReturn(Optional.of(packingListWithOneItem()));
        PackingList updatedPackingList = PackingList.builder()
                .id("1")
                .destination("Kyoto")
                .dateOfArrival(LocalDate.parse("2022-09-03"))
                .packingItemList(List.of(
                        PackingItem.builder()
                                .id("01")
                                .name("swimwear")
                                .status("Open")
                                .category("clothing")
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
                                .status("Open")
                                .category("clothing")
                                .build()))
                .build());
        //WHEN
        PackingList actual = packingListService.updatePackingItem(listId, itemId, packingItemDto());
        //THEN
        PackingList expected = PackingList.builder()
                .id("1")
                .destination("Kyoto")
                .dateOfArrival(LocalDate.parse("2022-09-03"))
                .packingItemList(List.of(
                        PackingItem.builder()
                                .id("01")
                                .name("swimwear")
                                .status("Open")
                                .category("clothing")
                                .build()))
                .build();
        assertEquals(expected, actual);
        verify(packingListRepository).findById(listId);
        verify(packingListRepository).save(updatedPackingList);
    }

    @Test
    void updatePackingItem_whenIdOfItemIsNotValid_ShouldThrowNoSuchElementException() {
        //GIVEN
        PackingItemDto updatedItemDto = PackingItemDto.builder()
                .name("swimwear")
                .status("DONE")
                .build();
        when(packingListRepository.findById(listId)).thenReturn(Optional.of(packingListWithOneItem()));
        //WHEN //THEN
        assertNotNull(packingItemDto().getName());
        assertThrows(NoSuchElementException.class, () -> packingListService.updatePackingItem(listId, invalidId, updatedItemDto));
        verify(packingListRepository).findById(listId);
    }

    @Test
    void updatePackingItem_whenNameOfItemIsNotGiven_shouldThrowIllegalArgumentException() {
        //GIVEN
        PackingItemDto emptyItem = PackingItemDto.builder()
                .build();
        //WHEN //THEN
        assertThrows(IllegalArgumentException.class, () -> packingListService.updatePackingItem(listId, itemId, emptyItem));
    }

    public NewPackingListDto newPackingListDto() {
        return NewPackingListDto.builder()
                .destination("Kyoto")
                .build();
    }

    public UpdatePackingListDto updatePackingListDto() {
        return UpdatePackingListDto.builder()
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-09-24"))
                .build();
    }

    public PackingList packingListWithoutItem() {
        return PackingList.builder()
                .id("1")
                .dateOfArrival(LocalDate.parse("2022-09-03"))
                .destination("Kyoto")
                .build();
    }

    public PackingList packingListWithOneItem() {
        return PackingList.builder()
                .id("1")
                .dateOfArrival(LocalDate.parse("2022-09-03"))
                .destination("Kyoto")
                .packingItemList(List.of(PackingItem.builder()
                        .id("01")
                        .name("passport")
                        .status("Open")
                        .category("no category")
                        .build()))
                .build();
    }

    public PackingList packingListWithTwoItems() {
        return PackingList.builder()
                .id("1")
                .dateOfArrival(LocalDate.parse("2022-09-03"))
                .destination("Kyoto")
                .packingItemList(List.of(PackingItem.builder()
                                .id("01")
                                .name("passport")
                                .status("Open")
                                .category("no category")
                                .build(),
                        PackingItem.builder()
                                .id("02")
                                .name("swimwear")
                                .status("Open")
                                .category("clothing")
                                .build()))
                .build();
    }

    public PackingList packingListWithOneItemWithAndOneWithoutCategory() {
        return PackingList.builder()
                .id("1")
                .dateOfArrival(LocalDate.parse("2022-09-03"))
                .destination("Kyoto")
                .packingItemList(List.of(PackingItem.builder()
                                .id("01")
                                .name("passport")
                                .status("Open")
                                .category("no category")
                                .build(),
                        PackingItem.builder()
                                .id("02")
                                .name("swimwear")
                                .status("Open")
                                .category("clothing")
                                .build()))
                .build();
    }

    public PackingList updatedPackingList() {
        return PackingList.builder()
                .id("1")
                .dateOfArrival(LocalDate.parse("2022-09-24"))
                .destination("Tokyo")
                .packingItemList(List.of(PackingItem.builder()
                        .id("01")
                        .name("passport")
                        .status("Open")
                        .category("no category")
                        .build()))
                .build();
    }

    public PackingItemDto packingItemDto() {
        return PackingItemDto.builder()
                .name("swimwear")
                .status("Open")
                .category("clothing")
                .build();
    }

    public PackingItemDto packingItemDto2() {
        return PackingItemDto.builder()
                .name("passport")
                .status("Open")
                .category("no category")
                .build();
    }
}
