package com.desarrollo.cuatrolinea.security.pojo;

import com.desarrollo.cuatrolinea.security.model.UserDocument;

public class User {
    public String name;

    public User(UserDocument user) {
        this.name = user.name;
    }
}
