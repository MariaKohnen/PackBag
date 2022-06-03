package com.github.mariakohnen.packbag.backend.controller;

import com.github.mariakohnen.packbag.backend.dto.NewPackingItemDto;
import com.github.mariakohnen.packbag.backend.model.PackingItem;
import com.github.mariakohnen.packbag.backend.model.PackingList;
import com.github.mariakohnen.packbag.backend.service.IdService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class PackingItemsControllerTest extends PackingListControllerTest{

    @MockBean
    IdService idService;

    NewPackingItemDto newPackingItemDto = NewPackingItemDto.builder()
            .name("swimwear")
            .build();

    NewPackingItemDto newPackingItemDto2 = NewPackingItemDto.builder()
            .name("passport")
            .build();

    String itemId = "01";

    @Test
    void addPackingItemToPackingList_whenNameIsGiveAndActualListIsNull_shouldReturnPackingList() {
        //GIVEN
        PackingList packingList = PackingList.builder()
                .id("1")
                .dateOfArrival(LocalDate.parse("2022-09-03"))
                .destination("Kyoto")
                .packingItemList(List.of())
                .build();
        packingListRepository.insert(packingList);
        //WHEN
        assertNotNull(packingList);
        when(idService.generateId()).thenReturn("01");
        PackingList actual = webTestClient.put()
                .uri("/api/packinglists/" + listId + "/packingitems")
                .bodyValue(newPackingItemDto2)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PackingList.class)
                .returnResult()
                .getResponseBody();
        //THEN
        PackingList expected = packingListWithOneItem;
        assertEquals(expected, actual);
    }

    @Test
    void addPackingItemToPackingList_whenNameIsNotGiven_shouldThrowNoSuchElementException() {
        //GIVEN
        packingListRepository.insert(packingListWithOneItem);
        //WHEN
        assertNotNull(packingListWithOneItem);
        NewPackingItemDto emptyItem = NewPackingItemDto.builder()
                .build();
        webTestClient.put()
                .uri("/api/packinglists/" + listId + "/packingitems")
                .bodyValue(emptyItem)
                .exchange()
                .expectStatus().isEqualTo(400);
    }
    @Test
    void deleteItemFromPackingList_whenIdOfListAndItemAreValid() {
        //GIVEN
        packingListRepository.insert(packingListWithTwoItems);
        String itemToDelete = "02";
        //WHEN
        assertNotNull(packingListWithTwoItems);
        PackingList actual = webTestClient.delete()
                .uri("/api/packinglists/" + listId + "/packingitems/" + itemToDelete)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PackingList.class)
                .returnResult()
                .getResponseBody();
        //THEN
        PackingList excepted = packingListWithOneItem;
        assertEquals(excepted, actual);
    }

    @Test
    void deleteItemFromPackingList_whenIdOfListIsNotValid_ShouldThrowNoSuchElementException() {
        //GIVEN
        packingListRepository.insert(packingListWithTwoItems);
        String itemToDelete = "02";
        String wrongListId = "2";
        //WHEN //THEN
        assertNotNull(packingListWithTwoItems);
        webTestClient.delete()
                .uri("/api/packinglists/" + wrongListId + "/packingitems/" + itemToDelete)
                .exchange()
                .expectStatus().isEqualTo(404);
    }

    @Test
    void deleteItemFromPackingList_whenListHasNoItems_ShouldThrowNoSuchElementException() {//GIVEN
        PackingList packingListWithNoItems = PackingList.builder()
                .id("1")
                .destination("Kyoto")
                .dateOfArrival(LocalDate.parse("2022-09-03"))
                .build();
        packingListRepository.insert(packingListWithNoItems);
        //WHEN //THEN
        assertNotNull(packingListWithNoItems);
        webTestClient.delete()
                .uri("/api/packinglists/" + listId + "/packingitems/" + itemId)
                .exchange()
                .expectStatus().isEqualTo(404);
    }

    @Test
    void updateExistingPackingItemOfList_whenIdOfListAndItemIsValid_shouldReturnUpdatedList() {
        //GIVEN
        packingListRepository.insert(packingListWithTwoItems);
        //WHEN
        assertNotNull(packingListWithTwoItems);
        PackingList actual = webTestClient.put()
                .uri("/api/packinglists/" + listId + "/packingitems/" + itemId)
                .bodyValue(newPackingItemDto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PackingList.class)
                .returnResult()
                .getResponseBody();
        //THEN
        PackingList excepted = PackingList.builder()
                .id("1")
                .dateOfArrival(LocalDate.parse("2022-09-03"))
                .destination("Kyoto")
                .packingItemList(List.of(PackingItem.builder()
                                .id("01")
                                .name("swimwear")
                                .build(),
                        PackingItem.builder()
                                .id("02")
                                .name("swimwear")
                                .build()))
                .build();
        assertEquals(excepted, actual);
    }

    @Test
    void updateExistingPackingItemOfList_whenIdIsNotValid_shouldThrowNoSuchElementException() {
        //GIVEN
        packingListRepository.insert(packingListWithTwoItems);
        String invalidItemId = "1";
        //WHEN //THEN
        assertNotNull(packingListWithTwoItems);
        webTestClient.put()
                .uri("/api/packinglists/" + listId + "/packingitems/" + invalidItemId)
                .bodyValue(newPackingItemDto)
                .exchange()
                .expectStatus().isEqualTo(404);
    }

    @Test
    void updateExistingPackingItemOfList_whenNameOfItemIsNotGiven_shouldThrowIllegalArgumentException() {
        //GIVEN
        NewPackingItemDto emptyItem = NewPackingItemDto.builder()
                .build();
        //WHEN //THEN
        webTestClient.put()
                .uri("/api/packinglists/" + listId + "/packingitems/" + itemId)
                .bodyValue(emptyItem)
                .exchange()
                .expectStatus().isEqualTo(400);
    }
}
