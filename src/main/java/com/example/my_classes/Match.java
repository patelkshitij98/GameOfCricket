package com.example.my_classes;

//Class for Cricket Match
public class Match {
    private Team team1;
    private Team team2;
    private String result;

    public Match(String team_name1, String team_name2) {
        this.team1 = new Team(team_name1,0,0);
        this.team2 = new Team(team_name2,0,0);
        this.result = "MATCH NOT PLAYED";
    }

    public void play_random() {
        Helper.play_inning_randomly(team1, team2,50,Integer.MAX_VALUE);          //team1 plays randomly
//        Helper.print_me(team1,team2,1);
        Helper.play_inning_randomly(team2,team1,50,team1.getScore() + 1);     //team2 plays randomly
//        Helper.print_me(team2,team1,2);
        set_winner();                                                    //to set the result String
    }

    private void set_winner() {
        result = "TIE";
        if(team1.getScore() > team2.getScore()){
            result = team1.getName() + " won by " + (team1.getScore()-team2.getScore()) + " run/s";
        }
        else if(team1.getScore()<team2.getScore()){
            result = team2.getName() + " won by " + (10-team2.getWickets_lost()) + " wicket/s";
        }
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public String getResult() {
        return result;
    }

}