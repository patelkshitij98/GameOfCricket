package com.example.beans;

import lombok.Data;

import java.util.List;

@Data
public class Team {

    private String name;
    private List<Player> players;

    public Team(String name, List<Player> players) {
        this.name = name;
        this.players = players;
    }
}
