package Main.service;

import Main.model.Score;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface ScoreService {
    Score createScore(Score score);
}
