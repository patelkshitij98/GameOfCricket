package com.example.beans;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Team {

    private String name;
    public List<Player> players;

    public Team(String name) {
        this.name = name;
        this.players = new ArrayList<>(11);
        for(int i=0;i<11;i++){
            this.players.add(i,new Player());
            this.players.get(i).setId(i+1);
        }
    }

}
