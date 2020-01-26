package com.example.beans;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public @Data class TeamScoreCard {
    private Team team;
    private int score;
    private int wicketsLost;
    private Map<Integer, PlayerScoreCard> scoreCards;

    public TeamScoreCard(String team_name, List<Player> playerList) {
        this.team = new Team(team_name, playerList);
        this.scoreCards = new HashMap<>(11);
    }

}
