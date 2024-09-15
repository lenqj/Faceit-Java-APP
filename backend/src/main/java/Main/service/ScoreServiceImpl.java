package Main.service;

import Main.model.Score;
import Main.repository.ScoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class ScoreServiceImpl implements ScoreService {
    private final ScoreRepository scoreRepository;

    @Override
    public Score createScore(Score score) {
        return scoreRepository.save(score);
    }
}
