package org.example.service;

import org.example.dto.LeaderBoardEntry;
import org.example.dto.ScoreRequest;
import org.example.dto.ProfileResponse;

import java.util.List;

public interface GameService {

    String saveScore(ScoreRequest request);

    String updateProfile(String email, String name, String avatar);

    boolean profileStatus(String email);

    String getAvatar(String email);

    ProfileResponse getProfileData(String email, String gameName);

    String getRecentGame(String email);

    double getAverageScore(String email);
    public List<LeaderBoardEntry> getLeaderboard();

}