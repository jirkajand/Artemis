package cz.uhk.fim.kafka;

import cz.uhk.fim.entity.InternationalStudentEntity;
import cz.uhk.fim.entity.LocalStudentEntity;
import cz.uhk.fim.usermanagement.kafka.model.NotificationSendEmailMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendEmailKafkaProducer {

    private final StreamBridge streamBridge;

    //This could be better with async events to invoke this method, but there was a troubles with transaction management so for now it is like this.
    public void sendEmailMessage(NotificationSendEmailMessage.NotificationType type, LocalStudentEntity localStudentEntity, InternationalStudentEntity internationalStudent) {
        log.info("Sending email message to Kafka");

        var message = new NotificationSendEmailMessage();
        message.setType(type);
        message.setRecipient(internationalStudent.getEmail());
        message.setRecipientFullName(internationalStudent.getFirstName() + " " + internationalStudent.getLastName());
        message.setCountryCode(internationalStudent.getCountryISO());
        message.setAssignerFullName(localStudentEntity.getFirstName() + " " + localStudentEntity.getLastName());

        streamBridge.send(KafkaBindings.NOTIFICATIONS_SEND_EMAIL,
                MessageBuilder.withPayload(message)
                        .setHeader(KafkaHeaders.KEY, UUID.randomUUID().toString())
                        .build());
    }
}
