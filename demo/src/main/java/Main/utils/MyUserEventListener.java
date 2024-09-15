package Main.utils;

import Main.DTO.MyUserCreationDTO;
import Main.exceptions.ApiExceptionResponse;
import Main.mapper.MyUserMapper;
import Main.model.*;
import Main.repository.ConfirmationTokenRepository;
import Main.repository.EmailPayloadRepository;
import Main.repository.MyUserRepository;
import Main.service.EmailService;
import Main.service.MyUserService;
import Main.service.ScoreService;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;



@Component
public class MyUserEventListener {
    private final EmailService emailService;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final PasswordEncoder passwordEncoder;

    public MyUserEventListener(@Lazy EmailService emailService, @Lazy ConfirmationTokenRepository confirmationTokenRepository, PasswordEncoder passwordEncoder) {
        this.emailService = emailService;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    @PrePersist
    public void sendEmail(MyUser myUser) {
        myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        ConfirmationToken confirmationToken = new ConfirmationToken(myUser);
        confirmationTokenRepository.save(confirmationToken);
        String emailAddress = myUser.getEmailAddress();

        EmailPayload emailPayload = EmailPayload.builder()
                .recipient(emailAddress)
                .category("welcome")
                .subject("Welcome, " + myUser.getFirstName() + " " + myUser.getLastName())
                .username(myUser.getUsername())
                .body("To confirm your account, please click here: "
                        + "http://localhost/confirm-account/" + confirmationToken.getConfirmationToken()).build();
        emailService.sendWelcomeEmail(emailPayload);
        emailService.save(emailPayload);
    }

}
