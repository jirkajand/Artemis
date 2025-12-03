package cz.uhk.fim.notificationservice.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "country-language")
public class CountryLanguageProperties {

    private String defaultLanguage;
    private Map<String, String> languages;
}
