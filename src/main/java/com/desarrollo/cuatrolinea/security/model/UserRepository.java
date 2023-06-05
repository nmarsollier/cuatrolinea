package com.desarrollo.cuatrolinea.security.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<UserDocument, String> {

    @Query("{name:'?0'}")
    UserDocument findItemByName(String name);
}