package com.github.mariakohnen.packbag.backend.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IdServiceTest {

    final IdService idService= new IdService();

    @Test
    void generateId() {
        //GIVEN
        String uuId = "b84d54bb-a398-4e07-a4de-3c4105a195b5";
        //WHEN
        String actual = idService.generateId();
        //THEN
        assertEquals(uuId.length(), actual.length());
    }
}