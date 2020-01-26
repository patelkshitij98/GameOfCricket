package com.example.beans;

import lombok.Data;

@Data
public class MatchScoreCard {
    private TeamScoreCard teamScoreCard1;
    private TeamScoreCard teamScoreCard2;
    private String result;

    public MatchScoreCard(String team_name1, String team_name2) {
        this.teamScoreCard1 = new TeamScoreCard(team_name1);
        this.teamScoreCard2 = new TeamScoreCard(team_name2);
        this.result = "MATCH_NOT_PLAYED";
    }

}
