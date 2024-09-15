package Main.service;

import Main.DTO.MyMatchDTO;
import Main.model.MyMatchScore;
import org.springframework.stereotype.Component;

@Component
public interface MyMatchScoreService {
    MyMatchScore createMyMatchScore(MyMatchScore myMatchScore);
}
