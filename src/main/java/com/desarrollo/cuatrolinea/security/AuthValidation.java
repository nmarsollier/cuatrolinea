package com.desarrollo.cuatrolinea.security;

import com.desarrollo.cuatrolinea.security.model.*;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Objects;

public class AuthValidation {
    public static TokenDocument validateAuth(TokenRepository tokenRepository, String authHeader) {
        TokenDocument token = tokenRepository.findById(authHeader.substring(7)).orElseThrow();
        if (token.status != RecordStatus.ACTIVE) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(404), "Invalid password");
        }
        return token;
    }

    public static UserDocument validateAuthUser(UserRepository userRepository, TokenRepository tokenRepository, String authHeader) {
        TokenDocument token = AuthValidation.validateAuth(tokenRepository, authHeader);

        return Objects.requireNonNull(userRepository.findItemByName(token.userId));
    }
}
