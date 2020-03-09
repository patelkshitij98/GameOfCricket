package com.example.game.service;

import com.example.game.beans.TeamStatistics;
import com.example.game.repositories.TeamStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamStatisticsService {
    @Autowired
    private TeamStatisticsRepository teamStatisticsRepository;

    public TeamStatistics getTeamStatisticsById(int id){
        return teamStatisticsRepository.getTeamStatisticsByTeamId(id);
    }
}
