package Main.model;

import Main.utils.MyMatchEventListener;
import Main.utils.MyUserEventListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
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
@EntityListeners(MyUserEventListener.class)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MyUser extends AbstractEntity{
    @Column(unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9]+[a-zA-Z0-9_-]*$", message = "Username must start with a letter or digit and can contain only letters, digits, '-' and '_'")
    @NotBlank(message = "Username is required")
    private String username;

    @XmlTransient
    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Role is required")
    @ManyToOne(cascade = {CascadeType.REFRESH})
    private UserRole role;

    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "First name must start with an uppercase letter and contain only letters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Last name must start with an uppercase letter and contain only letters")
    private String lastName;

    @NotBlank(message = "Email address is required")
    @Email(message = "Invalid email address format")
    @Column(unique = true)
    private String emailAddress;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Score score;

    private Integer numberOfMatches;

    private boolean isEnabled;

    public void addNumberOfMatches(Integer numberOfMatches) {
        this.numberOfMatches += numberOfMatches;
    }

    public MyUser compareTo(MyUser user) {
        int scoreComparison = this.getScore().compareTo(user.getScore());
        if (scoreComparison > 0) {
            return this;
        }else if (scoreComparison < 0) {
            return user;
        }else{
            int numberOfMatchesComparison = this.numberOfMatches.compareTo(user.getNumberOfMatches());
            if (numberOfMatchesComparison > 0) {
                return this;
            } else if (numberOfMatchesComparison < 0) {
                return user;
            }else{
                return this;
            }
        }
    }

}
