package com.desarrollo.cuatrolinea.game.pojo;

import com.desarrollo.cuatrolinea.game.model.GameDocument;
import org.springframework.data.annotation.Id;

import java.util.Arrays;

public class GameBoard {
    @Id
    public String id;

    public String user1;

    public String user2;

    public String[][] board;
    public String turn;

    public String winner;

    public boolean match;

    public GameBoard(GameDocument document) {
        this.id = document.id;
        this.board = new String[document.board.length][];
        for (int i = 0; i < document.board.length; i++) {
            this.board[i] = new String[document.board[i].length];
            for (int j = 0; j < board[i].length; j++) {
                if (document.board[i][j] == 1) {
                    this.board[i][j] = document.user1;
                } else if (document.board[i][j] == 2) {
                    this.board[i][j] = document.user2;
                } else {
                    this.board[i][j] = "";
                }
            }
        }

        this.winner = document.winner;
        this.match = document.match;
        if (winner == null) {
            if (document.turn == 1) {
                this.turn = document.user1;
            } else if (document.turn == 2) {
                this.turn = document.user2;
            }
        }
        this.user1 = document.user1;
        this.user2 = document.user2;
    }
}
