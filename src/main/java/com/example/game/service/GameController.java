package com.example.game.service;

import com.example.game.beans.*;
import com.example.game.util.CricketGame;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameController {

    private Team team1;
    private Team team2;
    private MatchScoreCard matchScoreCard;
    private Helper helper;

    private class Helper{
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
        return matchScoreCard;
    }

    private void initializeTeamsAndScoreCards() {
        this.team1 = new Team(1, "Alpha", assignTeam1Players());
        this.team2 = new Team(2, "Beta", assignTeam2Players());
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

    private void setupInning(int inningNumber,List<List<Player>> players2DList,
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
            result = team1.getName() + " won by " + (helper.teamScoreCard1.getScore() - helper.teamScoreCard2.getScore()) + " run/s";
        } else if (helper.teamScoreCard1.getScore() < helper.teamScoreCard2.getScore()) {
            result = team2.getName() + " won by " + (10 - helper.teamScoreCard2.getWicketsLost()) + " wicket/s";
        }
        matchScoreCard.setResult(result);
    }

    private List<Player> assignTeam1Players() {
        List<Player> players = new ArrayList<>(11);
        int[] batsmanRating = new int[]{987, 960, 981, 879, 799, 567, 450, 200, 4, 9, 8};
        int[] bowlerRating = new int[]{18, 2, 12, 60, 100, 800, 861, 801, 760, 771, 940};
        for(int i=0;i<5;i++)
            players.add(i, new Player(i+1, Player.Category.BATSMAN, batsmanRating[i], bowlerRating[i]));
        for(int i=5;i<11;i++)
            players.add(i, new Player(i+1, Player.Category.BOWLER, batsmanRating[i], bowlerRating[i]));
        return players;
    }

    private List<Player> assignTeam2Players() {
        List<Player> players = new ArrayList<>(11);
        int[] batsmanRating = new int[]{976, 961, 912, 850, 776, 590, 489, 190, 13, 17, 7};
        int[] bowlerRating = new int[]{81, 21, 10, 70, 150, 851, 882, 804, 766, 710, 914};
        for(int i=0, j=21 ;i<5;i++,j++)
            players.add(i, new Player(j, Player.Category.BATSMAN, batsmanRating[i], bowlerRating[i]));
        for(int i=5, j=26 ;i<11;i++,j++)
            players.add(i, new Player(j, Player.Category.BOWLER, batsmanRating[i], bowlerRating[i]));
        return players;
    }
}
