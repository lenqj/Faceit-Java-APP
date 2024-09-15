package Main.service;

import Main.model.EmailPayload;
import Main.repository.EmailPayloadRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@Service
@AllArgsConstructor
@Transactional
public class EmailService {
    private final EmailPayloadRepository emailPayloadRepository;
    private final JavaMailSender mailSender;

    public EmailPayload sendWelcomeEmail(EmailPayload emailPayload) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            message.setFrom(emailPayload.getRecipient());
            message.setRecipients(MimeMessage.RecipientType.TO, emailPayload.getRecipient());
            message.setSubject(emailPayload.getSubject());

            String htmlTemplate = Files.readString(Paths.get("welcomeTemplate.html"));
            htmlTemplate = htmlTemplate.replace("${name}", emailPayload.getUsername());
            String messageBody = emailPayload.getBody().replace("\n", "<br/>");
            htmlTemplate = htmlTemplate.replace("${message}", messageBody);

            message.setContent(htmlTemplate, "text/html; charset=utf-8");
            mailSender.send(message);
            return emailPayload;
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public EmailPayload sendSupportEmail(EmailPayload emailPayload) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            message.setFrom(emailPayload.getRecipient());
            message.setRecipients(MimeMessage.RecipientType.TO, emailPayload.getRecipient());
            message.setSubject(emailPayload.getSubject());

            String htmlTemplate = Files.readString(Paths.get("supportTemplate.html"));
            htmlTemplate = htmlTemplate.replace("${subject}", emailPayload.getSubject());
            htmlTemplate = htmlTemplate.replace("${name}", emailPayload.getUsername());
            htmlTemplate = htmlTemplate.replace("${category}", emailPayload.getCategory());

            String messageBody = emailPayload.getBody().replace("\n", "<br/>");
            htmlTemplate = htmlTemplate.replace("${message}", messageBody);

            message.setContent(htmlTemplate, "text/html; charset=utf-8");
            mailSender.send(message);
            save(emailPayload);
            return emailPayload;
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void save(EmailPayload emailPayload) {
        emailPayloadRepository.save(emailPayload);
    }
}

