package com.example.game.service;

import com.example.game.repositories.MatchScoreCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchScoreCardService {
    @Autowired
    MatchScoreCardRepository matchScoreCardRepository;

    public List<?> getAllMatchScoreCards(){
        return matchScoreCardRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

}
