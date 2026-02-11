package org.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_scores")
public class UserScores {

    public UserScores( String gameName, int score, User user) {
        this.gameName = gameName;
        this.score = score;
        this.user = user;
        this.category = "General";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_id")
    private Long id;


    @Column(name = "game_name")
    private String gameName;

    @Column(name = "game_category")
    private String category;

    @Column(name = "score")
    private int score;

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private int gamesPlayed;
    public UserScores() {}

    public Long getId() { return id; }
    public String getGameName() { return gameName; }
    public void setGameName(String gameName) { this.gameName = gameName; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
}