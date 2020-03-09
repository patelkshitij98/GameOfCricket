package com.example.game.service;

import com.example.game.beans.*;
import com.example.game.repositories.*;
import com.example.game.util.CricketGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameService {

    private Team team1;
    private Team team2;
    private MatchScoreCard matchScoreCard;
    private Helper helper;

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MatchScoreCardRepository matchScoreCardRepository;
    @Autowired
    private TeamStatisticsRepository teamStatisticsRepository;

    private class Helper {
        TeamScoreCard teamScoreCard1;
        TeamScoreCard teamScoreCard2;
        List<PlayerScoreCard> playerScoreCards1;
        List<PlayerScoreCard> playerScoreCards2;
    }

    public MatchScoreCard playMatch() {
        initializeTeamsAndScoreCards();
        setupInningsAndPlay();
        mapPlayersWithPlayerScoreCards();
        mapTeamsWithTeamScoreCards();
        assignWinner();
        storeMatchStatistics();
        return matchScoreCard;
    }

    private void initializeTeamsAndScoreCards() {
        this.team1 = teamRepository.getTeamById(1);
        this.team2 = teamRepository.getTeamById(2);
        this.matchScoreCard = new MatchScoreCard();
        this.helper = new Helper();
        this.helper.teamScoreCard1 = new TeamScoreCard();
        this.helper.teamScoreCard2 = new TeamScoreCard();
        this.helper.playerScoreCards1 = new ArrayList<>(11);
        this.helper.playerScoreCards2 = new ArrayList<>(11);
        initializePlayerScoreCards(helper.playerScoreCards1);
        initializePlayerScoreCards(helper.playerScoreCards2);
    }

    private void setupInningsAndPlay() {
        List<List<Player>> players2DList = new ArrayList<>(2);
        List<List<PlayerScoreCard>> playerScoreCards2DList = new ArrayList<>(2);

        setupInning(1, players2DList, playerScoreCards2DList);
        CricketGame.playInning(players2DList, helper.teamScoreCard1, Integer.MAX_VALUE, playerScoreCards2DList);
        setupInning(2, players2DList, playerScoreCards2DList);
        CricketGame.playInning(players2DList, helper.teamScoreCard2, helper.teamScoreCard1.getScore() + 1, playerScoreCards2DList);
    }

    private void setupInning(int inningNumber, List<List<Player>> players2DList,
                             List<List<PlayerScoreCard>> playerScoreCards2DList) {
        playerScoreCards2DList.add(0, (inningNumber == 1) ? helper.playerScoreCards1 : helper.playerScoreCards2);
        playerScoreCards2DList.add(1, (inningNumber == 1) ? helper.playerScoreCards2 : helper.playerScoreCards1);
        players2DList.add(0, (inningNumber == 1) ? team1.getPlayers() : team2.getPlayers());
        players2DList.add(1, (inningNumber == 1) ? team2.getPlayers() : team1.getPlayers());

    }

    private void initializePlayerScoreCards(List<PlayerScoreCard> playerScoreCards) {
        for (int i = 0; i < 11; i++)
            playerScoreCards.add(i, new PlayerScoreCard());
    }

    private void mapPlayersWithPlayerScoreCards() {
        Map<Integer, PlayerScoreCard> playerScMap1 = new HashMap<>(11);
        Map<Integer, PlayerScoreCard> playerScMap2 = new HashMap<>(11);
        helper.teamScoreCard1.setPlayerScoreCardMap(playerScMap1);
        helper.teamScoreCard2.setPlayerScoreCardMap(playerScMap2);
        for (int i = 0; i < 11; i++) {
            PlayerScoreCard playerScoreCard1 = helper.playerScoreCards1.get(i);
            BattingCard battingCard1 = playerScoreCard1.getBattingCard();
            BowlingCard bowlingCard1 = playerScoreCard1.getBowlingCard();
            helper.playerScoreCards1.set(i,playerScoreCard1);

            PlayerScoreCard playerScoreCard2 = helper.playerScoreCards2.get(i);
            BattingCard battingCard2 = playerScoreCard2.getBattingCard();
            BowlingCard bowlingCard2 = playerScoreCard2.getBowlingCard();
            helper.playerScoreCards2.set(i,playerScoreCard2);

            playerScMap1.put(team1.getPlayers().get(i).getId(), helper.playerScoreCards1.get(i));
            playerScMap2.put(team2.getPlayers().get(i).getId(), helper.playerScoreCards2.get(i));
        }
    }

    private void mapTeamsWithTeamScoreCards() {
        Map<Integer, TeamScoreCard> teamScMap = new HashMap<>(2);
        teamScMap.put(team1.getId(), helper.teamScoreCard1);
        teamScMap.put(team2.getId(), helper.teamScoreCard2);
        matchScoreCard.setTeamScoreCardMap(teamScMap);
    }

    private void assignWinner() {

        String result = "TIE";
        if (helper.teamScoreCard1.getScore() > helper.teamScoreCard2.getScore()) {
            matchScoreCard.setWinnerTeamId(team1.getId());
            result = team1.getName() + " won by " + (helper.teamScoreCard1.getScore() - helper.teamScoreCard2.getScore()) + " run/s";
        } else if (helper.teamScoreCard1.getScore() < helper.teamScoreCard2.getScore()) {
            matchScoreCard.setWinnerTeamId(team2.getId());
            result = team2.getName() + " won by " + (10 - helper.teamScoreCard2.getWicketsLost()) + " wicket/s";
        }
        matchScoreCard.setResult(result);
    }

    private void storeMatchStatistics() {
        matchScoreCardRepository.save(matchScoreCard);
        TeamStatistics team1Statistics = teamStatisticsRepository.getTeamStatisticsByTeamId(team1.getId());
        if(team1Statistics == null){
            team1Statistics = new TeamStatistics(team1.getId(),0,0,0);
        }
        TeamStatistics team2Statistics = teamStatisticsRepository.getTeamStatisticsByTeamId(team2.getId());
        if(team2Statistics == null){
            team2Statistics = new TeamStatistics(team2.getId(),0,0,0);
        }
        if(matchScoreCard.getWinnerTeamId() == team1.getId()){
            team1Statistics.setWon(team1Statistics.getWon() + 1);
            team2Statistics.setLost(team2Statistics.getLost() + 1);
        }
        else if(matchScoreCard.getWinnerTeamId() == team2.getId()){
            team2Statistics.setWon(team2Statistics.getWon() + 1);
            team1Statistics.setLost(team1Statistics.getLost() + 1);
        }
        else{
            team1Statistics.setDraw(team1Statistics.getDraw() + 1);
            team2Statistics.setDraw(team2Statistics.getDraw() + 1);
        }
        teamStatisticsRepository.save(team1Statistics);
        teamStatisticsRepository.save(team2Statistics);
    }


}
