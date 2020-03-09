package com.example.game.controller;

import com.example.game.beans.MatchScoreCard;
import com.example.game.service.GameController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CricketMatchApi {

    @Autowired
    private GameController game;

    @RequestMapping("/startmatch")
    public MatchScoreCard startMatch(){
        return game.playMatch();
    }
}
