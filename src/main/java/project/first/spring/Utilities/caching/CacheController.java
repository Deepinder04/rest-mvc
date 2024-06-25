package project.first.spring.Utilities.caching;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sb/cache")
@RequiredArgsConstructor
public class CacheController {

    private final CachingRepository cachingRepository;

    @GetMapping("/keys")
    ResponseEntity<List<String>> getAllKeys(){
        return ResponseEntity.ok(cachingRepository.getKeys());
    }

    @GetMapping("/keys/{key}")
    ResponseEntity<Object> getValueOfKey(@PathVariable("keys") String key){
        return ResponseEntity.ok(cachingRepository.getValueOfKey(key));
    }

    @DeleteMapping("/keys")
    ResponseEntity<Boolean> clearAllCache(){
        cachingRepository.clearCache();
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/keys/{key}")
    ResponseEntity<Boolean> clearCacheForKey(@PathVariable("key") String key){
        cachingRepository.clearCacheForKey(key);
        return ResponseEntity.ok(true);
    }


    // use to verify patter mapping
    @PostMapping("/test")
    void addDataToCache(@RequestParam("key") String key, @RequestBody Object value){
        cachingRepository.addKeyValuePairInCache(key, value);
    }

    @PostMapping("/keys/{key}")
    ResponseEntity<Boolean> updateCacheValue(@PathVariable("key") String key, @RequestBody Object value){
        return ResponseEntity.ok(cachingRepository.setValue(key, value));
    }

}
