/*
package Main.utils;

import Main.model.ConfirmationToken;
import Main.model.EmailPayload;
import Main.model.MyUser;
import Main.repository.ConfirmationTokenRepository;
import Main.service.EmailService;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
@Component
@Transactional
public class ConfirmationTokenEventListener {

    private final EmailService emailService;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationTokenEventListener(@Lazy EmailService emailService, @Lazy ConfirmationTokenRepository confirmationTokenRepository) {
        this.emailService = emailService;
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @PostPersist
    public void sendEmail(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
        EmailPayload emailPayload = EmailPayload.builder()
                .recipient(confirmationToken.getMyUser().getEmailAddress())
                .category("welcome")
                .subject("Welcome, " + confirmationToken.getMyUser().getFirstName() + " " + confirmationToken.getMyUser().getLastName())
                .username(confirmationToken.getMyUser().getUsername())
                .body("To confirm your account, please click here: "
                        + "http://localhost:8080/user/confirm-account?token=" + confirmationToken.getConfirmationToken()).build();
        emailService.sendWelcomeEmail(emailPayload);
        emailService.save(emailPayload);

    }
}
*/
