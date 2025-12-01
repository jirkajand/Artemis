package cz.uhk.fim.notificationservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class EmailSenderConfig {

    private final EmailCredentialsConfig emailCredentialsConfig;

    @Bean
    public JavaMailSender getJavaMailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailCredentialsConfig.getHost());
        mailSender.setPort(emailCredentialsConfig.getPort());
        mailSender.setUsername(emailCredentialsConfig.getUsername());
        mailSender.setPassword(emailCredentialsConfig.getPassword());
        mailSender.setProtocol("smtp");
        mailSender.setDefaultEncoding("UTF-8");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        // starttls.required může být true nebo false podle serveru; pokud způsobuje problém, nastavte na false
        props.put("mail.smtp.starttls.required", "true");
        // Důvěřovat konkrétnímu hostu (pomůže s certifikáty)
        props.put("mail.smtp.ssl.trust", emailCredentialsConfig.getHost());
        // Zajistit moderní TLS verzi
        props.put("mail.smtp.ssl.protocols", "TLSv1.2 TLSv1.3");
        // volitelné timeouts pro lepší diagnostiku
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");
        props.put("mail.smtp.writetimeout", "10000");

        props.put("mail.debug", "true");

        return mailSender;
    }
}
