package com.example.game.repositories;

import com.example.game.beans.TeamStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamStatisticsRepository extends MongoRepository<TeamStatistics,Integer> {
    TeamStatistics getTeamStatisticsByTeamId(int teamId);
}
