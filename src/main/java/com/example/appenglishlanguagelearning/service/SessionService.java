package com.example.appenglishlanguagelearning.service;

import com.example.appenglishlanguagelearning.entity.UserSession;
import com.example.appenglishlanguagelearning.enums.UserState;
import com.example.appenglishlanguagelearning.payload.UserSessionDTO;
import com.example.appenglishlanguagelearning.repository.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class SessionService {

    @Qualifier(value = "userSession")
    private final RedisTemplate<String, UserSessionDTO> redisTemplate;

    private final UserSessionRepository sessionRepository;

    private static final long TTL_MINUTES = 10  ;

    public UserSessionDTO getSession(Long chatId) {
        String key = chatId.toString();
        ValueOperations<String, UserSessionDTO> ops = redisTemplate.opsForValue();

        UserSessionDTO session = ops.get(key);
        if (session != null) return session;

        UserSession entity = sessionRepository.findByChatId(chatId).orElse(null);
        if (entity != null) {
            session = new UserSessionDTO(entity.getChatId(), entity.getUserState(),null);
        } else {

            UserSession sessionEntity=new UserSession(chatId,UserState.SEND_PHONE_NUMBER);
            sessionRepository.save(sessionEntity);

            session = new UserSessionDTO(chatId, UserState.SEND_PHONE_NUMBER,null);
        }

        ops.set(key, session, TTL_MINUTES, TimeUnit.MINUTES);
        return session;
    }

    public void updateSession(Long chatId, UserState state) {
        String key = chatId.toString();
        UserSessionDTO session = new UserSessionDTO(chatId, state,null);

        redisTemplate.opsForValue().set(key, session, TTL_MINUTES, TimeUnit.MINUTES);

        saveOrUpdateSessionInDb(chatId, state);

    }

    public void updateSession(Long chatId, UserState state,String learningWord) {
        String key = chatId.toString();
        UserSessionDTO session = new UserSessionDTO(chatId, state, learningWord);

        redisTemplate.opsForValue().set(key, session, TTL_MINUTES, TimeUnit.MINUTES);

        saveOrUpdateSessionInDb(chatId, state);

    }

    private void saveOrUpdateSessionInDb(Long chatId, UserState state) {
        UserSession entity = sessionRepository.findByChatId(chatId)
                .orElse(new UserSession(chatId, state));
        entity.setUserState(state);
        sessionRepository.save(entity);
    }
}
