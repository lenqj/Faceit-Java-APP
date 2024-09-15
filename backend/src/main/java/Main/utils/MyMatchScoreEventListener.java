package Main.utils;

import Main.model.MyMatchScore;
import Main.model.Score;
import jakarta.persistence.PrePersist;
import org.springframework.stereotype.Component;

@Component
public class MyMatchScoreEventListener {
    @PrePersist
    public void calculateDerivedFields(MyMatchScore myMatchScore) {
        //myMatchScore.setID(myMatchScore.getID());
        myMatchScore.setFinalScore(myMatchScore.getFirstHalfScore() + myMatchScore.getSecondHalfScore());
    }
}
