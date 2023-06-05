package com.desarrollo.cuatrolinea.provinces.pojo;

import com.desarrollo.cuatrolinea.provinces.model.ProvinceDocument;
import com.desarrollo.cuatrolinea.security.model.UserDocument;

public class Province {
    public String id;

    public String name;

    public Province(ProvinceDocument province) {
        this.id = province.id;
        this.name = province.name;
    }
}

