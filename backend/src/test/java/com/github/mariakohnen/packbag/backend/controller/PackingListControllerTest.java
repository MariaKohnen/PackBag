package com.github.mariakohnen.packbag.backend.controller;

import com.github.mariakohnen.packbag.backend.dto.NewPackingItemDto;
import com.github.mariakohnen.packbag.backend.dto.NewPackingListDto;
import com.github.mariakohnen.packbag.backend.dto.UpdatePackingListDto;
import com.github.mariakohnen.packbag.backend.model.PackingItem;
import com.github.mariakohnen.packbag.backend.model.PackingList;
import com.github.mariakohnen.packbag.backend.repository.PackingListRepository;
import com.github.mariakohnen.packbag.backend.service.IdService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PackingListControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PackingListRepository packingListRepository;

    @MockBean
    private IdService idService;

    @BeforeEach
    public void cleanUp() {
        packingListRepository.deleteAll();
    }

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
                .destination("Tokyo")
                .build();
        packingListRepository.insert(packingListWithOneItem);
        packingListRepository.insert(packingList2);
        //WHEN
        List<PackingList> actual = webTestClient.get()
                .uri("/api/packinglists")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(PackingList.class)
                .returnResult()
                .getResponseBody();
        //THEN
        List<PackingList> expected = List.of(PackingList.builder()
                        .id("1")
                        .dateOfArrival(LocalDate.parse("2022-09-03"))
                        .destination("Kyoto")
                        .packingItemList(List.of(PackingItem.builder()
                                .id("01")
                                .name("passport")
                                .build()))
                        .build(),
                PackingList.builder()
                        .id("2")
                        .destination("Tokyo")
                        .build());
        assertEquals(expected, actual);
    }

    @Test
    void getPackingListById_whenIdIsValid_shouldReturnPackingList() {
        //GIVEN
        packingListRepository.insert(packingListWithOneItem);

        //WHEN
        assertNotNull(packingListWithOneItem);
        PackingList actual = webTestClient.get()
                .uri("/api/packinglists/" + packingListWithOneItem.getId())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PackingList.class)
                .returnResult()
                .getResponseBody();
        //THEN
        assertNotNull(actual);
        PackingList expected = PackingList.builder()
                .id("1")
                .dateOfArrival(LocalDate.parse("2022-09-03"))
                .destination("Kyoto")
                .packingItemList(List.of(PackingItem.builder()
                        .id("01")
                        .name("passport")
                        .build()))
                .build();
        assertEquals(expected, actual);
    }

    @Test
    void getPackingListById_whenIdIsNotValid_shouldThrowException() {
        //GIVEN
        packingListRepository.insert(packingListWithOneItem);
        //WHEN
        assertNotNull(packingListWithOneItem);
        webTestClient.get()
                .uri("/api/packinglists/" + invalidId)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void postNewPackingList_whenListIsNotEmpty_shouldReturnNewPackingList() {
        //WHEN
        PackingList actual = webTestClient.post()
                .uri("/api/packinglists")
                .bodyValue(newPackingListDto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PackingList.class)
                .returnResult()
                .getResponseBody();
        //THEN
        assertNotNull(actual);
        assertNotNull(actual.getId());
        PackingList expected = PackingList.builder()
                .id(actual.getId())
                .destination("Kyoto")
                .build();
        assertEquals(24, actual.getId().length());
        assertEquals(expected, actual);
    }

    @Test
    void postNewPackingList_whenNameIsNotGiven_shouldReturnException() {
        //GIVEN
        NewPackingListDto emptyPackingList = NewPackingListDto.builder()
                .build();
        //WHEN//THEN
        webTestClient.post()
                .uri("/api/packinglists")
                .bodyValue(emptyPackingList)
                .exchange()
                .expectStatus().isEqualTo(400);
    }

    @Test
    void updateExistingPackingListById_whenIdIsValid_shouldReturnUpdatedPackingList() {
        //GIVEN
        packingListRepository.insert(packingListWithOneItem);
        //WHEN
        assertNotNull(packingListWithOneItem);
        PackingList actual = webTestClient.put()
                .uri("/api/packinglists/" + listId)
                .bodyValue(updatePackingListDto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PackingList.class)
                .returnResult()
                .getResponseBody();
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
    }

    @Test
    void updateExistingPackingListById_whenIdIsNotValid_shouldThrowException() {
        //GIVEN
        packingListRepository.insert(packingListWithOneItem);
        //WHEN
        assertNotNull(packingListWithOneItem);
        String invalidId = "123";
        webTestClient.put()
                .uri("/api/packinglists/" + invalidId)
                .bodyValue(updatePackingListDto)
                .exchange()
                .expectStatus().isEqualTo(404);
    }

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
    void deletePackingListByID_whenIdIsValid() {
        //GIVEN
        packingListRepository.insert(packingListWithOneItem);
        //WHEN //THEN
        assertNotNull(packingListWithOneItem);
        webTestClient.delete()
                .uri("/api/packinglists/" + listId)
                .exchange()
                .expectStatus().is2xxSuccessful();
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