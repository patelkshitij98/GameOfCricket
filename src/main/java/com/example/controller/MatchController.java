package com.example.controller;

import com.example.beans.MatchScoreCard;
import com.example.service.Helper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchController {
    @RequestMapping("/startmatch")
    public MatchScoreCard start_match(){
        return Helper.play_match();
    }
}
