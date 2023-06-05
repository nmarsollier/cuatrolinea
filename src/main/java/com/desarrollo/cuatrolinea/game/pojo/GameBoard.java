package com.desarrollo.cuatrolinea.game.pojo;

import com.desarrollo.cuatrolinea.security.pojo.User;
import org.springframework.data.annotation.Id;

public class GameBoard {
    @Id
    public String id;

    public User user1;

    public User user2;

    public int[][] board;
    public int turn;
}
