package com.example.appenglishlanguagelearning.service;

import com.example.appenglishlanguagelearning.BotSender;
import com.voicerss.tts.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TextToSpeechConverter {

    private final BotSender botSender;

    private static final String VOICERSS_API_KEY = "a3d5c68ffd0d437192a40a5578db78ee";

    private void sendTextToAudio(String messageText, long chatId) {

        String audioFilePath = convertTextToSpeech(messageText);
        if (audioFilePath != null) {
            botSender.sendAudio(chatId, audioFilePath);
        } else {
            botSender.sendText(chatId, "Ovoz yaratishda xatolik yuz berdi.");
        }
    }

    private String convertTextToSpeech(String text) {
        try {
            VoiceProvider tts = new VoiceProvider(VOICERSS_API_KEY);
            VoiceParameters params = new VoiceParameters(text, Languages.English_UnitedStates);
            params.setCodec(AudioCodec.WAV);
            params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
            params.setBase64(false);
            params.setSSML(false);
            params.setRate(-3);

            byte[] voice = tts.speech(params);

            String filePath = "C:\\Users\\User\\Desktop\\bot_audio\\"+text+"-"+ UUID.randomUUID()+".mp3";
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(voice, 0, voice.length);
            fos.flush();
            fos.close();
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
