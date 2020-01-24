package com.example.beans;

public class Team {
    private String name;
    public Player[] players_list;

    public Team(String name) {
        this.name = name;
        this.players_list = new Player[11];
        for(int i=0;i<11;i++){
            this.players_list[i] = new Player();
            this.players_list[i].setPlayerid(i+1);
        }
    }

    public String getName() {
        return name;
    }

    public Player[] getPlayers_list() {
        return players_list;
    }



}
