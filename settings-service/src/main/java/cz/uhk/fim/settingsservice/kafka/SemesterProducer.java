package cz.uhk.fim.settingsservice.kafka;

import cz.uhk.fim.settingsservice.kafka.model.SemesterMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SemesterProducer {

    private final StreamBridge streamBridge;

    //private final SemesterService semesterService;
/*
    @Async
    @TransactionalEventListener(value = PushSemesterToKafka.class, phase = TransactionPhase.AFTER_COMMIT)
    public void pushSemesterToKafkaByEvent(PushSemesterToKafka event) {
        log.info("Received event to push semester to Kafka for semester id: {}", event.semesterId());
        var semesterEntity = semesterService.getById(event.semesterId());
        if (semesterEntity.isEmpty()) {
            log.error("Semester with id {} not found, cannot push to Kafka", event.semesterId());
            return;
        }
        pushSemesterEntityToKafka(semesterEntity.get());
    }


 */
    public void pushSemesterEntityToKafka(SemesterMessage message) {

        log.info("Pushing semester message to Kafka: {}", message);

        streamBridge.send(KafkaBindings.SEMESTER_OUT,
                MessageBuilder.withPayload(message)
                        .setHeader(KafkaHeaders.KEY, message.getId().toString())
                        .build());
    }
}
