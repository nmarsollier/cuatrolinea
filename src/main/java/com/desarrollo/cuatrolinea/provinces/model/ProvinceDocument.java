package com.desarrollo.cuatrolinea.provinces.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("provinces")
public class ProvinceDocument {
    @Id
    public String id;


    public String name;

    public ProvinceDocument(String name) {
        super();
        this.name = name;
    }
}
