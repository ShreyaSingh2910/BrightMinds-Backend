package org.example.dto;

import java.time.LocalDateTime;

public class ProfileResponse {

    private String name;
    private String avatar;
    private LocalDateTime joined;
    private int totalScore;
    private int gamesPlayed;
    private double avgScore;
    private int rank;

    public ProfileResponse(
            String name,
            String avatar,
            LocalDateTime joined,
            int totalScore,
            int gamesPlayed,
            double avgScore,
            int rank
    ) {
        this.name = name;
        this.avatar = avatar;
        this.joined = joined;
        this.totalScore = totalScore;
        this.gamesPlayed = gamesPlayed;
        this.avgScore = avgScore;
        this.rank = rank;
    }

    public String getName() { return name; }
    public String getAvatar() { return avatar; }
    public LocalDateTime getJoined() { return joined; }
    public int getTotalScore() { return totalScore; }
    public int getGamesPlayed() { return gamesPlayed; }
    public double getAvgScore() { return avgScore; }
    public int getRank() { return rank; }
}