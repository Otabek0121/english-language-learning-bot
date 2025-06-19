package com.example.appenglishlanguagelearning;

import com.example.appenglishlanguagelearning.service.UpdateHandlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Slf4j
@Configuration
public class BotSender extends TelegramLongPollingBot {


    @Autowired
    private  UpdateHandlerService updateHandlerService;


    @Override
    public void onUpdateReceived(Update update) {
       updateHandlerService.handle(update);
    }


    public void sendText(Long chatId, String text) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getBotUsername() {
        return "lang_mentor_bot";
    }

    @Override
    public String getBotToken() {
        return "8134268808:AAGiy8rCRDQGa4G_5a8Si9Omc_ZOTxJsBaI";
    }
}
