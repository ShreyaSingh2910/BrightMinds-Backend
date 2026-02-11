package org.example.controller;
import org.example.dto.ProfileResponse;
import jakarta.transaction.Transactional;
import org.example.dto.ScoreRequest;
import org.example.entity.User;
import org.example.entity.UserScores;
import org.example.repository.UserRepository;
import org.example.repository.UserScoreRepository;
import org.example.service.GameService;
import org.example.service.LeaderBoardService;
import org.springframework.web.bind.annotation.*;
import org.example.dto.LeaderBoardEntry;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;
    LeaderBoardService lbs;
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/saveScore")
    public String saveScore(@RequestBody ScoreRequest req) {
        return gameService.saveScore(req);
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@RequestBody User u) {
        return gameService.updateProfile(u.getEmail(), u.getName(), u.getAvatar());
    }

    @GetMapping("/profileStatus")
    public boolean profileStatus(@RequestParam String email) {
        return gameService.profileStatus(email);
    }

    @GetMapping("/avatar")
    public String avatar(@RequestParam String email) {
        return gameService.getAvatar(email);
    }

    @GetMapping("/recentGame")
    public String recentGame(@RequestParam String email) {
        return gameService.getRecentGame(email);
    }

    @GetMapping("/averageScore")
    public double avg(@RequestParam String email) {
        return gameService.getAverageScore(email);
    }

    @GetMapping("/profileData")
    public  ProfileResponse profile(
            @RequestParam String email,
            @RequestParam String gameName
    ) {
        return gameService.getProfileData(email, gameName);
    }

    @GetMapping("/leaderboard")
    public List<LeaderBoardEntry> leaderboard() {
        return gameService.getLeaderboard();
    }



}