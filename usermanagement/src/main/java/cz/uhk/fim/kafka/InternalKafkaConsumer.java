package cz.uhk.fim.kafka;

import cz.uhk.fim.service.SemesterService;
import cz.uhk.fim.usermanagement.kafka.model.SemesterMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
@RequiredArgsConstructor
public class InternalKafkaConsumer {

    private final SemesterService semesterService;

    @Bean
    public Consumer<Message<SemesterMessage>> settingsSemester() {
        return this::processSettingsSemestersMessage;
    }

    private void processSettingsSemestersMessage(Message<SemesterMessage> semesterMessageMessage) {
        var semesterMessage = semesterMessageMessage.getPayload();
        log.info("Received semester message: {}", semesterMessage);
        log.info("Received semester message with key: {}", semesterMessageMessage.getHeaders().get(KafkaHeaders.RECEIVED_KEY, String.class));
        semesterService.consumeSemesterMessage(semesterMessage);
    }
}
