package com.example.beans;

import java.util.HashMap;
import java.util.Map;

public class TeamScoreCard {
    private Team team;
    private int score;
    private int wickets_lost;
    public Map<Player, PlayerScoreCard> scorecardmap;

    public TeamScoreCard(String team_name) {
        this.team = new Team(team_name);
        this.scorecardmap = new HashMap<>(11);

    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Map<Player, PlayerScoreCard> getScorecardmap() {
        return scorecardmap;
    }

    public void setScorecardmap(Map<Player, PlayerScoreCard> scorecardmap) {
        this.scorecardmap = scorecardmap;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getWickets_lost() {
        return wickets_lost;
    }

    public void setWickets_lost(int wickets_lost) {
        this.wickets_lost = wickets_lost;
    }
}
