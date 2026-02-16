package org.example.service;

import jakarta.transaction.Transactional;
import org.example.dto.LeaderBoardEntry;
import org.example.dto.ProfileResponse;
import org.example.dto.ScoreRequest;
import org.example.entity.User;
import org.example.entity.UserScores;
import org.example.repository.GameScoreRepository;
import org.example.repository.UserRepository;
import org.example.repository.UserScoreRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameServiceImpl implements GameService {

    private final UserRepository userRepository;
    private final UserScoreRepository userScoreRepository;
    GameScoreRepository gsr;
    public GameServiceImpl(UserRepository userRepository,
                           UserScoreRepository userScoreRepository,GameScoreRepository gsr) {
        this.userRepository = userRepository;
        this.userScoreRepository = userScoreRepository;
        this.gsr=gsr;
    }

    private String getBaseGame(String gameName) {
        int idx = gameName.indexOf("-");
        return idx == -1 ? gameName : gameName.substring(0, idx);
    }

    @Transactional
    public String saveScore(ScoreRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Profile not created yet"));

        return userScoreRepository.findByUserAndGameName(user, request.getGameName())
                .map(existing -> {
                    existing.setScore(existing.getScore() + request.getScore());
                    existing.setGamesPlayed(existing.getGamesPlayed() + 1);
                    userScoreRepository.save(existing);

                    return "Score updated";
                })
                .orElseGet(() -> {
                    UserScores us = new UserScores(
                            request.getGameName(),
                            request.getScore(),
                            user
                    );
                    us.setGamesPlayed(1);
                    userScoreRepository.save(us);

                    return "First time played";
                });
    }

    @Transactional
    public String updateProfile(String email, String name, String avatar) {

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User u = new User();
                    u.setEmail(email);
                    u.setDateJoined(LocalDateTime.now());
                    return u;
                });

        user.setName(name);
        user.setAvatar(avatar);

        userRepository.save(user);
        return "Profile Updated";
    }


    public boolean profileStatus(String email) {
        return userRepository.findByEmail(email)
                .map(u -> u.getName() != null && u.getAvatar() != null)
                .orElse(false);
    }

    public String getAvatar(String email) {
        return userRepository.findByEmail(email)
                .map(User::getAvatar)
                .orElse(null);
    }

    public String getRecentGame(String email) {
        List<UserScores> list = userScoreRepository.findRecentGame(email);
        return list.isEmpty() ? null : list.get(0).getGameName();
    }
    @Transactional
    public double getAverageScore(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        int total = user.getScores().stream().mapToInt(UserScores::getScore).sum();
        return total / 12.0;
    }
    @Transactional
    public ProfileResponse getProfileData(String email, String gameName) {

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return new ProfileResponse("Player", null, null, 0, 0, 0, -1);
        }

        String baseGame = getBaseGame(gameName);
        List<UserScores> gameEntries = user.getScores().stream()
                .filter(s -> getBaseGame(s.getGameName())
                        .equalsIgnoreCase(baseGame))
                .toList();

        int totalScore = gameEntries.stream()
                .mapToInt(UserScores::getScore)
                .sum();

        int gamesPlayed = gameEntries.stream()
                .mapToInt(UserScores::getGamesPlayed)
                .sum();

        double avgScore = user.getScores().stream()
                .mapToInt(UserScores::getScore)
                .sum() / 12.0;

        int rank = 1;

        List<User> allUsers = userRepository.findAll();

        for (User u : allUsers) {

            if (u.getEmail().equals(email)) continue;

            int otherScore = u.getScores().stream()
                    .filter(s -> getBaseGame(s.getGameName())
                            .equalsIgnoreCase(baseGame))
                    .mapToInt(UserScores::getScore)
                    .sum();

            if (otherScore > totalScore) {
                rank++;
            }
        }

        return new ProfileResponse(
                user.getName(),
                user.getAvatar(),
                user.getDateJoined(),
                totalScore,
                gamesPlayed,
                avgScore,
                rank
        );
    }

    public List<LeaderBoardEntry> getLeaderboard() {

        List<UserScores> scores = gsr.findAll(); 
        Map<User, Integer> userTotalScore = new HashMap<>();

        for (UserScores us : scores) {
            User user = us.getUser();
            userTotalScore.put(
                    user,
                    userTotalScore.getOrDefault(user, 0) + us.getScore()
            );
        }

        List<Map.Entry<User, Integer>> sorted =
                userTotalScore.entrySet()
                        .stream()
                        .sorted((a, b) -> b.getValue() - a.getValue())
                        .toList();

        List<LeaderBoardEntry> leaderboard = new ArrayList<>();
        int rank = 1;

        for (Map.Entry<User, Integer> entry : sorted) {
            User u = entry.getKey();

            leaderboard.add(
                    new LeaderBoardEntry(
                            rank++,
                            u.getName(),
                            u.getEmail(),   
                            entry.getValue()
                    )
            );
        }

        return leaderboard;
    }



}
