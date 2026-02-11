package org.example.dto;

public class ScoreRequest {
    public String email;
    public String gameName;
    public String level;
    public int score;

    public ScoreRequest() {
    }

    public int getScore() {
        return score;
    }
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

