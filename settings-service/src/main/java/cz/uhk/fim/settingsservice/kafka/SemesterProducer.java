package cz.uhk.fim.settingsservice.kafka;

import cz.uhk.fim.settingsservice.events.PushSemesterToKafka;
import cz.uhk.fim.settingsservice.service.SemesterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class SemesterProducer {

    private final StreamBridge streamBridge;

    private final SemesterService semesterService;

    @Async
    @TransactionalEventListener(value = PushSemesterToKafka.class, phase = TransactionPhase.AFTER_COMMIT)
    public void pushSemesterToKafka(PushSemesterToKafka event) {
        log.info("Received event to push semester to Kafka for semester id: {}", event.semesterId());
        var semesterEntity = semesterService.getById(event.semesterId());
        if (semesterEntity.isEmpty()) {
            log.error("Semester with id {} not found, cannot push to Kafka", event.semesterId());
            return;
        }
        var message = semesterService.getSemesterMessage(semesterEntity.get());

        streamBridge.send(KafkaBindings.SEMESTER_OUT,
                MessageBuilder.withPayload(message)
                        .setHeader(KafkaHeaders.KEY, message.getId().toString())
                        .build());
    }
}
