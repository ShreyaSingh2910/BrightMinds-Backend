package org.example.repository;

import org.example.entity.User;
import org.example.entity.UserScores;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserScoreRepository extends JpaRepository<UserScores, Long> {

    // For saveScore (already using)
    java.util.Optional<UserScores> findByUserAndGameName(User user, String gameName);

    // 🔥 Leaderboard by game
    @Query("""
        SELECT us
        FROM UserScores us
        WHERE us.gameName = :gameName
        ORDER BY us.score DESC
    """)
    List<UserScores> findLeaderboard(@Param("gameName") String gameName);

    // 🔥 Recently played game (latest updated)
    @Query("""
        SELECT us
        FROM UserScores us
        WHERE us.user.email = :email
        ORDER BY us.id DESC
    """)
    List<UserScores> findRecentGame(@Param("email") String email);
}
