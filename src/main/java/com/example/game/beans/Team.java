package com.example.game.beans;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
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
