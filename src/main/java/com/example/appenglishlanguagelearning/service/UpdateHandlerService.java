package com.example.appenglishlanguagelearning.service;

import com.example.appenglishlanguagelearning.BotSender;
import com.example.appenglishlanguagelearning.entity.Dictionary;
import com.example.appenglishlanguagelearning.entity.User;
import com.example.appenglishlanguagelearning.enums.UserState;
import com.example.appenglishlanguagelearning.payload.AddWordSessionDTO;
import com.example.appenglishlanguagelearning.payload.UserSessionDTO;
import com.example.appenglishlanguagelearning.repository.DictionaryRepository;
import com.example.appenglishlanguagelearning.repository.UserRepository;
import com.example.appenglishlanguagelearning.repository.UserSessionRepository;
import com.example.appenglishlanguagelearning.utils.ButtonMessage;
import com.example.appenglishlanguagelearning.utils.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateHandlerService {

    private final UserRepository userRepository;

    private final SessionService sessionService;

    private final AddWordSessionService addWordSessionService;

    private final ButtonCreatorService buttonCreatorService;

    private final DictionaryRepository dictionaryRepository;

    private final BotSender botSender;
    private final WordSessionService wordSessionService;


    public void handle(Update update) {

        if (update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();
            UserSessionDTO session = sessionService.getSession(chatId);
            UserState state = session.getUserState();

            if (update.getMessage().hasText()) {
                String text = update.getMessage().getText();

                if (text.equalsIgnoreCase("/start")) {
                    checkUserAndSave(update, chatId);
                }

                switch (state) {
                    case MAIN_MENU -> handleMainMenu(update, chatId);
                    case DICTIONARY_MENU -> handleDictionaryMenu(update, chatId);

                    case ADD_WORD_WAIT_WORD -> handleAddWord(update, chatId);
                    case ADD_WORD_WAIT_TRANSLATION -> handleAddTranslation(update, chatId);
//                    case ADD_WORD_WAIT_DESCRIPTION -> handleAddDescription(update, chatId); TODO NOTE: voz kechilishi mumkin
//
//                    case MY_DICTIONARY_LIST -> handleMyDictionary(update, chatId);    TODO NOTE: voz kechilishi mumkin
//                    case PUBLIC_DICTIONARY_LIST -> handlePublicDictionary(update, chatId);
//
                    case MY_DICTIONARY_TEST -> handleMyDictionaryTest(update, chatId);
//                    case PUBLIC_DICTIONARY_TEST -> handlePublicDictionaryTest(update, chatId);
//
//                    case SETTINGS_MENU -> handleSettings(update, chatId);
//                    case SETTINGS_CHANGE_LANGUAGE -> handleChangeLanguage(update, chatId);
//                    case SETTINGS_CHANGE_LEVEL -> handleChangeLevel(update, chatId);
//
//                    case STATISTICS_MENU -> handleStatistics(update, chatId);
//                    case HELP_MENU -> handleHelp(update, chatId);
//
//                    case CONFIRM_EXIT -> handleConfirmExit(update, chatId);
//                    case WAIT_COMMAND -> handleWaitCommand(update, chatId);
//
//                    default -> handleDefault(update, chatId);
                    case WAIT_COMMAND -> botSender.sendText(chatId, "Iltimos, buyruq kiriting 📩");

                }


            } else if (update.getMessage().hasContact()) {
                updateUserPhoneNumber(update, chatId);
                sessionService.updateSession(chatId, UserState.MAIN_MENU);

                sendMenu(chatId, buttonCreatorService.mainMenuButtonCreate(), MessageConstants.MAIN_MENU_TEXT);
            }
        }


    }

    private void handleMyDictionaryTest(Update update, Long chatId) {



    }


//    private void handleAddDescription(Update update, Long chatId) {
//
//        if (update.hasMessage()) {
//            if (update.getMessage().hasText()) {
//                String text = update.getMessage().getText();
//
//                if (text.equalsIgnoreCase(ButtonMessage.MENU_BACK)) {
//                    sessionService.updateSession(chatId, UserState.DICTIONARY_MENU);
//                    sendMenu(chatId, buttonCreatorService.dictionaryButtonCreate(), MessageConstants.DICTIONARY_MESSAGE);
//                    return;
//                }
//                AddWordSessionDTO wordSession = addWordSessionService.getSession(chatId);
//                if (Objects.isNull(wordSession)) {
//                    sessionService.updateSession(chatId, UserState.ADD_WORD_WAIT_TRANSLATION);
//                    sendMenu(chatId, buttonCreatorService.backMenuButtonCreate(), MessageConstants.ENTER_WORD_TRANSLATION);
//                    return;
//                }
//
//                wordSession.setDescription(text);
//                addWordSessionService.saveSession(wordSession);
//
//                sessionService.updateSession(chatId, UserState.ADD_WORD_WAIT_DESCRIPTION);
//                sendMenu(chatId, buttonCreatorService.backMenuButtonCreate(), MessageConstants.ENTER_WORD_TRANSLATION);
//                return;
//            }
//        }
//
//        // TODO bu yerda xatolik haqida xabar berish textmas boshqa narsa yuborilsa userga xatolik haqida xabar berish
//
//    }

    private void handleAddTranslation(Update update, Long chatId) {

        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                String wordTranslate = update.getMessage().getText();

                if (wordTranslate.equalsIgnoreCase(ButtonMessage.MENU_BACK)) {
                    sessionService.updateSession(chatId, UserState.DICTIONARY_MENU);
                    sendMenu(chatId, buttonCreatorService.dictionaryButtonCreate(), MessageConstants.DICTIONARY_MESSAGE);
                    return;
                }
                AddWordSessionDTO wordSession = addWordSessionService.getSession(chatId);
                if (Objects.isNull(wordSession)) {
                    sessionService.updateSession(chatId, UserState.ADD_WORD_WAIT_WORD);
                    sendMenu(chatId, buttonCreatorService.backMenuButtonCreate(), MessageConstants.ENTER_WORD);
                    return;
                }

                wordSession.setDescription(wordTranslate);
                Dictionary dictionary = new Dictionary();
                dictionary.setTranslateWord(wordTranslate);
                dictionary.setWord(wordSession.getWord());
                dictionary.setUser(userRepository.findByChatId(chatId).get());
                dictionaryRepository.save(dictionary);
                addWordSessionService.clearSession(chatId);

                sessionService.updateSession(chatId, UserState.DICTIONARY_MENU);
                sendMenu(chatId, buttonCreatorService.dictionaryButtonCreate(), MessageConstants.WORD_CREATE);
                return;
            }
        }

        // TODO bu yerda xatolik haqida xabar berish textmas boshqa narsa yuborilsa userga xatolik haqida xabar berish

    }

    private void handleAddWord(Update update, Long chatId) {

        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                String text = update.getMessage().getText();

                if (text.equalsIgnoreCase(ButtonMessage.MENU_BACK)) {
                    sessionService.updateSession(chatId, UserState.DICTIONARY_MENU);
                    sendMenu(chatId, buttonCreatorService.dictionaryButtonCreate(), MessageConstants.DICTIONARY_MESSAGE);
                } else {
                    AddWordSessionDTO addWordSessionDTO = new AddWordSessionDTO();
                    addWordSessionDTO.setWord(text);
                    addWordSessionDTO.setChatId(chatId);
                    addWordSessionService.saveSession(addWordSessionDTO);
                    /// sessionga saqlandi endi stateni o'zgaartirmiz

                    sessionService.updateSession(chatId, UserState.ADD_WORD_WAIT_TRANSLATION);
                    sendMenu(chatId, buttonCreatorService.backMenuButtonCreate(), MessageConstants.ENTER_WORD_TRANSLATION);
                }

            }
        }

    }

    private void handleDictionaryMenu(Update update, Long chatId) {

        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                String text = update.getMessage().getText();

                if (text.equalsIgnoreCase(ButtonMessage.DICTIONARY_LEARNING_MY_WORDS)) {
                    sessionService.updateSession(chatId, UserState.MY_DICTIONARY_TEST);
                    sendMenu(chatId, buttonCreatorService.backMenuButtonCreate(), MessageConstants.KNOW_DONT_KNOW_MESSAGE);
                }
                else if (text.equalsIgnoreCase(ButtonMessage.MENU_BACK)) {
                    sessionService.updateSession(chatId, UserState.MAIN_MENU);
                    sendMenu(chatId, buttonCreatorService.mainMenuButtonCreate(), MessageConstants.ENTER_WORD);

                }
                else if (text.equalsIgnoreCase(ButtonMessage.DICTIONARY_ADD_WORD)) {
                    sessionService.updateSession(chatId, UserState.ADD_WORD_WAIT_WORD);
                    sendMenu(chatId, buttonCreatorService.backMenuButtonCreate(), MessageConstants.ENTER_WORD);
                }
                else if(text.equalsIgnoreCase(ButtonMessage.DICTIONARY_MY_WORDS)){
                    List<Dictionary> words = dictionaryRepository.findAllByUser_ChatId(chatId);

                    if (words.isEmpty()) {
                        // TODO empty bo'lsa unga so'z qo'shishi kerakligi haqida message yuborish
                        sessionService.updateSession(chatId, UserState.DICTIONARY_MENU);
                        sendMenu(chatId, buttonCreatorService.dictionaryButtonCreate(), MessageConstants.DICTIONARY_NOT_FOUND);
                        return;
                    }

                    // TODO bo'sh bo'lmasa so'zlarrni hammasini userga ko'rsatish quyidagi ko'rinishda   hello <-> salom kabi
                    StringBuilder myWords=new StringBuilder();
                    myWords.append("<b> 🧾 WORDS - SO'ZLAR 🧾 </b> \n \n");

                    for (int i = 0; i < words.size(); i++) {
                        myWords.append("<b>").append((i+1)+".  ").append(words.get(i).getWord()).append(" ↔️ ").append(words.get(i).getTranslateWord()).append("</b>").append("\n\n");
                    }

                    sessionService.updateSession(chatId, UserState.DICTIONARY_MENU);
                    sendMenuTextToParseHtml(chatId, buttonCreatorService.dictionaryButtonCreate(), myWords.toString());
                }

                // TODO dictionry ning qolgan qismini yozish else if bilan

            }

        }


    }


    // Todo main menu handleni tugatish
    private void handleMainMenu(Update update, Long chatId) {

        if (update.hasMessage()) {

            if (update.getMessage().hasText()) {
                String text = update.getMessage().getText();

                if (text.equalsIgnoreCase(ButtonMessage.MAIN_MENU_DICTIONARY)) {
                    botSender.deleteButtons(chatId, "✨Lug'at bo'limiga xush kelibsiz!");
                    sessionService.updateSession(chatId, UserState.DICTIONARY_MENU);
                    sendMenu(chatId, buttonCreatorService.dictionaryButtonCreate(), MessageConstants.DICTIONARY_MESSAGE);

                } else if (text.equalsIgnoreCase(ButtonMessage.MAIN_MENU_STATISTICS)) {
                    botSender.deleteButtons(chatId, "Statistika bo'limiga xush kelibsiz!");
                    // TODO -> statistika bo'limini qilish (lug'atlari soni,
                    //  testlardagi muvoffaqiyati(buni o'ylab ko'rish kerak),
                    //  eng ko'p so'z qo'shganlar reytingini qilish,
                    //  qachon botga start bosganini ko'rsatish, (bu orqali ularga medallar berish)
                    //  (keyinchalik pullik bo'lsa medallarga qarab bazi pullik funksionalliklarni ochib berissh uchun 😁TIRIKCHILIK)
                    //  nechta so'z o'rganilganlar qatorida eknligi)

                } else if (text.equalsIgnoreCase(ButtonMessage.MAIN_MENU_SETTINGS)) {
                    botSender.deleteButtons(chatId, "Sozlamalar bo'limiga xush kelibsiz!");
                    // TODO -> settings bo'limini qilish (kelajakda botni multilanguage qilish uchun)

                } else if (text.equalsIgnoreCase(ButtonMessage.MAIN_MENU_SUPPORT)) {
                    botSender.deleteButtons(chatId, "Yordam bo'limiga xush kelibsiz!");

                    // TODO -> support bo'limini qilish(Foydalnauvchilarning fikr mulohazalari bilan ishlash uchun)
                } else {
                    botSender.sendText(chatId, "\uD83D\uDCCC Sizga ko'rsatilgan buttonlardan birini tanlang.");
                }

            }
        }

    }


    private void sendMenu(Long chatId, ReplyKeyboardMarkup replyKeyboardMarkup, String text) {
        botSender.sendButton(chatId, text, replyKeyboardMarkup);
    }

    private void sendMenuTextToParseHtml(Long chatId, ReplyKeyboardMarkup replyKeyboardMarkup, String text) {
        botSender.sendButtonTextParseHtml(chatId, text, replyKeyboardMarkup);
    }

    private void updateUserPhoneNumber(Update update, Long chatId) {
        Contact contact = update.getMessage().getContact();
        User user = userRepository.findByChatId(chatId).orElse(null);

        if (Objects.isNull(user)) {
            botSender.sendText(chatId, "Siz tizimda maavjud emassiz! \n /start buyrug'i orqali botni qayta ishga tushiring!");
        } else {
            user.setPhoneNumber(contact.getPhoneNumber());
            userRepository.save(user);
        }
        botSender.deleteContactButton(chatId);
    }

    private void checkUserAndSave(Update update, Long chatId) {
        Optional<User> byUser = userRepository.findByChatId(chatId);

        if (byUser.isEmpty()) {
            User user = new User();
            user.setChatId(chatId);
            user.setUsername(update.getMessage().getChat().getUserName());
            user.setFirstName(update.getMessage().getChat().getFirstName());
            user.setLastName(update.getMessage().getChat().getLastName());
            userRepository.save(user);
        }

        ReplyKeyboardMarkup contactButton = buttonCreatorService.sendPhoneNumberButtonCreate();
        String text = """
                👋 Salom, hush kelibsiz!
                Bu bot orqali siz ingliz tilidagi so'zlarni o'rganishingiz, shaxsiy lug'atingizni yaratishingiz va statistikalaringizni kuzatishingiz mumkin.
                Ingliz tilini birga o'rganamiz😉
                """;
        botSender.sendButton(chatId, text, contactButton);
    }

}
