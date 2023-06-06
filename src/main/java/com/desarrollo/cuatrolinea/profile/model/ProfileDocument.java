package com.desarrollo.cuatrolinea.profile.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("profiles")
public class ProfileDocument {
    @Id
    public String id;

    public String userId;

    public String name;

    public String email;

    public String address;

    public String provinceId;

    public String picture;

    public String phone;

    public ProfileDocument(String userId, String name, String email, String picture, String provinceId, String address, String phone) {
        super();
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.provinceId = provinceId;
        this.address = address;
        this.picture = picture;
        this.phone = phone;
    }
}
