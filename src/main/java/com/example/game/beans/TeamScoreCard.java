package com.example.game.beans;

import lombok.Data;

import java.util.Map;

public @Data class TeamScoreCard {
    private int score;
    private int wicketsLost;
    private double overs;
    private Map<Integer, PlayerScoreCard> playerScoreCardMap;

}
