package org.example.repository;

import org.example.entity.UserScores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameScoreRepository
        extends JpaRepository<UserScores, Long> {

    List<UserScores> findAllByOrderByScoreDesc();
}

