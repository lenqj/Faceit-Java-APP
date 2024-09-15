package Main.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
@SuperBuilder
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfirmationToken extends AbstractEntity {
    @NotBlank(message="Confirmation Token must not be blank")
    private String confirmationToken;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime expiresAt;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime confirmedAt;
    private String myUser;

    public ConfirmationToken(MyUser myUser) {
        this.confirmationToken = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.expiresAt = this.createdAt.plusMinutes(15);
        this.myUser = myUser.getUsername();
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public boolean isConfirmed() {
        return confirmedAt == null;
    }

    public boolean isValidForUser(String username) {
        return !isExpired() && isConfirmed() && myUser.equals(username);
    }

    public void confirm() {
        if (isConfirmed()) {
            this.confirmedAt = LocalDateTime.now();
        }
    }
    public void refetch(String username) {
        if (!isValidForUser(username)){
            this.confirmationToken = UUID.randomUUID().toString();
            this.createdAt = LocalDateTime.now();
            this.expiresAt = this.createdAt.plusMinutes(15);
            this.confirmedAt = null;
        }
    }
}
