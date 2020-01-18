package com.example.assignment1;

//Class for Cricket Match
public class Match {
    private Team team1;
    private Team team2;
    private String result;

    public Match(String team_name1, String team_name2) {
        team1 = new Team(team_name1,0,0);
        team2 = new Team(team_name2,0,0);
        this.result = "MATCH NOT PLAYED";
    }
    /*
    Team class may be shifted from inner class to outer class
    for future project enhancement
     */
    private static class Team{
        String name;
        int score;
        int wickets_lost;

        public Team(String name, int score, int wickets_lost) {
            this.name = name;
            this.score = score;
            this.wickets_lost = wickets_lost;
        }
    }

    public void play_random() {
        play_inning_randomly(team1,50,Integer.MAX_VALUE);          //team1 plays randomly
        play_inning_randomly(team2,50,team1.score + 1);     //team2 plays randomly
        set_winner();                                                    //to set the result String
    }

    private void play_inning_randomly(Team team, int overs, int target) {
        int balls_played=1,range = 8;  // { range = [0,7] } ==>> [[ 0-6 for runs, 7 is for wicket ]]
        while(balls_played <= overs*6 && team.score<target){
            int runs = (int)(Math.random()*range);
            if(runs == 7){
                team.wickets_lost++;
                if(team.wickets_lost==10) break;
            }
            else team.score = team.score + runs;
            balls_played++;
        }
    }

    private void set_winner() {
        result = "TIE";
        if(team1.score > team2.score){
            result = team1.name + " won by " + (team1.score-team2.score) + " run/s";
        }
        else if(team1.score<team2.score){
            result = team2.name + " won by " + (10-team2.wickets_lost) + " wicket/s";
        }
    }

    public String getSummary() {
        return "MatchResult {" +
                "Team1 = '" + team1.name + "',\n" +
                "Score1 = " + team1.score + "/" + team1.wickets_lost + ",\n"+
                "Team2 = '" + team2.name + "',\n" +
                "Score2 = " + team2.score + "/" + team2.wickets_lost + ",\n"+
                "Result = " + result +
                " }";
    }
}