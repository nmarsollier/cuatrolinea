package com.desarrollo.cuatrolinea.security.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
public class UserDocument {
    @Id
    public String id;

    @Indexed(unique = true)
    public String name;

    public String password;

    public UserDocument(String name, String password) {
        super();
        this.name = name;
        this.password = password;
    }
}
