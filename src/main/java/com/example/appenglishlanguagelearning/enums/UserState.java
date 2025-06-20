package com.example.appenglishlanguagelearning.enums;

public enum UserState {
    START,
    SEND_PHONE_NUMBER,

    MAIN_MENU,              // Asosiy menyuda

    WORD_LEARNING,          // So‘z o‘rganish bo‘limida
    WORD_LEARNING_TEST,     // Bilaman / Bilmayman tugmasi bosilishini kutish

    ADD_WORD_WAIT_WORD,     // So‘z kirityapti
    ADD_WORD_WAIT_TRANSLATION, // Tarjimasini kiritmoqda
    ADD_WORD_WAIT_DESCRIPTION, // Izoh kiritmoqda yoki o‘tkazib yuboradi

    MY_DICTIONARY,          // O‘z lug‘ati menyusi
    PUBLIC_DICTIONARY,      // Umumiy lug‘at menyusi
    PUBLIC_DICTIONARY_TEST, // Umumiy lug‘atda random/test jarayoni

    SETTINGS_MENU,          // Sozlamalar menyusi
    SETTINGS_CHANGE_LANGUAGE,  // Til o‘zgartirish jarayoni
    SETTINGS_CHANGE_LEVEL,     // Daraja o‘zgartirish jarayoni

    STATISTICS_MENU,        // Statistika menyusi

    HELP_MENU,              // Yordam menyusi

    CONFIRM_EXIT,           // Orqaga yoki menyudan chiqish tasdiqlash

    WAIT_COMMAND            // Buyruq yoki menyu tugmasini kutish

}
