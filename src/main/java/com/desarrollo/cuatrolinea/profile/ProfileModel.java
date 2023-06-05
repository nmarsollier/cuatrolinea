package com.desarrollo.cuatrolinea.profile;

import com.desarrollo.cuatrolinea.profile.model.ProfileDocument;
import com.desarrollo.cuatrolinea.profile.model.ProfileRepository;
import com.desarrollo.cuatrolinea.profile.pojo.Profile;
import com.desarrollo.cuatrolinea.profile.pojo.ProfileData;
import com.desarrollo.cuatrolinea.security.AuthValidation;
import com.desarrollo.cuatrolinea.security.model.TokenRepository;
import com.desarrollo.cuatrolinea.security.model.UserDocument;
import com.desarrollo.cuatrolinea.security.model.UserRepository;
import com.desarrollo.cuatrolinea.security.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/profile")
public class ProfileModel {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    ProfileRepository profileRepository;

    @PostMapping(
            value = "/update",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Profile update(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
            @RequestBody ProfileData profileData
    ) {
        UserDocument user = AuthValidation.validateAuthUser(userRepository, tokenRepository, auth);

        ProfileDocument profile = profileRepository.findItemByUserId(user.id);
        if (profile == null) {
            profile = new ProfileDocument(user.id, user.name, null, null);
        }

        profile.name = profileData.name;
        profile.birthDate = profileData.birthDate;
        profile.picture = profileData.picture;

        profileRepository.save(profile);

        return new Profile(profile);
    }

    @GetMapping(
            value = "/current",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Profile currentUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        UserDocument user = AuthValidation.validateAuthUser(userRepository, tokenRepository, auth);
        ProfileDocument profile = profileRepository.findItemByUserId(user.id);
        if (profile == null) {
            profile = new ProfileDocument(user.id, user.name, "", "");
        }
        return new Profile(profile);
    }
}
