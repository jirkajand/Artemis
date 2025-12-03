package cz.uhk.fim.notificationservice.email;

import cz.uhk.fim.notificationservice.kafka.model.NotificationSendEmailMessage;
import cz.uhk.fim.notificationservice.properties.CountryLanguageProperties;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final EmailTemplatingService emailTemplatingService;
    private final EmailLocaleService emailLocaleService;

    private final CountryLanguageProperties countryLanguageProperties;

    @Value("${spring.mail.username}")
    private String sender;

    public void processMessage(NotificationSendEmailMessage message) {
        log.info("Processing email message: {}", message);

        HashMap<String, String> variables = new HashMap<>();
        variables.put("{RECIPIENT_FULLNAME}", message.getRecipientFullName());
        if (message.getAssignerFullName() != null) {
            variables.put("{ASSIGNER_FULLNAME}", message.getAssignerFullName());
        }

        var languageCode = message.getCountryCode() != null
                ? countryLanguageProperties.getLanguages().getOrDefault(message.getCountryCode(), countryLanguageProperties.getDefaultLanguage())
                : countryLanguageProperties.getDefaultLanguage();

        var body = emailTemplatingService.getBodyWithTemplate(message.getType(), languageCode, variables);

        MimeMessage mailMessage = createMimeMessage(body, message, languageCode);
        if (mailMessage != null) {
            var emailSent = sendEmail(mailMessage, message);
            if (!emailSent) {
                //Possible to add caching of failed emails here
                log.error("Email sending failed for {}", message.getRecipient());
            }
        } else {
            log.error("Failed to create MimeMessage for {}", message.getRecipient());
        }

    }

    private boolean sendEmail(MimeMessage mailMessage, NotificationSendEmailMessage message) {
        try {
            javaMailSender.send(mailMessage);
            log.info("Email sent to {}", message.getRecipient());
            return true;
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", message.getRecipient(), e.getMessage());
            return false;
        }
    }

    private MimeMessage createMimeMessage(String body, NotificationSendEmailMessage message, String languageCode) {
        MimeMessage mailMessage = null;
        try {
            mailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, false, "UTF-8");
            helper.setFrom(sender);
            helper.setTo(message.getRecipient());
            helper.setSubject(emailLocaleService.getLocalizedSubject(message.getType(), languageCode));
            mailMessage.setContent(body, "text/html; charset=UTF-8");
        } catch (MessagingException e) {
            log.error("MessagingException: ", e);
        }
        return mailMessage;
    }
}
