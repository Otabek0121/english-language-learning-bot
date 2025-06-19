package com.example.appenglishlanguagelearning.service;

import com.example.appenglishlanguagelearning.BotSender;
import com.example.appenglishlanguagelearning.entity.User;
import com.example.appenglishlanguagelearning.entity.UserSession;
import com.example.appenglishlanguagelearning.enums.UserState;
import com.example.appenglishlanguagelearning.repository.UserRepository;
import com.example.appenglishlanguagelearning.repository.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateHandlerService {

    private final UserRepository userRepository;

    private final SessionService sessionService;

    private final UserSessionRepository userSessionRepository;

    private final ButtonCreatorService buttonCreatorService;

    private final BotSender botSender;


    public void handle(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()) {

            Long chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();

            if (text.equalsIgnoreCase("/start")) {
                checkUserAndSave(update, chatId);
            }
        }




    }

    private void checkUserAndSave(Update update, Long chatId) {
        Optional<User> byUser = userRepository.findByChatId(chatId);

        if(byUser.isEmpty()){
            User user = new User();
            user.setChatId(chatId);
            user.setUsername(update.getMessage().getChat().getUserName());
            user.setFirstName(update.getMessage().getChat().getFirstName());
            user.setLastName(update.getMessage().getChat().getLastName());
            userRepository.save(user);

            UserSession userSession = new UserSession();
            userSession.setChatId(chatId);
            userSession.setUserState(UserState.SEND_PHONE_NUMBER);

            userSessionRepository.save(userSession);

            // TODO send contact button

            ReplyKeyboardMarkup contactButton = buttonCreatorService.sendPhoneNumberButtonCreate();
            botSender.sendButton(chatId, contactButton);

        }
    }

}
