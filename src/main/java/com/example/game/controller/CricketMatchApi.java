package com.example.game.controller;

import com.example.game.beans.MatchScoreCard;
import com.example.game.beans.Team;
import com.example.game.beans.TeamStatistics;
import com.example.game.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CricketMatchApi {

    @Autowired
    private GameService gameService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private MatchScoreCardService matchScoreCardService;

    @Autowired
    private TeamStatisticsService teamStatisticsService;

    @GetMapping("/startmatch")
    public MatchScoreCard startMatch(){
        return gameService.playMatch();
    }

    @GetMapping("/teams/{id}")
    public Team getTeamById(@PathVariable int id){
        return teamService.getTeamById(id);
    }

    @GetMapping("/allMatchScoreCards")
    public List<?> getMatchScoreCardById(){
        return matchScoreCardService.getAllMatchScoreCards();
    }
    @GetMapping("/teamStats/{id}")
    public TeamStatistics getTeamStatisticsById(@PathVariable int id){
        return teamStatisticsService.getTeamStatisticsById(id);
    }

}
