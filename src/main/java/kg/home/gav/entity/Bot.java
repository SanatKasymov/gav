package kg.home.gav.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Data
@Slf4j
public class Bot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.getMessage() != null) {
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(update.getMessage().getChatId()));
                message.setText("Test");
                execute(message);
            }
        } catch (TelegramApiException e) {
            log.error("onUpdateReceived: update: {}, exception: {}", update.toString(), e.getMessage());
        }
    }
}