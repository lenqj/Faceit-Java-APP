package Main.utils;

import Main.model.Score;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class ScoreEventListener {
    @PreUpdate
    public void updateDerivedFieldsBeforeUpdate(Score score) {
        calculateDerivedFields(score);
    }
    @PrePersist
    public void calculateDerivedFields(Score score) {
        score.setID(score.getID());
        score.setKd(calculateKD(score.getKills(), score.getDeaths()));
        score.setHsRate(calculateHSRate(score.getKills(), score.getHeadshots()));
    }

    private float calculateKD(int kills, int deaths) {
        if (deaths == 0) {
            return kills;
        }
        return (float) kills / deaths;
    }

    private int calculateHSRate(int kills, int headshots) {
        if (kills == 0) {
            return 0;
        }
        return (int) ((float) headshots / kills * 100);
    }
}
