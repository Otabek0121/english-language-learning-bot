package com.example.appenglishlanguagelearning.config;

import com.example.appenglishlanguagelearning.payload.AddWordSessionDTO;
import com.example.appenglishlanguagelearning.payload.UserSessionDTO;
import com.example.appenglishlanguagelearning.payload.WordSessionDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean(name="userSession")
    public RedisTemplate<String, UserSessionDTO> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, UserSessionDTO> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(UserSessionDTO.class));
        return template;
    }

    @Bean(name="addWordSession")
    public RedisTemplate<String, AddWordSessionDTO> addWordSessionRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, AddWordSessionDTO> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(AddWordSessionDTO.class));
        return template;
    }

    @Bean(name="wordSession")
    public RedisTemplate<String, WordSessionDTO> wordSessionRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, WordSessionDTO> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(WordSessionDTO.class));
        return template;
    }

//    @Bean
//    public RedisMessageListenerContainer keyExpirationListener(RedisConnectionFactory factory, SessionExpirationListener listener) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(factory);
//        container.addMessageListener(listener, new PatternTopic("__keyevent@0__:expired"));
//        return container;
//    }

}
