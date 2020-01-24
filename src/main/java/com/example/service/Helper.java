package com.example.service;

import com.example.beans.*;

import java.util.concurrent.ThreadLocalRandom;

public class Helper {

    private static void set_winner(MatchScoreCard sc) {
        TeamScoreCard team1_sc = sc.getTeamScoreCard1(), team2_sc = sc.getTeamScoreCard2();
        String result = "TIE";
        if(team1_sc.getScore() > team2_sc.getScore()){
            result = team1_sc.getTeam().getName() + " won by " + (team1_sc.getScore()-team2_sc.getScore()) + " run/s";
        }
        else if(team1_sc.getScore()<team2_sc.getScore()){
            result = team2_sc.getTeam().getName() + " won by " + (10-team2_sc.getWickets_lost()) + " wicket/s";
        }
        sc.setResult(result);
    }

    /*Wide, No balls, free hits, extra runs for overthrow, Run out are not considered*/
    private static void play_inning_randomly(TeamScoreCard batting_team_sc, int total_overs, int target, PlayerScoreCard[] batting_player_sc, PlayerScoreCard[] bowling_player_sc) {
        int range_of_runs = 8;  // { range_of_runs = [0,7] } ==>> [[ 0-6 for runs, 7 is for wicket ]]
        int player_on_strike = 0, player_on_non_strike = 1, player_next = 2, current_over = 0, bowler_index;

        //Run this loop till overs are left and target is not achieved with wickets in hand
        while (current_over <= total_overs && batting_team_sc.getScore() < target && batting_team_sc.getWickets_lost()<10) {

            bowler_index = ThreadLocalRandom.current().nextInt(5,11);

            int runs_in_current_over = 0;

            //Iterate for 6 balls in an over
            for (int i = 0; i < 6 && batting_team_sc.getScore() < target; i++) {
                int runs_on_ball = (int) (Math.random() * range_of_runs);
                if (runs_on_ball == 7) {
                    batting_team_sc.setWickets_lost(batting_team_sc.getWickets_lost()+1); //Wicket is fallen
                    //increment balls played by batsman
                    batting_player_sc[player_on_strike].getPlayerBattingScoreCard().setNoOfBalls_Played(batting_player_sc[player_on_strike].getPlayerBattingScoreCard().getNoOfBalls_Played()+1);

                    player_on_strike = player_next;  //new player comes on strike
                    player_next++;

                    //increment wickets taken by a bowler
                    bowling_player_sc[bowler_index].getPlayerBowlingScoreCard().setNoOfWickets_Taken(bowling_player_sc[bowler_index].getPlayerBowlingScoreCard().getNoOfWickets_Taken() + 1);

                    //if team is all-out break out of loop
                    if (batting_team_sc.getWickets_lost() == 10) break;
                } else {

                    //increment runs score by batsman along with # of balls played, batting team score, runs given by bowler
                    batting_player_sc[player_on_strike].getPlayerBattingScoreCard().setNoOfRuns_Scored(batting_player_sc[player_on_strike].getPlayerBattingScoreCard().getNoOfRuns_Scored()+runs_on_ball);

                    batting_player_sc[player_on_strike].getPlayerBattingScoreCard().setNoOfBalls_Played(batting_player_sc[player_on_strike].getPlayerBattingScoreCard().getNoOfBalls_Played()+1);

                    batting_team_sc.setScore(batting_team_sc.getScore() + runs_on_ball);

                    bowling_player_sc[bowler_index].getPlayerBowlingScoreCard().setNoOfRuns_Given(bowling_player_sc[bowler_index].getPlayerBowlingScoreCard().getNoOfRuns_Given() + runs_on_ball);
                    runs_in_current_over += runs_on_ball;
                    if (runs_on_ball % 2 == 1) {           //swap players as strike changes
                        int temp = player_on_strike;
                        player_on_strike = player_on_non_strike;
                        player_on_non_strike = temp;
                    }
                    else if(runs_on_ball==4){
                        //increment fours scored by batsman
                        batting_player_sc[player_on_strike].getPlayerBattingScoreCard().setNoOfFours(batting_player_sc[player_on_strike].getPlayerBattingScoreCard().getNoOfFours()+1);
                    }
                    else if(runs_on_ball==6){
                        //increment sixes scored by batsman
                        batting_player_sc[player_on_strike].getPlayerBattingScoreCard().setNoOfSixes(batting_player_sc[player_on_strike].getPlayerBattingScoreCard().getNoOfSixes()+1);
                    }
                }


            }

            //swap players as strike changes
            int temp = player_on_strike;
            player_on_strike = player_on_non_strike;
            player_on_non_strike = temp;

            if (runs_in_current_over == 0) {
                //increment maiden overs by the bowler
                bowling_player_sc[bowler_index].getPlayerBowlingScoreCard().setNoOfMaiden_Overs(bowling_player_sc[bowler_index].getPlayerBowlingScoreCard().getNoOfMaiden_Overs() + 1);
            }

            bowling_player_sc[bowler_index].getPlayerBowlingScoreCard().setNoOfOvers(bowling_player_sc[bowler_index].getPlayerBowlingScoreCard().getNoOfOvers() + 1);

            current_over++;
        }

    }

    public static MatchScoreCard play_match() {
        MatchScoreCard sc = new MatchScoreCard("Alpha","Beta");
        PlayerScoreCard[] p_sc_1 = new PlayerScoreCard[11];
        PlayerScoreCard[] p_sc_2 = new PlayerScoreCard[11];
        initialize(p_sc_1);
        initialize(p_sc_2);

        play_inning_randomly(sc.getTeamScoreCard1(),50, Integer.MAX_VALUE,p_sc_1,p_sc_2);
        play_inning_randomly(sc.getTeamScoreCard2(),50, sc.getTeamScoreCard1().getScore()+1,p_sc_2,p_sc_1);

        mapPlayersWithScoreCards(sc,p_sc_1,p_sc_2);
        set_winner(sc);
        return sc;
    }

    private static void initialize(PlayerScoreCard[] playerScoreCards){
        for(int i=0;i<11;i++)
            playerScoreCards[i] = new PlayerScoreCard();

    }


    private static void mapPlayersWithScoreCards(MatchScoreCard sc, PlayerScoreCard[] playerScoreCards1, PlayerScoreCard[] playerScoreCards2){
        for(int i=0;i<11;i++){
            sc.getTeamScoreCard1().scorecardmap.put(sc.getTeamScoreCard1().getTeam().players_list[i],playerScoreCards1[i]);
            sc.getTeamScoreCard2().scorecardmap.put(sc.getTeamScoreCard2().getTeam().players_list[i],playerScoreCards2[i]);
        }
    }

}
