package org.example.repository;

import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(value = """
        SELECT rank FROM (
            SELECT u.email,
                   RANK() OVER (ORDER BY SUM(us.score) DESC) AS rank
            FROM users u
            JOIN user_scores us ON u.id = us.user_id
            GROUP BY u.id
        ) ranked
        WHERE email = :email
        """, nativeQuery = true)
    Integer findUserRank(@Param("email") String email);
}


