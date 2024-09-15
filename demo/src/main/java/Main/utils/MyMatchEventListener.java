package Main.utils;

import Main.DTO.MyUserCreationDTO;
import Main.exceptions.ApiExceptionResponse;
import Main.mapper.MyUserMapper;
import Main.model.MyMatch;
import Main.model.MyUserInMatch;
import Main.model.Score;
import Main.service.MyUserService;
import Main.service.ScoreService;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class MyMatchEventListener {
    private final ScoreService scoreService;

    public MyMatchEventListener(@Lazy ScoreService scoreService) {
        this.scoreService = scoreService;
    }
    @PrePersist
    public void notifyAvailability(MyMatch myMatch) throws ApiExceptionResponse {
        for (MyUserInMatch user : myMatch.getTeamA()) {
            updateScore(user);
        }
        for (MyUserInMatch user : myMatch.getTeamB()) {
            updateScore(user);
        }
    }
    private void updateScore(MyUserInMatch user) {
        Score userScore = user.getMyUser().getScore();
        user.getMyUser().addNumberOfMatches(1);
        Score userMatchScore = user.getScore();
        userScore.addKills(userMatchScore.getKills());
        userScore.addDeaths(userMatchScore.getDeaths());
        userScore.addAssists(userMatchScore.getAssists());
        userScore.addHeadshots(userMatchScore.getHeadshots());
        userScore.addMVPs(userMatchScore.getMvps());
        scoreService.createScore(userScore);
    }
}
