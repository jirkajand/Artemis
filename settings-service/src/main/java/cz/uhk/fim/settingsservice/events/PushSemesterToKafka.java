package cz.uhk.fim.settingsservice.events;

import java.util.UUID;

public record PushSemesterToKafka(UUID semesterId) {
}
