package com.example.appenglishlanguagelearning.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Service
public class ButtonCreatorService {

    private ReplyKeyboardMarkup sendPhoneNumberButtonCreate() {

        KeyboardButton phoneNumber = new KeyboardButton("📞Telefon nomerni jo'natish");
        phoneNumber.setRequestContact(true);

        KeyboardRow buttons = new KeyboardRow();
        buttons.add(phoneNumber);

        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(true);
        keyboard.setResizeKeyboard(true);
        keyboard.setKeyboard(List.of(buttons));


        return keyboard;
    }

}
