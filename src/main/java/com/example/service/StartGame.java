package com.example.service;

import com.example.beans.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class StartGame {

    private void assignWinner(MatchScoreCard sc) {
        TeamScoreCard team1Sc = sc.getTeamScoreCard1(), team2Sc = sc.getTeamScoreCard2();
        String result = "TIE";
        if (team1Sc.getScore() > team2Sc.getScore()) {
            result = team1Sc.getTeam().getName() + " won by " + (team1Sc.getScore() - team2Sc.getScore()) + " run/s";
        } else if (team1Sc.getScore() < team2Sc.getScore()) {
            result = team2Sc.getTeam().getName() + " won by " + (10 - team2Sc.getWicketsLost()) + " wicket/s";
        }
        sc.setResult(result);
    }

    /*Wide, No balls, free hits, extra runs for overthrow, Run out are not considered*/
    private void playInning(TeamScoreCard battingTeamSc, int totalOvers, int target, List<PlayerScoreCard> playerScoreCards1, List<PlayerScoreCard> playerScoreCards2) {
        // { runs = [0,7] } ==>> [[ 0-6 for runs, 7 is for wicket ]]
        int playerOnStrike = 0, playerOnNonStrike = 1, nextPlayer = 2, currentOver = 1;

        //Run this loop till overs are left and target is not achieved with wickets in hand
        while (currentOver <= totalOvers && battingTeamSc.getScore() < target && battingTeamSc.getWicketsLost() < 10) {

            int bowlerIndex = ThreadLocalRandom.current().nextInt(5, 11);
            BowlingCard bowlingCard = playerScoreCards2.get(bowlerIndex).getBowlingCard();

            int runsInCurrentOver = 0;

            //Iterate for 6 balls in an over
            for (int i = 0; i < 6 && battingTeamSc.getScore() < target; i++) {
                int runsPerBall = ThreadLocalRandom.current().nextInt(0,8);
                BattingCard battingCard = playerScoreCards1.get(playerOnStrike).getBattingCard();


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

    public MatchScoreCard playMatch() {
        MatchScoreCard sc = new MatchScoreCard("Alpha", "Beta");
        List<PlayerScoreCard> playerScoreCards1 = new ArrayList<>(11);
        List<PlayerScoreCard> playerScoreCards2 = new ArrayList<>(11);

        initializeScoreCards(playerScoreCards1);
        initializeScoreCards(playerScoreCards2);

        playInning(sc.getTeamScoreCard1(), 50, Integer.MAX_VALUE, playerScoreCards1, playerScoreCards2);
        playInning(sc.getTeamScoreCard2(), 50, sc.getTeamScoreCard1().getScore() + 1, playerScoreCards2, playerScoreCards1);

        mapPlayersWithScoreCards(sc, playerScoreCards1, playerScoreCards2);
        assignWinner(sc);
        return sc;
    }

    private void initializeScoreCards(List<PlayerScoreCard> playerScoreCards) {
        for (int i = 0; i < 11; i++)
            playerScoreCards.add(i, new PlayerScoreCard());

    }


    private void mapPlayersWithScoreCards(MatchScoreCard sc, List<PlayerScoreCard> playerScoreCards1, List<PlayerScoreCard> playerScoreCards2) {
        List<Player> players1 = sc.getTeamScoreCard1().getTeam().getPlayers();
        List<Player> players2 = sc.getTeamScoreCard2().getTeam().getPlayers();
        Map<Integer,PlayerScoreCard> playerScMap1 = sc.getTeamScoreCard1().getScoreCards();
        Map<Integer,PlayerScoreCard> playerScMap2 = sc.getTeamScoreCard2().getScoreCards();
        for (int i = 0; i < 11; i++) {
                playerScMap1.put(players1.get(i).getId(), playerScoreCards1.get(i));
                playerScMap2.put(players2.get(i).getId(), playerScoreCards2.get(i));
        }
    }

}
