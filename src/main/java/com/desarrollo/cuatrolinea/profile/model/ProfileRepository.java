package com.desarrollo.cuatrolinea.profile.model;

import com.desarrollo.cuatrolinea.security.model.TokenDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ProfileRepository extends MongoRepository<ProfileDocument, String> {
    @Query("{userId:'?0'}")
    ProfileDocument findItemByUserId(String userId);
}