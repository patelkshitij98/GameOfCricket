package com.example.assignment1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MatchController {

    @RequestMapping("/startmatch")
    public String start_match(){
        Match match = new Match("Alpha","Beta");
        match.play_random();
        return match.getSummary();
    }
}
