package org.example.dto;
public class LeaderBoardEntry {
    private int rank;
    private String name;
    private String email;
    private int score;

    public LeaderBoardEntry(int rank, String name, String email,int score) {
        this.rank = rank;
        this.name = name;
        this.email = email;
        this.score = score;
    }

    public int getRank() { return rank; }
    public String getName() { return name; }
    public int getScore() { return score; }
    public String getEmail() { return email; }
}
