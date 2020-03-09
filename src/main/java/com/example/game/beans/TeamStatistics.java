package com.example.game.beans;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class TeamStatistics {
    @Id
    private int teamId;
    private int won;
    private int lost;
    private int draw;

    public TeamStatistics(int teamId, int won, int lost, int draw) {
        this.teamId = teamId;
        this.won = won;
        this.lost = lost;
        this.draw = draw;
    }
}
