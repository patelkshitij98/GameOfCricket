package com.example.game.beans;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

@Data
@Document
public class MatchScoreCard {
    private int winnerTeamId;
    private Map<Integer, TeamScoreCard> teamScoreCardMap;
    private String result;
    private Date createdAt;

    public MatchScoreCard() {
        this.createdAt = new Date();
    }
}
