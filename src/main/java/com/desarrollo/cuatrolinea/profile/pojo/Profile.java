package com.desarrollo.cuatrolinea.profile.pojo;

import com.desarrollo.cuatrolinea.profile.model.ProfileDocument;

public class Profile {
    public String name;
    public String birthDate;
    public String picture;

    public Profile(ProfileDocument document) {
        this.name = document.name;
        this.birthDate = document.birthDate;
        this.picture = document.picture;
    }

}
