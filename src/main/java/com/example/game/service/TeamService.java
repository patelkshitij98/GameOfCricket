package com.example.game.service;

import com.example.game.beans.Team;
import com.example.game.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    public Team getTeamById(int id){
        return teamRepository.getTeamById(id);
    }
}
