package project.first.spring.config.cacheConfig;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


import java.net.SocketOptions;
import java.time.Duration;

@Configuration
@ComponentScan(value = {"project.first.spring.config.cacheConfig"})
public class RedisLettuceConfig {

    private final RedisConfig redisConfig;

    public RedisLettuceConfig(final RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
    }

    @Bean
    public LettuceConnectionFactory redisClusterConnectionFactory() {
        RedisStandaloneConfiguration redisClusterConfiguration = new RedisStandaloneConfiguration();
        redisClusterConfiguration.setHostName(redisConfig.host());
        redisClusterConfiguration.setPort(redisConfig.port());


        ClusterClientOptions clusterClientOptions = ClusterClientOptions.builder()
                .autoReconnect(true)
                .socketOptions(SocketOptions.builder().connectTimeout(Duration.ofMillis(redisConfig.timeout())).tcpNoDelay(true)
                        .keepAlive(true)
                        .build())
                .maxRedirects(redisConfig.maxRetry()).build();


        LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
                .readFrom(ReadFrom.ANY)
                .commandTimeout(Duration.ofMillis(redisConfig.timeout()))
                .clientOptions(clusterClientOptions)
                .useSsl()
                .build();

        return new LettuceConnectionFactory(redisClusterConfiguration);
    }

    @Bean(value = "redisTemplate")
    public RedisTemplate<String, Object> redisClusterTemplate(@Qualifier("redisClusterConnectionFactory") LettuceConnectionFactory redisConnectionFactory) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Primary
    @Bean(name = "cacheManager23hr")
    public CacheManager cacheManager23hr(LettuceConnectionFactory lettuceConnectionFactory) {
        Duration expiration = Duration.ofHours(23);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(expiration)
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));
        redisCacheConfiguration.usePrefix();

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(lettuceConnectionFactory)
                .cacheDefaults(redisCacheConfiguration).build();
    }
}
