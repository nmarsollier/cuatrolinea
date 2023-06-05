package com.desarrollo.cuatrolinea.profile.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("profiles")
public class ProfileDocument {
    @Id
    public String id;

    public String userId;

    public String name;

    public String birthDate;

    public String picture;

    public ProfileDocument(String userId, String name, String birthDate, String picture) {
        super();
        this.userId = userId;
        this.name = name;
        this.birthDate = birthDate;
        this.picture = picture;
    }
}
