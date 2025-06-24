package com.example.appenglishlanguagelearning.service;

import com.example.appenglishlanguagelearning.payload.WordSessionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class WordSessionService {

    private final RedisTemplate<String, WordSessionDTO> redisTemplate;
    private static final long TTL_MINUTES = 10;

    public void saveWordSession(WordSessionDTO dto) {
        String key = getKey(dto.getChatId());
        redisTemplate.opsForValue().set(key, dto, TTL_MINUTES, TimeUnit.MINUTES);
    }

    public WordSessionDTO getWordSession(Long chatId) {
        return redisTemplate.opsForValue().get(getKey(chatId));
    }

    public void clearWordSession(Long chatId) {
        redisTemplate.delete(getKey(chatId));
    }

    private String getKey(Long chatId) {
        return "word_session:" + chatId;
    }

}
