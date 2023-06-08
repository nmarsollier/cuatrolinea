package com.desarrollo.cuatrolinea.security;

import com.desarrollo.cuatrolinea.security.model.*;
import com.desarrollo.cuatrolinea.security.pojo.ChangePasswordData;
import com.desarrollo.cuatrolinea.security.pojo.RegisterData;
import com.desarrollo.cuatrolinea.security.pojo.Token;
import com.desarrollo.cuatrolinea.security.pojo.User;
import com.desarrollo.cuatrolinea.utilities.ShaEncoder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping(value = "/user")
public class AuthModel {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping(
            value = "/register",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Token register(@RequestBody RegisterData registerData) {
        if (registerData.userName.isBlank()) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(404), "Invalid username");
        }
        if (registerData.password.isBlank()) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(404), "Invalid password");
        }

        UserDocument user = userRepository.save(new UserDocument(
                registerData.userName,
                ShaEncoder.encode(registerData.password)
        ));

        TokenDocument token = tokenRepository.save(new TokenDocument(
                user.name
        ));

        Token result = new Token();
        result.token = token.id;
        return result;
    }

    @GetMapping(
            value = "/current",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public User currentUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        UserDocument user = AuthValidation.validateAuthUser(userRepository, tokenRepository, auth);

        return new User(user);
    }

    public @PostMapping(
            value = "/login",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Token login(@RequestBody RegisterData registerData) {
        UserDocument user = Objects.requireNonNull(userRepository.findItemByName(registerData.userName));
        if (!ShaEncoder.encode(registerData.password).equals(user.password)) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(404), "Invalid password");
        }

        TokenDocument token = tokenRepository.save(new TokenDocument(
                user.name
        ));

        Token result = new Token();
        result.token = token.id;
        return result;
    }

    public @PostMapping(
            value = "/changePassword",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    void changePassword(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
            @RequestBody ChangePasswordData changePasswordData
    ) {
        UserDocument user = AuthValidation.validateAuthUser(userRepository, tokenRepository, auth);

        if (!ShaEncoder.encode(changePasswordData.currentPassword).equals(user.password)) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(404), "Invalid password");
        }

        if (changePasswordData.newPassword.isBlank()) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(404), "Invalid password");
        }

        user.password = ShaEncoder.encode(changePasswordData.newPassword);
        userRepository.save(user);
    }

    public @GetMapping(
            value = "/logout",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    void logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        TokenDocument token = tokenRepository.findById(auth.substring(7)).orElseThrow();
        token.status = RecordStatus.INACTIVE;
        tokenRepository.save(token);
    }
}
