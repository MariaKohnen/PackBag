package com.github.mariakohnen.packbag.backend.controller;

import com.github.mariakohnen.packbag.backend.dto.NewPackingListDto;
import com.github.mariakohnen.packbag.backend.dto.UpdatePackingListDto;
import com.github.mariakohnen.packbag.backend.model.PackingItem;
import com.github.mariakohnen.packbag.backend.model.PackingList;
import com.github.mariakohnen.packbag.backend.repository.PackingListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PackingListControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    PackingListRepository packingListRepository;

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

    String listId = "1";
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


}