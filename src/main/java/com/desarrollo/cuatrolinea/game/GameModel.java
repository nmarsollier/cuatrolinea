package com.desarrollo.cuatrolinea.game;

import com.desarrollo.cuatrolinea.game.model.GameDocument;
import com.desarrollo.cuatrolinea.game.model.GameRepository;
import com.desarrollo.cuatrolinea.game.pojo.GameBoard;
import com.desarrollo.cuatrolinea.security.AuthValidation;
import com.desarrollo.cuatrolinea.security.model.TokenRepository;
import com.desarrollo.cuatrolinea.security.model.UserDocument;
import com.desarrollo.cuatrolinea.security.model.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

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

    @Tag(name = "Game", description = "Play new game")
    @PostMapping(
            value = "/new",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GameBoard newGame(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth
    ) {
        UserDocument user = AuthValidation.validateAuthUser(userRepository, tokenRepository, auth);

        GameDocument game = gameRepository.findItemFree(user.name).stream().findFirst().orElse(null);
        if (game != null) {
            game.user2 = user.name;
        } else {
            game = new GameDocument();
            game.user1 = user.name;
            game.turn = 1;
        }

        gameRepository.save(game);

        return new GameBoard(game);
    }

    @Tag(name = "Game", description = "Gets the current board")
    @GetMapping(
            value = "/{id}/board",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GameBoard getBoard(
            @PathVariable("id") String id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth
    ) {
        AuthValidation.validateAuthUser(userRepository, tokenRepository, auth);

        GameDocument existingGame = gameRepository.findById(id).orElseThrow();
        return new GameBoard(existingGame);
    }

    @Tag(name = "Game", description = "Make a play")
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

        return new GameBoard(game);
    }
}
