package com.example.beans;

public class MatchScoreCard {
    private TeamScoreCard teamScoreCard1;
    private TeamScoreCard teamScoreCard2;
    private String result;

    public MatchScoreCard(String team_name1, String team_name2) {
        this.teamScoreCard1 = new TeamScoreCard(team_name1);
        this.teamScoreCard2 = new TeamScoreCard(team_name2);
        this.result = "MATCH_NOT_PLAYED";
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public TeamScoreCard getTeamScoreCard1() {
        return teamScoreCard1;
    }

    public void setTeamScoreCard1(TeamScoreCard teamScoreCard1) {
        this.teamScoreCard1 = teamScoreCard1;
    }

    public TeamScoreCard getTeamScoreCard2() {
        return teamScoreCard2;
    }

    public void setTeamScoreCard2(TeamScoreCard teamScoreCard2) {
        this.teamScoreCard2 = teamScoreCard2;
    }
}
