package org.example.service;
import org.example.dto.LeaderBoardEntry;
import org.example.entity.UserScores;
import org.example.repository.GameScoreRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeaderBoardService {

    private final GameScoreRepository repository;

    public LeaderBoardService(GameScoreRepository repository) {
        this.repository = repository;
    }


}

