package project.first.spring.processData.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rules")
public record RuleExecutorConfig(Integer threadPoolSize) {
}
