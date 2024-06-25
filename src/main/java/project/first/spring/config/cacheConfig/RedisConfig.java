package project.first.spring.config.cacheConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "redis")
public record RedisConfig(String host, Integer port, Integer timeout, Integer maxRetry) {}
