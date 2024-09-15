package Main.model;

import Main.utils.MyMatchEventListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
@SuperBuilder
@EntityListeners(MyMatchEventListener.class)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MyMatch extends AbstractEntity{
    @NotBlank(message = "Name is required")
    @Column(unique = true)
    private String name;

    @NotNull(message = "Team A players list is required")
    @Size(min = 1, message = "Team A must have at least one member")
    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "my_match_team_a",
            joinColumns = @JoinColumn(name = "my_match_id"),
            inverseJoinColumns = @JoinColumn(name = "my_user_in_match_id"))
    private List<MyUserInMatch> teamA;

    @NotNull(message = "Team B players list is required")
    @Size(min = 1, message = "Team B must have at least one member")
    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "my_match_team_b",
            joinColumns = @JoinColumn(name = "my_match_id"),
            inverseJoinColumns = @JoinColumn(name = "my_user_in_match_id"))
    private List<MyUserInMatch> teamB;

    @XmlTransient
    @NotNull(message = "Start time is required")
    private Date startTimeMatch;

    @XmlTransient
    @NotNull(message = "End time is required")
    private Date endTimeMatch;

    @NotNull(message = "Team A leader is required")
    @ManyToOne(cascade = {CascadeType.REFRESH})
    private MyUser teamLeaderA;

    @NotNull(message = "Team B leader is required")
    @ManyToOne(cascade = {CascadeType.REFRESH})
    private MyUser teamLeaderB;


    @NotNull(message = "Team A score is required")
    @ManyToOne(cascade = {CascadeType.REMOVE})
    private MyMatchScore teamAScore;

    @NotNull(message = "Team B score is required")
    @ManyToOne(cascade = {CascadeType.REMOVE})
    private MyMatchScore teamBScore;

    @NotBlank(message = "Map is required")
    private String map;
}
