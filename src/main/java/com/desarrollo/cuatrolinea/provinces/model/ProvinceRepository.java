package com.desarrollo.cuatrolinea.provinces.model;

import com.desarrollo.cuatrolinea.profile.model.ProfileDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ProvinceRepository extends MongoRepository<ProvinceDocument, String> {

}