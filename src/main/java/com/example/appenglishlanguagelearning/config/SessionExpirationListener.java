//package com.example.appenglishlanguagelearning.config;
//
//import com.example.appenglishlanguagelearning.service.SessionService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.connection.MessageListener;
//import org.springframework.stereotype.Component;
//
//import java.nio.charset.StandardCharsets;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class SessionExpirationListener implements MessageListener {
//
//    private final SessionService sessionService;
//
//    @Override
//    public void onMessage(Message message, byte[] pattern) {
//        String expiredKey = new String(message.getBody(), StandardCharsets.UTF_8);
//        try {
//            Long chatId = Long.valueOf(expiredKey);
//            sessionService.saveSessionFromRedis(chatId);
//            log.info("SESSION CHAT ID {} EXPIRED KEY {}", chatId, expiredKey);
//        } catch (NumberFormatException ignored) {
//
//        }
//    }
//}
