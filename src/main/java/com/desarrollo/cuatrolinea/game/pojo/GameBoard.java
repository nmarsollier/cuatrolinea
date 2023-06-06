package com.desarrollo.cuatrolinea.game.pojo;

import com.desarrollo.cuatrolinea.game.model.GameDocument;
import com.desarrollo.cuatrolinea.security.model.UserDocument;
import com.desarrollo.cuatrolinea.security.model.UserRepository;
import com.desarrollo.cuatrolinea.security.pojo.User;
import org.springframework.data.annotation.Id;

import java.util.Objects;

public class GameBoard {
    @Id
    public String id;

    public User user1;

    public User user2;

    public int[][] board;
    public int turn;

    public String winner;

    public GameBoard(GameDocument document, UserRepository userRepository) {
        this.id = document.id;
        this.board = document.board;
        this.winner = document.winner;
        if (winner == null) {
            this.turn = document.turn;
        }
        UserDocument user1 = Objects.requireNonNull(userRepository.findItemByName(document.user1));
        this.user1 = new User(user1);

        if (document.user2 != null) {
            UserDocument user2 = Objects.requireNonNull(userRepository.findItemByName(document.user2));
            this.user2 = new User((user2));
        }
    }
}
