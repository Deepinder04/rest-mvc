package project.first.spring.Utilities.caching;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
public class CachingRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ValueOperations valueOperations;


    public CachingRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        valueOperations = redisTemplate.opsForValue();
    }

    List<String> getKeys(){
        List<String> keys = new ArrayList<>(Objects.requireNonNull(redisTemplate.keys("*")));
        log.info("size of keys in cache: {}", keys.size());
        return keys;
    }

    Object getValueOfKey(String key){
        Object value = valueOperations.get(key);
        log.info("For key - {} value fetched from cache is - {}", key, value);
        return value;
    }

    boolean setValue(String key, Object value){
        if(Boolean.TRUE.equals(redisTemplate.hasKey(key))){
            valueOperations.set(key, value);
            return true;
        } else return false;
    }

    void clearCache(){
        List<String> keys = new ArrayList<>(Objects.requireNonNull(redisTemplate.keys("*")));
        log.info("going to clear all cache");
        redisTemplate.delete(keys);
    }

    void clearCacheForKey(String key){
        boolean keyExists = redisTemplate.hasKey(key);
        if (keyExists)
            redisTemplate.delete(key);
        else log.info("key - {} doesn't exist in cache", key);
    }


    void clearCacheMatchingAPattern(String pattern){
        List<String> keys = new ArrayList<>(Objects.requireNonNull(redisTemplate.keys(pattern + "*")));
        log.info("going to clear all cache matching the pattern - {}", pattern);
        redisTemplate.delete(keys);
    }

    void addKeyValuePairInCache(String key, Object value){
        valueOperations.set(key, value);
    }
}
