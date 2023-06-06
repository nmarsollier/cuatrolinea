package com.desarrollo.cuatrolinea.provinces;

import com.desarrollo.cuatrolinea.profile.model.ProfileDocument;
import com.desarrollo.cuatrolinea.profile.model.ProfileRepository;
import com.desarrollo.cuatrolinea.profile.pojo.Profile;
import com.desarrollo.cuatrolinea.profile.pojo.ProfileData;
import com.desarrollo.cuatrolinea.provinces.model.ProvinceDocument;
import com.desarrollo.cuatrolinea.provinces.model.ProvinceRepository;
import com.desarrollo.cuatrolinea.provinces.pojo.NewProvinceData;
import com.desarrollo.cuatrolinea.provinces.pojo.Province;
import com.desarrollo.cuatrolinea.security.AuthValidation;
import com.desarrollo.cuatrolinea.security.model.TokenRepository;
import com.desarrollo.cuatrolinea.security.model.UserDocument;
import com.desarrollo.cuatrolinea.security.model.UserRepository;
import com.desarrollo.cuatrolinea.security.pojo.ChangePasswordData;
import com.desarrollo.cuatrolinea.security.pojo.User;
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

    @GetMapping(
            value = "/list",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Province> list(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        AuthValidation.validateAuthUser(userRepository, tokenRepository, auth);

        return provinceRepository.findAll().stream().map(Province::new).toList();
    }

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
