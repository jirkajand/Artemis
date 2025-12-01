package cz.uhk.fim.notificationservice.email;

import cz.uhk.fim.notificationservice.kafka.model.NotificationSendEmailMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class EmailLocaleService {

    private final Map<String, Map<String, String>> locales = new HashMap<>();

    @PostConstruct
    public void init() {
        try (InputStream is = new ClassPathResource("email/locales.yml").getInputStream()) {
            Yaml yaml = new Yaml();
            Object loaded = yaml.load(is);
            if (loaded instanceof Map<?, ?> raw) {
                for (var e : raw.entrySet()) {
                    String lang = e.getKey().toString().toLowerCase();
                    Map<String, String> map = new HashMap<>();
                    if (e.getValue() instanceof Map<?, ?> sub) {
                        for (var se : sub.entrySet()) {
                            map.put(se.getKey().toString().toUpperCase(), se.getValue().toString());
                        }
                    }
                    locales.put(lang, map);
                }
            }
        } catch (Exception ex) {
            log.error("Failed to load email locales", ex);
        }
    }

    public String getLocalized(String languageCode, String key) {
        if (languageCode == null) languageCode = "en";
        Map<String, String> map = locales.getOrDefault(languageCode.toLowerCase(), locales.get("en"));
        if (map == null) return null;
        return map.get(key.toUpperCase());
    }

    public String getLocalizedSubject(NotificationSendEmailMessage.NotificationType type, String languageCode) {
        return locales.getOrDefault(languageCode.toLowerCase(), locales.get("en")).get("SUBJECT_" + type.name());
    }
}
