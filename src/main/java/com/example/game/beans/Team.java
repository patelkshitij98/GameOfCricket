package com.example.game.beans;

import lombok.Data;

import java.util.List;

@Data
public class Team {

    private int id;
    private String name;
    private List<Player> players;

    public Team(int id, String name, List<Player> players) {
        this.id = id;
        this.name = name;
        this.players = players;
    }
}
