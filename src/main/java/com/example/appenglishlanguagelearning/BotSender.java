package com.example.appenglishlanguagelearning;

import com.example.appenglishlanguagelearning.service.UpdateHandlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;


@Slf4j
@Configuration

public class BotSender extends TelegramLongPollingBot {


    private  UpdateHandlerService updateHandlerService;

    public BotSender(@Lazy UpdateHandlerService updateHandlerService) {
        this.updateHandlerService = updateHandlerService;
    }

    @Override
    public void onUpdateReceived(Update update) {
       updateHandlerService.handle(update);
    }


    public void deleteContactButton(Long chatId) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Telefon raqamingizni ulashganingiz uchun rahmat!");
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteButtons(Long chatId, String text) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
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

    public void sendButton(Long chatId, String text ,ReplyKeyboardMarkup replyKeyboardMarkup) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(replyKeyboardMarkup)
                .build();

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void sendAudio(long chatId, String filePath) {
        try {
            InputFile audioFile = new InputFile(new File(filePath));
            SendAudio sendAudio = new SendAudio();
            sendAudio.setChatId(String.valueOf(chatId));
            sendAudio.setAudio(audioFile);

            execute(sendAudio);
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
