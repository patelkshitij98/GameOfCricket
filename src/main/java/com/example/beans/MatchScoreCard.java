package com.example.beans;

import lombok.Data;

import java.util.List;

@Data
public class MatchScoreCard {
    private TeamScoreCard teamScoreCard1;
    private TeamScoreCard teamScoreCard2;
    private String result;

    public MatchScoreCard(String team_name1, String team_name2, List<Player> playerList1, List<Player> playerList2) {
        this.teamScoreCard1 = new TeamScoreCard(team_name1, playerList1);
        this.teamScoreCard2 = new TeamScoreCard(team_name2, playerList2);
        this.result = "MATCH_NOT_PLAYED";
    }

}
