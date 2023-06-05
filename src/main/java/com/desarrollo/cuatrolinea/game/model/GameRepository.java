package com.desarrollo.cuatrolinea.game.model;

import com.desarrollo.cuatrolinea.profile.model.ProfileDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Collection;

public interface GameRepository extends MongoRepository<GameDocument, String> {
    @Query("{'$and':[ { 'user1': {$ne : ?0}}, { 'user2': null} ] } ")
    Collection<GameDocument> findItemFree(String userId);
}