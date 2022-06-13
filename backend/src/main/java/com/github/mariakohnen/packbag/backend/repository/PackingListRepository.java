package com.github.mariakohnen.packbag.backend.repository;

import com.github.mariakohnen.packbag.backend.model.PackingList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackingListRepository extends MongoRepository<PackingList, String> {

    List<PackingList> findAllByUserId(String userId);
}
