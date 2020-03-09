package com.example.game.repositories;

import com.example.game.beans.MatchScoreCard;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchScoreCardRepository extends MongoRepository<MatchScoreCard, ObjectId> {
//    @Query(value="{}",fields = "{'teamScoreCardMap.1.playerScoreCardMap' : 0, 'teamScoreCardMap.2.playerScoreCardMap' : 0 }")
    List<MatchScoreCard> findAll(Sort sort);

}
