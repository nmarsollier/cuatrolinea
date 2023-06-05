package com.desarrollo.cuatrolinea.profile.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProfileData {

    public String name;

    public String birthDate;

    public String picture;

    @JsonCreator
    public ProfileData(
            @JsonProperty(required = true) String name,
            @JsonProperty() String birthDate,
            @JsonProperty() String picture
    ) {
        this.name = name;
        this.birthDate = birthDate;
        this.picture = picture;
    }
}