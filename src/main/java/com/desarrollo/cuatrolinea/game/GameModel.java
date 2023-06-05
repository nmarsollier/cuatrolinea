package com.desarrollo.cuatrolinea.game;

import com.desarrollo.cuatrolinea.game.model.GameDocument;
import com.desarrollo.cuatrolinea.game.model.GameRepository;
import com.desarrollo.cuatrolinea.game.pojo.GameBoard;
import com.desarrollo.cuatrolinea.profile.model.ProfileRepository;
import com.desarrollo.cuatrolinea.security.AuthValidation;
import com.desarrollo.cuatrolinea.security.model.TokenRepository;
import com.desarrollo.cuatrolinea.security.model.UserDocument;
import com.desarrollo.cuatrolinea.security.model.UserRepository;
import com.desarrollo.cuatrolinea.security.pojo.User;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.security.InvalidParameterException;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping(value = "/game")
public class GameModel {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    ProfileRepository profileRepository;

    @PostMapping(
            value = "/new",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GameBoard newGame(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth
    ) {
        UserDocument user = AuthValidation.validateAuthUser(userRepository, tokenRepository, auth);

        GameDocument game = gameRepository.findItemFree(user.id).stream().findFirst().orElse(null);
        if (game != null) {
            game.user2 = user.name;
        } else {
            game = new GameDocument();
            game.user1 = user.name;
            game.turn = 1;
        }

        gameRepository.save(game);

        return buildGameBoard(game);
    }

    @PostMapping(
            value = "/{id}/board",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GameBoard getBoard(
            @PathVariable("id") String id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth
    ) {
        UserDocument user = AuthValidation.validateAuthUser(userRepository, tokenRepository, auth);

        GameDocument existingGame = gameRepository.findById(id).orElseThrow();
        return buildGameBoard(existingGame);
    }


    @PostMapping(
            value = "/{id}/play",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GameBoard play(
            @PathVariable("id") String id,
            @RequestParam("column") int column,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth
    ) {
        UserDocument user = AuthValidation.validateAuthUser(userRepository, tokenRepository, auth);

        GameDocument game = gameRepository.findById(id).orElseThrow();
        if (!game.user2.equals(user.name) && !game.user1.equals(user.name)) throw new
                HttpClientErrorException(HttpStatusCode.valueOf(404));
        
        game.play(user.name, column);
        game = gameRepository.save(game);

        return buildGameBoard(game);
    }


    private GameBoard buildGameBoard(GameDocument document) {
        GameBoard result = new GameBoard();
        result.id = document.id;
        result.board = document.board;
        result.turn = document.turn;

        UserDocument user1 = Objects.requireNonNull(userRepository.findItemByName(document.user1));
        result.user1 = new User(user1);

        if (document.user2 != null) {
            UserDocument user2 = Objects.requireNonNull(userRepository.findItemByName(document.user2));
            result.user2 = new User((user2));
        }

        return result;
    }


}
