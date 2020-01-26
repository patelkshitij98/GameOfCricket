package com.example.beans;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

public @Data class TeamScoreCard {
    private Team team;
    private int score;
    private int wicketsLost;
    private Map<Integer, PlayerScoreCard> scoreCards;

    public TeamScoreCard(String team_name) {
        this.team = new Team(team_name);
        this.scoreCards = new HashMap<>(11);
    }

}
