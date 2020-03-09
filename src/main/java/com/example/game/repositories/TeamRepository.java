package com.example.game.repositories;

import com.example.game.beans.Team;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends MongoRepository<Team,Integer> {
    Team getTeamById(int id);
}
