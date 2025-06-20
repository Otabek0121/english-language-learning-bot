package com.example.appenglishlanguagelearning.service;

import com.example.appenglishlanguagelearning.BotSender;
import com.example.appenglishlanguagelearning.entity.User;
import com.example.appenglishlanguagelearning.enums.UserState;
import com.example.appenglishlanguagelearning.payload.UserSessionDTO;
import com.example.appenglishlanguagelearning.repository.UserRepository;
import com.example.appenglishlanguagelearning.repository.UserSessionRepository;
import com.example.appenglishlanguagelearning.utils.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.Objects;
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

        if(update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();
            UserSessionDTO session = sessionService.getSession(chatId);
            UserState state = session.getUserState();

            if (update.getMessage().hasText()) {
                String text = update.getMessage().getText();

                if (text.equalsIgnoreCase("/start")) {
                    checkUserAndSave(update, chatId);
                }

                switch (state){

                    case MAIN_MENU -> handleMainMenu(update, chatId);
                    case DICTIONARY_MENU -> handleDictionaryMenu(update, chatId);
//                    case WORD_LEARNING -> handleWordLearning(update, chatId);
//                    case WORD_LEARNING_TEST -> handleWordLearningTest(update, chatId);
//
//                    case ADD_WORD_WAIT_WORD -> handleAddWordWaitWord(update, chatId);
//                    case ADD_WORD_WAIT_TRANSLATION -> handleAddWordWaitTranslation(update, chatId);
//                    case ADD_WORD_WAIT_DESCRIPTION -> handleAddWordWaitDescription(update, chatId);
//
//                    case MY_DICTIONARY -> handleMyDictionary(update, chatId);
//                    case PUBLIC_DICTIONARY -> handlePublicDictionary(update, chatId);
//                    case PUBLIC_DICTIONARY_TEST -> handlePublicDictionaryTest(update, chatId);
//
//                    case SETTINGS_MENU -> handleSettingsMenu(update, chatId);
//                    case SETTINGS_CHANGE_LANGUAGE -> handleSettingsChangeLanguage(update, chatId);
//                    case SETTINGS_CHANGE_LEVEL -> handleSettingsChangeLevel(update, chatId);
//
//                    case STATISTICS_MENU -> handleStatisticsMenu(update, chatId);
//
//                    case HELP_MENU -> handleHelpMenu(update, chatId);
//
//                    case CONFIRM_EXIT -> handleConfirmExit(update, chatId);

                    case WAIT_COMMAND -> botSender.sendText(chatId, "Iltimos, buyruq kiriting 📩");

                }



            }
            else if (update.getMessage().hasContact()) {
                updateUserPhoneNumber(update, chatId);
                sessionService.updateSession(chatId, UserState.MAIN_MENU,false);

                String text="""
                \uD83D\uDCCC Asosiy bo‘lim.
                ⬇ Quyidagilardan birini tanlang:
                """;
                sendMenu(chatId,buttonCreatorService.mainMenuButtonCreate(),text);
            }
        }


    }

    private void handleDictionaryMenu(Update update, Long chatId) {



    }

    private void handleMainMenu(Update update, Long chatId) {

        if (update.hasMessage()) {
            UserSessionDTO session = sessionService.getSession(chatId);
            UserState state = session.getUserState();

            if (update.getMessage().hasText()) {
                String text = update.getMessage().getText();

                if (text.equalsIgnoreCase(MessageConstants.MAIN_MENU_DICTIONARY)) {
                    botSender.deleteButtons(chatId,"✨Lug'at bo'limiga xush kelibsiz!");
                    sessionService.updateSession(chatId, UserState.DICTIONARY_MENU,false);
                    sendMenu(chatId,buttonCreatorService.dictionaryButtonCreate(),"\uD83D\uDCCC Quyidagi bo'limlardan birini tanlang:");
                }
                else if (text.equalsIgnoreCase(MessageConstants.MAIN_MENU_STATISTICS)) {
                    botSender.deleteButtons(chatId,"Statistika bo'limiga xush kelibsiz!");

                }
                else if (text.equalsIgnoreCase(MessageConstants.MAIN_MENU_SETTINGS)) {
                    botSender.deleteButtons(chatId,"Sozlamalar bo'limiga xush kelibsiz!");


                } else if (text.equalsIgnoreCase(MessageConstants.MAIN_MENU_SUPPORT)) {
                    botSender.deleteButtons(chatId,"Yordam bo'limiga xush kelibsiz!");

                }

            }
        }

    }


    private void sendMenu(Long chatId,ReplyKeyboardMarkup replyKeyboardMarkup, String text) {
        botSender.sendButton(chatId,text, replyKeyboardMarkup);
    }

    private void updateUserPhoneNumber(Update update, Long chatId) {
        Contact contact = update.getMessage().getContact();
        User user = userRepository.findByChatId(chatId).orElse(null);

        if(Objects.isNull(user)){
            botSender.sendText(chatId,"Siz tizimda maavjud emassiz! \n /start buyrug'i orqali botni qayta ishga tushiring!");
        }
        else {
            user.setPhoneNumber(contact.getPhoneNumber());
            userRepository.save(user);
        }
        botSender.deleteContactButton(chatId);
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
        }

        ReplyKeyboardMarkup contactButton = buttonCreatorService.sendPhoneNumberButtonCreate();
        String text= """
                👋 Salom, hush kelibsiz!
                Bu bot orqali siz ingliz tilidagi so'zlarni o'rganishingiz, shaxsiy lug'atingizni yaratishingiz va statistikalaringizni kuzatishingiz mumkin.
                Ingliz tilini birga o'rganamiz😉
                """;
        botSender.sendButton(chatId, text ,contactButton);
    }

}
