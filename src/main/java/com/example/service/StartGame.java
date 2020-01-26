package com.example.service;

import com.example.beans.*;
import com.example.util.GameHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StartGame {

    private void assignTeamWinner(MatchScoreCard sc) {
        TeamScoreCard team1Sc = sc.getTeamScoreCard1(), team2Sc = sc.getTeamScoreCard2();
        String result = "TIE";
        if (team1Sc.getScore() > team2Sc.getScore()) {
            result = team1Sc.getTeam().getName() + " won by " + (team1Sc.getScore() - team2Sc.getScore()) + " run/s";
        } else if (team1Sc.getScore() < team2Sc.getScore()) {
            result = team2Sc.getTeam().getName() + " won by " + (10 - team2Sc.getWicketsLost()) + " wicket/s";
        }
        sc.setResult(result);
    }

    public MatchScoreCard playMatch() {
        List<Player> playerList1 = assignTeamPlayers1();
        List<Player> playerList2 = assignTeamPlayers2();
        MatchScoreCard sc = new MatchScoreCard("Alpha", "Beta", playerList1, playerList2);
        List<PlayerScoreCard> playerScoreCards1 = new ArrayList<>(11);
        List<PlayerScoreCard> playerScoreCards2 = new ArrayList<>(11);

        initializeScoreCards(playerScoreCards1);
        initializeScoreCards(playerScoreCards2);

        GameHelper.playInning(sc.getTeamScoreCard1(), sc.getTeamScoreCard2(), 50, Integer.MAX_VALUE, playerScoreCards1, playerScoreCards2);
        GameHelper.playInning(sc.getTeamScoreCard2(), sc.getTeamScoreCard1(), 50, sc.getTeamScoreCard1().getScore() + 1, playerScoreCards2, playerScoreCards1);

        mapPlayersWithScoreCards(sc, playerScoreCards1, playerScoreCards2);
        assignTeamWinner(sc);
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

    private List<Player> assignTeamPlayers1() {
        List<Player> players = new ArrayList<>(11);
        players.add(0,new Player(1,Player.Category.BATSMAN,987,18));
        players.add(1,new Player(2,Player.Category.BATSMAN,960,2));
        players.add(2,new Player(3,Player.Category.BATSMAN,981,12));
        players.add(3,new Player(4,Player.Category.BATSMAN,879,60));
        players.add(4,new Player(5,Player.Category.BATSMAN,799,100));
        players.add(5,new Player(6,Player.Category.BOWLER,567,800));
        players.add(6,new Player(7,Player.Category.BOWLER,450,861));
        players.add(7,new Player(8,Player.Category.BOWLER,200,801));
        players.add(8,new Player(9,Player.Category.BOWLER,4,760));
        players.add(9,new Player(10,Player.Category.BOWLER,9,771));
        players.add(10,new Player(11,Player.Category.BOWLER,8,940));
        return players;
    }
    private List<Player> assignTeamPlayers2() {
        List<Player> players = new ArrayList<>(11);
        players.add(0,new Player(21,Player.Category.BATSMAN,976,81));
        players.add(1,new Player(22,Player.Category.BATSMAN,961,21));
        players.add(2,new Player(23,Player.Category.BATSMAN,912,10));
        players.add(3,new Player(24,Player.Category.BATSMAN,850,70));
        players.add(4,new Player(25,Player.Category.BATSMAN,776,150));
        players.add(5,new Player(26,Player.Category.BOWLER,590,851));
        players.add(6,new Player(27,Player.Category.BOWLER,489,882));
        players.add(7,new Player(28,Player.Category.BOWLER,190,804));
        players.add(8,new Player(29,Player.Category.BOWLER,13,766));
        players.add(9,new Player(30,Player.Category.BOWLER,17,710));
        players.add(10,new Player(31,Player.Category.BOWLER,7,914));
        return players;
    }
}
