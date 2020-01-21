package com.example.my_classes;

class Team {
    private String name;
    private int score;
    private int wickets_lost;
    private Player players_list[];

    public Team(String name, int score, int wickets_lost) {
        this.name = name;
        this.score = score;
        this.wickets_lost = wickets_lost;
        this.players_list = new Player[11];
        for(int i=0;i<11;i++){
            this.players_list[i] = new Player();
        }
    }


    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getWickets_lost() {
        return wickets_lost;
    }

    public Player[] getPlayers_list() {
        return players_list;
    }


    public void setScore(int score) {
        this.score = score;
    }

    public void setWickets_lost(int wickets_lost) {
        this.wickets_lost = wickets_lost;
    }

}
