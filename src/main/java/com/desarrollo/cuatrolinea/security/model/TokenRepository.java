package com.desarrollo.cuatrolinea.security.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TokenRepository extends MongoRepository<TokenDocument, String> {
    @Query("{userId:'?0'}")
    TokenDocument findItemByUserId(String userId);
}