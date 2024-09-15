package Main.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
@SuperBuilder
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MyTeam extends AbstractEntity {
    @NotBlank(message = "Name is required")
    @Column(unique = true)
    private String name;

    @NotNull(message = "Team members list is required")
    @Size(min = 1, message = "Team must have at least one member")
    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(name = "my_team_team_members",
            joinColumns = @JoinColumn(name = "my_team_id"),
            inverseJoinColumns = @JoinColumn(name = "my_user_id"))
    private List<MyUser> teamMembers;

    @NotNull(message = "Team leader is required")
    @ManyToOne(cascade = {CascadeType.REFRESH})
    private MyUser teamLeader;

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(name = "my_team_matches",
            joinColumns = @JoinColumn(name = "my_team_id"),
            inverseJoinColumns = @JoinColumn(name = "my_match_id"))
    private List<MyMatch> matches;

    @NotNull(message = "Number of matches is required")
    private Integer numberOfMatches;

    public void addNumberOfMatches(Integer numberOfMatches) {
        this.numberOfMatches += numberOfMatches;
    }
}
