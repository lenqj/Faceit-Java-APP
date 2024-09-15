package Main.model;

import Main.utils.MyMatchScoreEventListener;
import Main.utils.ScoreEventListener;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
@SuperBuilder
@EntityListeners(MyMatchScoreEventListener.class)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MyMatchScore extends AbstractEntity  {
    @Min(value = 0, message = "First half score must be greater than or equal to 0")
    @Max(value = 12, message = "Final score cannot be greater than 12")
    private int firstHalfScore;

    @Min(value = 0, message = "Second half score must be greater than or equal to 0")
    @Max(value = 12, message = "Final score cannot be greater than 12")
    private int secondHalfScore;

    private int finalScore;
}
