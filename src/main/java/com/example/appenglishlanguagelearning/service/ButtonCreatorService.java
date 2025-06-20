package com.example.appenglishlanguagelearning.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ButtonCreatorService {

    public ReplyKeyboardMarkup sendPhoneNumberButtonCreate() {

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

    public InlineKeyboardMarkup unitButtonCreate() {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<InlineKeyboardButton> row = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText("Unit " + i);
            button.setCallbackData("unit_" + i);

            row.add(button);

            if (row.size() == 5) {
                keyboard.add(row);
                row = new ArrayList<>();
            }
        }

        if (!row.isEmpty()) {
            keyboard.add(row);
        }


        inlineKeyboardMarkup.setKeyboard(keyboard);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup knowAndDontKnowButtonCreate(){
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();


        buttons.add(Arrays.asList(
                InlineKeyboardButton.builder().text("✅").callbackData("KNOW").build(),
                InlineKeyboardButton.builder().text("❌").callbackData("DONT_KNOW").build()
        ));

        keyboard.setKeyboard(buttons);
        return keyboard;
    }


    public InlineKeyboardMarkup anotherUnitButtonCreate(){
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();


        buttons.add(Arrays.asList(
                InlineKeyboardButton.builder().text("✅").callbackData("HA").build()
        ));

        keyboard.setKeyboard(buttons);
        return keyboard;
    }


}
