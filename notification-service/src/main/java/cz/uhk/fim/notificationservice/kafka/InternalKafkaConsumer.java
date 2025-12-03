package cz.uhk.fim.notificationservice.kafka;


import cz.uhk.fim.notificationservice.email.EmailService;
import cz.uhk.fim.notificationservice.kafka.model.NotificationSendEmailMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
@RequiredArgsConstructor
public class InternalKafkaConsumer {

    private final EmailService emailService;

    @Bean
    public Consumer<Message<NotificationSendEmailMessage>> notificationSendEmail() {
        return this::processNotificationSendEmailMessage;
    }

    private void processNotificationSendEmailMessage(Message<NotificationSendEmailMessage> message) {
        log.info("Received notification message: {}", message);

        emailService.processMessage(message.getPayload());
    }
}
