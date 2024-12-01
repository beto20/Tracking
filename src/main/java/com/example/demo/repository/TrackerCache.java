package com.example.demo.repository;

import com.example.demo.model.dto.TrackerDto;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class TrackerCache {
    private final ReactiveRedisTemplate<String, Object> redisTemplate;

    public TrackerCache(ReactiveRedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Mono<Boolean> saveLocation(float latitude, float longitude, TrackerDto dto) {
        var key = String.valueOf(latitude).concat("-").concat(String.valueOf(longitude));
        return redisTemplate.opsForValue().set(key, dto);
    }

    public Mono<TrackerDto> getLocation(float latitude, float longitude) {
        var key = String.valueOf(latitude).concat("-").concat(String.valueOf(longitude));
        return redisTemplate.opsForValue().get(key).cast(TrackerDto.class);
    }
}
