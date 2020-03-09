package com.example.game.util;

import com.example.game.beans.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class CricketGame {
    static final int TOTAL_OVERS = 50;

    private CricketGame(){
        throw new RuntimeException("Cannot be instantiated");
    }

    /*Wide, No balls, free hits, extra runs for overthrow, Run out are not considered*/
    public static void playInning(List<List<Player>> playerList, TeamScoreCard battingTeamSc, int target, List<List<PlayerScoreCard>> playerScoreCards) {
        List<Player> battingTeamPlayers = playerList.get(0), bowlingTeamPlayers = playerList.get(1);
        List<PlayerScoreCard> playerScoreCards1 = playerScoreCards.get(0), playerScoreCards2 = playerScoreCards.get(1);
        int playerOnStrike = 0, playerOnNonStrike = 1, nextPlayer = 2;

        //Run this loop till overs are left and target is not achieved with wickets in hand
        for (int currentOver = 0; currentOver < TOTAL_OVERS && battingTeamSc.getScore() < target && battingTeamSc.getWicketsLost() < 10; currentOver++) {
            int runsInCurrentOver = 0, currentBall=0, bowlerIndex = ThreadLocalRandom.current().nextInt(5, 11);
            BowlingCard bowlingCard = playerScoreCards2.get(bowlerIndex).getBowlingCard();

            //Iterate for 6 balls in an over
            for (int i = 0; i < 6 && battingTeamSc.getScore() < target && battingTeamSc.getWicketsLost() < 10; i++) {
                currentBall++;
                BattingCard battingCard = playerScoreCards1.get(playerOnStrike).getBattingCard();

                int runsPerBall = generateRuns(battingTeamPlayers.get(playerOnStrike).getBatsmanRating(), bowlingTeamPlayers.get(bowlerIndex).getBowlerRating());
                if (runsPerBall == 7) {
                    updateScoreCardsForWicketLost(battingTeamSc, bowlingCard, battingCard);
                    playerOnStrike = nextPlayer;  //new player comes on strike
                    nextPlayer++;
                } else {
                    updateScoreCardsForRunsScored(battingTeamSc, bowlingCard, battingCard, runsPerBall);
                    runsInCurrentOver += runsPerBall;
                    if (runsPerBall % 2 == 1) {           //swap players as strike changes
                        int temp = playerOnStrike;
                        playerOnStrike = playerOnNonStrike;
                        playerOnNonStrike = temp;
                    }
                }
            }
            //swap players as strike changes after over finishes
            int temp = playerOnStrike;
            playerOnStrike = playerOnNonStrike;
            playerOnNonStrike = temp;

            updateScoreCardAfterOverFinishes(battingTeamSc, currentOver, currentBall, bowlingCard, runsInCurrentOver);
        }
    }

    private static void updateScoreCardAfterOverFinishes(TeamScoreCard battingTeamSc, int currentOver, int currentBall, BowlingCard bowlingCard, int runsInCurrentOver) {
        if (runsInCurrentOver == 0)
            bowlingCard.setMaidens(bowlingCard.getMaidens() + 1);
        double overInDecimal = (currentBall == 6) ? 1 : ((currentBall * 1.0) / 10);
        battingTeamSc.setOvers(currentOver + overInDecimal);
        bowlingCard.setOvers(bowlingCard.getOvers() + overInDecimal);
    }

    private static void updateScoreCardsForRunsScored(TeamScoreCard battingTeamSc, BowlingCard bowlingCard, BattingCard battingCard, int runsPerBall) {
        battingCard.setRuns(battingCard.getRuns() + runsPerBall);
        battingCard.setBalls(battingCard.getBalls() + 1);
        battingTeamSc.setScore(battingTeamSc.getScore() + runsPerBall);
        bowlingCard.setRunsGiven(bowlingCard.getRunsGiven() + runsPerBall);

        if (runsPerBall == 4)
            battingCard.setFours(battingCard.getFours() + 1);
        if (runsPerBall == 6)
            battingCard.setSixes(battingCard.getSixes() + 1);
    }

    private static void updateScoreCardsForWicketLost(TeamScoreCard battingTeamSc, BowlingCard bowlingCard, BattingCard battingCard) {
        battingTeamSc.setWicketsLost(battingTeamSc.getWicketsLost() + 1);
        battingCard.setBalls(battingCard.getBalls() + 1);
        bowlingCard.setWicketsTaken(bowlingCard.getWicketsTaken() + 1);
    }

    private static int generateRuns(int batsmanRating, int bowlerRating){
        int batsmanAdvantage = batsmanRating + (1000-bowlerRating);
        int rndNumber = ThreadLocalRandom.current().nextInt(0,2001);
        if(batsmanAdvantage >= rndNumber) {
            return ThreadLocalRandom.current().nextInt(0,7);  //[0-6] only runs scored
        }
        return ThreadLocalRandom.current().nextInt(0,8);   //[0-7] runs and wicket considered together
    }

}
