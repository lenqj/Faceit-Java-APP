package Main.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
@SuperBuilder
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MyUserSession extends AbstractEntity {
    @NotNull(message = "User is required")
    @ManyToOne(cascade = {CascadeType.REFRESH})
    private MyUser myUser;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime loginAt;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime logoutAt;
}
