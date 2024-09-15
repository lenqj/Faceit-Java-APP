package Main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MyUserInMatch extends AbstractEntity {
    @NotNull(message = "User is required")
    @ManyToOne(cascade = {CascadeType.REFRESH})
    private MyUser myUser;

    @NotNull(message = "Score is required")
    @ManyToOne(cascade = {CascadeType.REMOVE})
    private Score score;
}
