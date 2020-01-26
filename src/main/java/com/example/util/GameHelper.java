package com.example.util;

import com.example.beans.BattingCard;
import com.example.beans.BowlingCard;
import com.example.beans.PlayerScoreCard;
import com.example.beans.TeamScoreCard;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameHelper {

    /*Wide, No balls, free hits, extra runs for overthrow, Run out are not considered*/
    public static void playInning(TeamScoreCard battingTeamSc, TeamScoreCard bowlingTeamSc, int totalOvers, int target, List<PlayerScoreCard> playerScoreCards1, List<PlayerScoreCard> playerScoreCards2) {
        // { runs = [0,7] } ==>> [[ 0-6 for runs, 7 is for wicket ]]
        int playerOnStrike = 0, playerOnNonStrike = 1, nextPlayer = 2, currentOver = 1;

        //Run this loop till overs are left and target is not achieved with wickets in hand
        while (currentOver <= totalOvers && battingTeamSc.getScore() < target && battingTeamSc.getWicketsLost() < 10) {

            int bowlerIndex = ThreadLocalRandom.current().nextInt(5, 11);
            BowlingCard bowlingCard = playerScoreCards2.get(bowlerIndex).getBowlingCard();

            int runsInCurrentOver = 0;

            //Iterate for 6 balls in an over
            for (int i = 0; i < 6 && battingTeamSc.getScore() < target; i++) {

                BattingCard battingCard = playerScoreCards1.get(playerOnStrike).getBattingCard();
                int runsPerBall = assignRuns(battingTeamSc.getTeam().getPlayers().get(playerOnStrike).getBatsmanRating(), bowlingTeamSc.getTeam().getPlayers().get(bowlerIndex).getBowlerRating());

                if (runsPerBall == 7) {
                    battingTeamSc.setWicketsLost(battingTeamSc.getWicketsLost() + 1); //Wicket is fallen
                    //increment balls played by batsman
                    battingCard.setBalls(battingCard.getBalls() + 1);

                    playerOnStrike = nextPlayer;  //new player comes on strike
                    nextPlayer++;

                    //increment wickets taken by a bowler
                    bowlingCard.setWickets(bowlingCard.getWickets() + 1);

                    //if team is all-out break out of loop
                    if (battingTeamSc.getWicketsLost() == 10) break;
                } else {

                    //increment runs score by batsman along with # of balls played, batting team score, runs given by bowler
                    battingCard.setRuns(battingCard.getRuns() + runsPerBall);

                    battingCard.setBalls(battingCard.getBalls() + 1);

                    battingTeamSc.setScore(battingTeamSc.getScore() + runsPerBall);

                    bowlingCard.setRunsGiven(bowlingCard.getRunsGiven() + runsPerBall);
                    runsInCurrentOver += runsPerBall;
                    if (runsPerBall % 2 == 1) {           //swap players as strike changes
                        int temp = playerOnStrike;
                        playerOnStrike = playerOnNonStrike;
                        playerOnNonStrike = temp;
                    } else if (runsPerBall == 4) {
                        //increment fours scored by batsman
                        battingCard.setFours(battingCard.getFours() + 1);
                    } else if (runsPerBall == 6) {
                        //increment sixes scored by batsman
                        battingCard.setSixes(battingCard.getSixes() + 1);
                    }
                }


            }

            //swap players as strike changes
            int temp = playerOnStrike;
            playerOnStrike = playerOnNonStrike;
            playerOnNonStrike = temp;

            if (runsInCurrentOver == 0) {
                //increment maiden overs by the bowler
                bowlingCard.setMaidens(bowlingCard.getMaidens() + 1);
            }

            bowlingCard.setOvers(bowlingCard.getOvers() + 1);

            currentOver++;
        }

    }

    private static int assignRuns(int batsmanAdvantage, int bowlerAdvantage){
        int advantage = batsmanAdvantage + (1000-bowlerAdvantage);
        int number = ThreadLocalRandom.current().nextInt(0,2001);
        if(advantage >= number) return ThreadLocalRandom.current().nextInt(0,7);  //[0-6] only runs scored
        return ThreadLocalRandom.current().nextInt(0,8);                          //[0-7] runs and wicket considered together
    }


}
