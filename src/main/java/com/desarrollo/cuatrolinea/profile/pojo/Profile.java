package com.desarrollo.cuatrolinea.profile.pojo;

import com.desarrollo.cuatrolinea.profile.model.ProfileDocument;
import com.desarrollo.cuatrolinea.security.model.UserDocument;
import com.fasterxml.jackson.annotation.JsonCreator;

public class Profile {
    public String login;

    public String name;

    public String email;

    public String address;

    public String provinceId;

    public String picture;

    public final String phone;

    @JsonCreator
    public Profile(UserDocument user, ProfileDocument profile) {
        this.login =  user.name;
        this.name = profile.name;
        this.email = profile.email;
        this.provinceId = profile.provinceId;
        this.address = profile.address;
        this.picture = profile.picture;
        this.phone = profile.phone;
    }

}
