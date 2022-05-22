package com.github.mariakohnen.packbag.backend.controller;

import com.github.mariakohnen.packbag.backend.dto.PackingListDto;
import com.github.mariakohnen.packbag.backend.model.PackingList;
import com.github.mariakohnen.packbag.backend.repository.PackingListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PackingListControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PackingListRepository packingListRepository;

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

}