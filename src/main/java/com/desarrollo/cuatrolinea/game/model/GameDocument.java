package com.desarrollo.cuatrolinea.game.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.security.InvalidParameterException;

@Document("games")
public class GameDocument {
    static int MAX_COLS = 10;
    static int MAX_ROWS = 10;

    @Id
    public String id;

    public String user1;

    public String user2;

    public int[][] board = new int[MAX_ROWS][MAX_COLS];

    public int turn = 0;

    public void play(String name, int column) {
        if (turn == 0) throw new InvalidParameterException("We cant play");
        if (turn == 1 && !name.equals(user1)) throw new InvalidParameterException("Not your turn");
        if (turn == 2 && !name.equals(user2)) throw new InvalidParameterException("Not your turn");

        if (column >= MAX_COLS) throw new InvalidParameterException("Invalid column");

        int user = 1;
        if (name.equals(user2)) user = 2;

        for (int i = 0; i < MAX_ROWS; i++) {
            if (board[i][column] != 0) {
                if (i == 0) {
                    throw new InvalidParameterException("column completed");
                }
                board[i - 1][column] = user;
                break;
            }
            if (i == (MAX_ROWS - 1)) {
                board[i][column] = user;
                break;
            }
        }

        if (name.equals(user1)) {
            turn = 2;
        } else {
            turn = 1;
        }
    }
}