package kg.home.gav.service.impl;

import kg.home.gav.entity.Bot;
import kg.home.gav.service.MessageExecuteService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageExecuteServiceImpl implements MessageExecuteService {

    @Override
    public void pushKeyboard(InlineKeyboardMarkup inlineKeyboardMarkup, Bot bot) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Выберите действие: ");
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendMessage.setChatId(bot.getChatId().toString());
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("pushKeyboard", e);
        }
    }

    @Override
    public void pushMessage(String message, Bot bot) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(bot.getChatId().toString());
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("pushMessage", e);
        }
    }
}
