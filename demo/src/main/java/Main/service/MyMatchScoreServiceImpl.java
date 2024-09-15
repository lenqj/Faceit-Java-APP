package Main.service;

import Main.mapper.MyMatchMapper;
import Main.model.MyMatchScore;
import Main.repository.MyMatchScoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MyMatchScoreServiceImpl implements MyMatchScoreService {
    private final MyMatchScoreRepository myMatchScoreRepository;

    @Override
    public MyMatchScore createMyMatchScore(MyMatchScore myMatchScore) {
        return myMatchScoreRepository.save(myMatchScore);
    }
}
