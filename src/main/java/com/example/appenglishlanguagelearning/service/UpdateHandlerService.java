package com.example.appenglishlanguagelearning.service;

import com.example.appenglishlanguagelearning.entity.User;
import com.example.appenglishlanguagelearning.payload.UserSessionDTO;
import com.example.appenglishlanguagelearning.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateHandlerService {

    private final UserRepository userRepository;

    private final SessionService sessionService;


    public void handle(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()) {

            Long chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();

            if (text.equalsIgnoreCase("/start")) {

                Optional<User> byUser = userRepository.findByChatId(chatId);


            }

        }




    }

}
