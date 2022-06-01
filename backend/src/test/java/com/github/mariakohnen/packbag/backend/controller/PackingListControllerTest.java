package com.github.mariakohnen.packbag.backend.controller;

import com.github.mariakohnen.packbag.backend.dto.CreatePackingItemDto;
import com.github.mariakohnen.packbag.backend.dto.PackingListDto;
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

    @Test
    void getAllPackingLists() {
        //GIVEN
        PackingList packingList1 = PackingList.builder()
                .id("1")
                .destination("Bayreuth")
                .dateOfArrival(LocalDate.parse("2022-05-01"))
                .packingItemList(List.of(PackingItem.builder()
                        .name("swimwear")
                        .build()))
                .build();
        PackingList packingList2 = PackingList.builder()
                .id("2")
                .destination("Frankfurt")
                .build();
        packingListRepository.insert(packingList1);
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
        List<PackingList> expected = List.of((PackingList.builder()
                        .id("1")
                        .destination("Bayreuth")
                        .dateOfArrival(LocalDate.parse("2022-05-01"))
                        .packingItemList(List.of(PackingItem.builder()
                                .name("swimwear")
                                .build()))
                        .build()),
                PackingList.builder()
                        .id("2")
                        .destination("Frankfurt")
                        .build());
        assertEquals(expected, actual);
    }

    @Test
    void getPackingListById_whenIdIsValid_shouldReturnPackingList() {
        //GIVEN
        PackingList packingListDto1 = PackingList.builder()
                .destination("Bayreuth")
                .build();

        PackingList addedPackingList = webTestClient.post()
                .uri("/api/packinglists")
                .bodyValue(packingListDto1)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PackingList.class)
                .returnResult()
                .getResponseBody();

        //WHEN
        assertNotNull(addedPackingList);
        PackingList actual = webTestClient.get()
                .uri("/api/packinglists/" + addedPackingList.getId())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PackingList.class)
                .returnResult()
                .getResponseBody();
        //THEN
        assertNotNull(actual);
        PackingList expected = PackingList.builder()
                .id(actual.getId())
                .destination("Bayreuth")
                .build();
        assertEquals(expected, actual);
    }

    @Test
    void getPackingListById_whenIdIsNotValid_shouldThrowException() {
        //GIVEN
        PackingList packingListDto1 = PackingList.builder()
                .destination("Bayreuth")
                .build();
        String invalidId = "123";

        PackingList addedPackingList = webTestClient.post()
                .uri("/api/packinglists")
                .bodyValue(packingListDto1)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PackingList.class)
                .returnResult()
                .getResponseBody();

        //WHEN
        assertNotNull(addedPackingList);
        webTestClient.get()
                .uri("/api/packinglists/" + invalidId)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void postNewPackingList_whenListIsNotEmpty_shouldReturnNewPackingList() {
        //GIVEN
        PackingListDto packingListDto1 = PackingListDto.builder()
                .destination("Bayreuth")
                .build();
        //WHEN
        PackingList actual = webTestClient.post()
                .uri("/api/packinglists")
                .bodyValue(packingListDto1)
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
                .destination("Bayreuth")
                .build();
        assertEquals(24, actual.getId().length());
        assertEquals(expected, actual);
    }

    @Test
    void postNewPackingList_whenNameIsNotGiven_shouldReturnException() {
        //GIVEN
        PackingListDto packingListDto1 = PackingListDto.builder()
                .destination(null)
                .build();
        //WHEN//THEN
        webTestClient.post()
                .uri("/api/packinglists")
                .bodyValue(packingListDto1)
                .exchange()
                .expectStatus().isEqualTo(400);
    }

    @Test
    void updateExistingPackingListById_whenIdIsValid_shouldReturnUpdatedPackingList() {
        //GIVEN
        PackingListDto existingPackingListDto = PackingListDto.builder()
                .destination("Bayreuth")
                .dateOfArrival(LocalDate.parse("2022-10-02"))
                .build();
        PackingList addedPackingList = webTestClient.post()
                .uri("/api/packinglists")
                .bodyValue(existingPackingListDto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PackingList.class)
                .returnResult()
                .getResponseBody();
        //WHEN
        assertNotNull(addedPackingList);
        PackingListDto updatedPackingList = PackingListDto.builder()
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .build();
        PackingList actual = webTestClient.put()
                .uri("/api/packinglists/" + addedPackingList.getId())
                .bodyValue(updatedPackingList)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PackingList.class)
                .returnResult()
                .getResponseBody();
        //THEN
        PackingList expected = PackingList.builder()
                .id(addedPackingList.getId())
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .build();
        assertEquals(expected, actual);
    }

    @Test
    void updateExistingPackingListById_whenIdIsNotValid_shouldThrowException() {
        //GIVEN
        PackingListDto existingPackingListDto = PackingListDto.builder()
                .destination("Bayreuth")
                .dateOfArrival(LocalDate.parse("2022-10-02"))
                .build();
        PackingList addedPackingList = webTestClient.post()
                .uri("/api/packinglists")
                .bodyValue(existingPackingListDto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PackingList.class)
                .returnResult()
                .getResponseBody();
        //WHEN
        assertNotNull(addedPackingList);
        PackingListDto updatedPackingList = PackingListDto.builder()
                .destination("Tokyo")
                .dateOfArrival(LocalDate.parse("2022-10-03"))
                .build();
        String invalidId = "123";
        webTestClient.put()
                .uri("/api/packinglists/" + invalidId)
                .bodyValue(updatedPackingList)
                .exchange()
                .expectStatus().isEqualTo(404);
    }

    @Test
    void addPackingItemToPackingList_whenNameIsGiveAndActualListIsNull_shouldReturnPackingList() {
        //GIVEN
        PackingListDto existingPackingListDto = PackingListDto.builder()
                .destination("Tokyo")
                .build();
        PackingList addedPackingList = webTestClient.post()
                .uri("/api/packinglists")
                .bodyValue(existingPackingListDto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PackingList.class)
                .returnResult()
                .getResponseBody();
        //WHEN
        assertNotNull(addedPackingList);
        CreatePackingItemDto newPackingItem = CreatePackingItemDto.builder()
                .name("passport")
                .build();
        when(idService.generateId()).thenReturn("1");
        PackingList actual = webTestClient.put()
                .uri("/api/packinglists/" + addedPackingList.getId() + "/packingitems")
                .bodyValue(newPackingItem)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PackingList.class)
                .returnResult()
                .getResponseBody();
        //THEN
        PackingList expected = PackingList.builder()
                .id(addedPackingList.getId())
                .destination("Tokyo")
                .packingItemList(List.of(PackingItem.builder()
                        .id("1")
                        .name("passport")
                        .build()))
                .build();
        assertEquals(expected, actual);
    }

    @Test
    void addPackingItemToPackingList_whenNameIsNotGiven_shouldThrowNoSuchElementException() {
        //GIVEN
        PackingListDto existingPackingListDto = PackingListDto.builder()
                .destination("Tokyo")
                .build();
        PackingList addedPackingList = webTestClient.post()
                .uri("/api/packinglists")
                .bodyValue(existingPackingListDto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PackingList.class)
                .returnResult()
                .getResponseBody();
        //WHEN
        assertNotNull(addedPackingList);
        CreatePackingItemDto newPackingItem = CreatePackingItemDto.builder()
                .build();
        webTestClient.put()
                .uri("/api/packinglists/" + addedPackingList.getId() + "/packingitems")
                .bodyValue(newPackingItem)
                .exchange()
                .expectStatus().isEqualTo(400);
    }

    @Test
    void deletePackingListByID_whenIdIsValid() {
        //GIVEN
        PackingListDto existingPackingListDto = PackingListDto.builder()
                .destination("Bayreuth")
                .dateOfArrival(LocalDate.parse("2022-10-02"))
                .build();
        PackingList addedPackingList = webTestClient.post()
                .uri("/api/packinglists")
                .bodyValue(existingPackingListDto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PackingList.class)
                .returnResult()
                .getResponseBody();
        //WHEN
        assertNotNull(addedPackingList);
        assertNotNull(addedPackingList.getId());
        webTestClient.delete()
                .uri("/api/packinglists/" + addedPackingList.getId())
                .exchange()
                .expectStatus().is2xxSuccessful();
        //THEN
    }

    @Test
    void deleteItemFromPackingList_whenIdOfListAndItemAreValid() {//GIVEN
        PackingListDto packingListDto = PackingListDto.builder()
                .destination("Bayreuth")
                .build();
        PackingList newPackingList = webTestClient.post()
                .uri("/api/packinglists")
                .bodyValue(packingListDto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PackingList.class)
                .returnResult()
                .getResponseBody();
        CreatePackingItemDto newPackingItem = CreatePackingItemDto.builder()
                .name("passport")
                .build();
        when(idService.generateId()).thenReturn("1");
        assertNotNull(newPackingList);
        assertNotNull(newPackingList.getId());
        PackingList updatedList = webTestClient.put()
                .uri("/api/packinglists/" + newPackingList.getId() + "/packingitems")
                .bodyValue(newPackingItem)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PackingList.class)
                .returnResult()
                .getResponseBody();
        String itemId = "1";
        //WHEN
        assertNotNull(updatedList);
        assertNotNull(updatedList.getId());
        PackingList actual = webTestClient.delete()
                .uri("/api/packinglists/" + updatedList.getId() + "/packingitems/" + itemId)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PackingList.class)
                .returnResult()
                .getResponseBody();
        //THEN
        PackingList excepted = PackingList.builder()
                .id(updatedList.getId())
                .destination("Bayreuth")
                .packingItemList(List.of())
                .build();
        assertEquals(excepted, actual);
    }

    @Test
    void deleteItemFromPackingList_whenIdOfListIsNotValid_ShouldThrowNoSuchElementException() {//GIVEN
        PackingListDto packingListDto = PackingListDto.builder()
                .destination("Bayreuth")
                .build();
        PackingList newPackingList = webTestClient.post()
                .uri("/api/packinglists")
                .bodyValue(packingListDto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PackingList.class)
                .returnResult()
                .getResponseBody();
        CreatePackingItemDto newPackingItem = CreatePackingItemDto.builder()
                .name("passport")
                .build();
        when(idService.generateId()).thenReturn("1");
        assertNotNull(newPackingList);
        assertNotNull(newPackingList.getId());
        PackingList updatedList = webTestClient.put()
                .uri("/api/packinglists/" + newPackingList.getId() + "/packingitems")
                .bodyValue(newPackingItem)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PackingList.class)
                .returnResult()
                .getResponseBody();
        String itemId = "1";
        String wrongListId = "2";
        //WHEN //THEN
        assertNotNull(updatedList);
        assertNotNull(updatedList.getId());
        webTestClient.delete()
                .uri("/api/packinglists/" + wrongListId + "/packingitems/" + itemId)
                .exchange()
                .expectStatus().isEqualTo(404);

    }

    @Test
    void deleteItemFromPackingList_whenListHasNoItems_ShouldThrowNoSuchElementException() {//GIVEN
        PackingListDto packingListDto = PackingListDto.builder()
                .destination("Bayreuth")
                .build();
        PackingList newPackingList = webTestClient.post()
                .uri("/api/packinglists")
                .bodyValue(packingListDto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(PackingList.class)
                .returnResult()
                .getResponseBody();
        String itemId = "1";
        //WHEN //THEN
        assertNotNull(newPackingList);
        assertNotNull(newPackingList.getId());
        webTestClient.delete()
                .uri("/api/packinglists/" + newPackingList.getId() + "/packingitems/" + itemId)
                .exchange()
                .expectStatus().isEqualTo(404);

    }
}