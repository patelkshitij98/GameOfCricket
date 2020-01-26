package com.example.controller;

import com.example.beans.MatchScoreCard;
import com.example.service.StartGame;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchController {
    @RequestMapping("/startmatch")
    public MatchScoreCard startMatch(){
        StartGame game = new StartGame();
        return game.playMatch();
    }
}
