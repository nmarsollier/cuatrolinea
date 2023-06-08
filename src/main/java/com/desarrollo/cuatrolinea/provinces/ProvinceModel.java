package com.desarrollo.cuatrolinea.provinces;

import com.desarrollo.cuatrolinea.provinces.model.ProvinceDocument;
import com.desarrollo.cuatrolinea.provinces.model.ProvinceRepository;
import com.desarrollo.cuatrolinea.provinces.pojo.NewProvinceData;
import com.desarrollo.cuatrolinea.provinces.pojo.Province;
import com.desarrollo.cuatrolinea.security.AuthValidation;
import com.desarrollo.cuatrolinea.security.model.TokenRepository;
import com.desarrollo.cuatrolinea.security.model.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/province")
public class ProvinceModel {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    ProvinceRepository provinceRepository;


    @Tag(name = "Provinces", description = "List all provinces")
    @GetMapping(
            value = "/list",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Province> list(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        AuthValidation.validateAuthUser(userRepository, tokenRepository, auth);

        return provinceRepository.findAll().stream().map(Province::new).toList();
    }

    @Tag(name = "Provinces", description = "Create new Province")
    @PostMapping(
            value = "/new",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void create(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
            @RequestBody NewProvinceData newProvinceData
    ) {
        AuthValidation.validateAuthUser(userRepository, tokenRepository, auth);

        provinceRepository.save(new ProvinceDocument(newProvinceData.name));
    }
}
