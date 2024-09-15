package Main.model;

import Main.utils.ScoreEventListener;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
@SuperBuilder
@EntityListeners(ScoreEventListener.class)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Score extends AbstractEntity {
    @NotNull(message = "Kills cannot be null")
    private Integer kills;

    @NotNull(message = "Assists cannot be null")
    @Min(value = 0, message = "Assists must be greater than or equal to 0")
    private Integer assists;

    @NotNull(message = "Deaths cannot be null")
    @Min(value = 0, message = "Deaths must be greater than or equal to 0")
    private Integer deaths;

    @NotNull(message = "Headshots cannot be null")
    @Min(value = 0, message = "Headshots must be greater than or equal to 0")
    private Integer headshots;

    @NotNull(message = "MVPs cannot be null")
    @Min(value = 0, message = "MVPs must be greater than or equal to 0")
    private Integer mvps;

    @NotNull(message = "Headshot rate cannot be null")
    private Float kd;

    @NotNull(message = "Headshot rate cannot be null")
    private Integer hsRate;

    public void addKills(Integer kills) {
        this.kills += kills;
    }
    public void addAssists(Integer assists) {
        this.assists += assists;
    }
    public void addDeaths(Integer deaths) {
        this.deaths += deaths;
    }
    public void addHeadshots(Integer headshots) {
        this.headshots += headshots;
    }
    public void addMVPs(Integer mvps) {
        this.mvps += mvps;
    }
    public int compareTo(Score score) {
        int kdComparison = this.getKd().compareTo(score.getKd());
        if (kdComparison != 0) {
            return kdComparison;
        }
        int hsRateComparison = this.getHsRate().compareTo(score.getHsRate());
        if (hsRateComparison != 0) {
            return hsRateComparison;
        }
        int killsComparison = this.getKills().compareTo(score.getKills());
        if (killsComparison != 0) {
            return killsComparison;
        }
        return this.getMvps().compareTo(score.getMvps());
    }
}
