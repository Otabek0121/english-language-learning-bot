package com.example.appenglishlanguagelearning.service;

import com.example.appenglishlanguagelearning.payload.AddWordSessionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AddWordSessionService {

    private final RedisTemplate<String, AddWordSessionDTO> redisTemplate;
    private static final long TTL_MINUTES = 10;

    public AddWordSessionDTO getSession(Long chatId) {
        String key = getKey(chatId);
        return redisTemplate.opsForValue().get(key);
    }

    public void saveSession(AddWordSessionDTO dto) {
        String key = getKey(dto.getChatId());
        redisTemplate.opsForValue().set(key, dto, TTL_MINUTES, TimeUnit.MINUTES);
    }

    public void clearSession(Long chatId) {
        redisTemplate.delete(getKey(chatId));
    }

    private String getKey(Long chatId) {
        return "add_word:" + chatId;
    }

}
