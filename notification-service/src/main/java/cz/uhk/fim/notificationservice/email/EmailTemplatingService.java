package cz.uhk.fim.notificationservice.email;

import ch.qos.logback.core.util.StringUtil;
import cz.uhk.fim.notificationservice.kafka.model.NotificationSendEmailMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailTemplatingService {

    private static final String DEFAULT_LOCALIZATION = "en";
    private static final String BASE_PATH = "email";
    private final EmailLocaleService emailLocaleService;

    private String setContentToMainTemplate(String content, String languageCode) throws IOException {
        ClassPathResource resource = new ClassPathResource(validEmailPath("email_template"));
        String template;
        try (InputStream inputStream = resource.getInputStream()) {
            template = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
        if (!template.isEmpty()) {
            template = template.replace("{CONTENT}", content);
            // replace {LOCALE_<KEY>}
            Pattern p = Pattern.compile("\\{LOCALE_([A-Z0-9_]+)\\}");
            Matcher m = p.matcher(template);
            StringBuffer sb = new StringBuffer();
            while (m.find()) {
                String key = m.group(1);
                String localized = emailLocaleService.getLocalized(languageCode, key);
                if (localized == null) localized = "";
                m.appendReplacement(sb, Matcher.quoteReplacement(localized));
            }
            m.appendTail(sb);
            template = sb.toString();
        }
        return template;
    }

    private String getBody(NotificationSendEmailMessage.NotificationType emailType, String languageCode, Map<String, String> attributes) {
        String mailContent = null;
        try {
            ClassPathResource resource = new ClassPathResource(getLocalizedEmailPathByType(emailType, languageCode));
            String body;
            try (InputStream inputStream = resource.getInputStream()) {
                body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            }
            if (!body.isEmpty() && !attributes.isEmpty()) {
                for (Map.Entry<String, String> entry : attributes.entrySet()) {
                    body = body.replace(entry.getKey(), entry.getValue());
                }
            }
            mailContent = setContentToMainTemplate(body, languageCode);
        } catch (FileNotFoundException e) {
            log.error("FileNotFoundException: ", e);
        } catch (IOException e) {
            log.error("IOException: ", e);
        }
        return mailContent;
    }

    /**
     * Generates email body with template and attributes.
     *
     * @param notificationType Type of notification email.
     *                         Supported types: ASSIGNED_BY_LOCAL_STUDENT, INTERNATIONAL_STUDENT_ANONYMIZED
     * @param languageCode     Language code for localization (e.g., "en", "cs").
     * @param attributes       FULLNAME | ASSIGNER_FULLNAME | ASSIGNED_FULLNAME
     * @return Generated email body as a String.
     */
    public String getBodyWithTemplate(NotificationSendEmailMessage.NotificationType notificationType, String languageCode, Map<String, String> attributes) {
        return getBody(notificationType, languageCode, attributes);
    }

    private String getLocalizedEmailPathByType(NotificationSendEmailMessage.NotificationType emailType, String languageCode) {

        String fileName = switch (emailType) {
            case NotificationSendEmailMessage.NotificationType.ASSIGNED_BY_LOCAL_STUDENT -> "assigned_by_local_student";
            case NotificationSendEmailMessage.NotificationType.INTERNATIONAL_STUDENT_ANONYMIZED ->
                    "international_student_anonymized";
            default -> throw new IllegalArgumentException("Unsupported email type: " + emailType);
        };

        return validEmailPath(fileName);
    }

    private String validEmailPath(String templateName) {
        return String.format("%s/%s.html", BASE_PATH, templateName);
    }

    private String validLanguageCode(String languageCode) {
        if (StringUtil.isNullOrEmpty(languageCode)) {
            return DEFAULT_LOCALIZATION;
        }
        return languageCode.toLowerCase();
    }
}
