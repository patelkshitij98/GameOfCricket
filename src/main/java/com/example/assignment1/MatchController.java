package com.example.assignment1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.my_classes.*;

@RestController
public class MatchController {

    @RequestMapping("/startmatch")
    public String start_match(){
        Match match = new Match("Alpha","Beta");
        match.play_random();
        return Helper.getScoreCardHtml(match);
    }
}
