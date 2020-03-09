package com.example.game.beans;

import lombok.Data;

import java.util.Map;

@Data
public class MatchScoreCard {
    private Map<Integer, TeamScoreCard> teamScoreCardMap;
    private String result;
}
