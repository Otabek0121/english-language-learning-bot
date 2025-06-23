package com.example.appenglishlanguagelearning.service;

import com.example.appenglishlanguagelearning.utils.ButtonMessage;
import com.example.appenglishlanguagelearning.utils.MessageConstants;
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

        KeyboardButton phoneNumber = new KeyboardButton(ButtonMessage.SEND_PHONE_NUMBER);
        phoneNumber.setRequestContact(true);

        KeyboardRow buttons = new KeyboardRow();
        buttons.add(phoneNumber);

        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(true);
        keyboard.setResizeKeyboard(true);
        keyboard.setKeyboard(List.of(buttons));


        return keyboard;
    }

    public ReplyKeyboardMarkup mainMenuButtonCreate() {

        KeyboardButton dictionary = new KeyboardButton(ButtonMessage.MAIN_MENU_DICTIONARY);
        KeyboardButton statistics = new KeyboardButton(ButtonMessage.MAIN_MENU_STATISTICS);
        KeyboardButton settings = new KeyboardButton(ButtonMessage.MAIN_MENU_SETTINGS);
        KeyboardButton support = new KeyboardButton(ButtonMessage.MAIN_MENU_SUPPORT);
        KeyboardButton aboutMe = new KeyboardButton(ButtonMessage.MAIN_MENU_ABOUT_ME);

        KeyboardRow row1 = new KeyboardRow();
        row1.add(dictionary);
        row1.add(statistics);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(settings);
        row2.add(support);

        KeyboardRow row3 = new KeyboardRow();
        row3.add(aboutMe);

        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(true);
        keyboard.setResizeKeyboard(true);
        keyboard.setKeyboard(List.of(row1, row2,row3));

        return keyboard;
    }

    public ReplyKeyboardMarkup dictionaryButtonCreate() {

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rows = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(ButtonMessage.DICTIONARY_LEARNING_MY_WORDS);
        row1.add(ButtonMessage.DICTIONARY_LEARNING_PUBLIC_WORDS);


        KeyboardRow row2 = new KeyboardRow();
        row2.add(ButtonMessage.DICTIONARY_MY_WORDS);
        row2.add(ButtonMessage.DICTIONARY_PUBLIC_WORDS);

        KeyboardRow row3 = new KeyboardRow();
        row3.add(ButtonMessage.DICTIONARY_ADD_WORD);
        row3.add(ButtonMessage.DICTIONARY_SEARCH_WORD);

        KeyboardRow row4 = new KeyboardRow();
        row4.add(ButtonMessage.MENU_BACK);

        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        rows.add(row4);

        markup.setKeyboard(rows);
        markup.setResizeKeyboard(true);
        return markup;
    }


    public ReplyKeyboardMarkup backMenuButtonCreate() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(ButtonMessage.MENU_BACK);
        rows.add(row1);
        markup.setKeyboard(rows);
        markup.setResizeKeyboard(true);
        return markup;
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
