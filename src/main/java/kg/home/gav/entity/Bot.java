package kg.home.gav.entity;

import kg.home.gav.service.BotService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Data
@Slf4j
public class Bot extends TelegramLongPollingBot {

    @Autowired
    private final BotService botService;

    private Long chatId;

    private boolean inReplenishMode;

    public Bot(BotService botService) {
        this.botService = botService;
    }

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.getCallbackQuery() == null && update.getMessage() == null) {
                return;
            }
            botService.processMessage(update, this);
        } catch (TelegramApiException e) {
            log.error("onUpdateReceived: ", e);
        }
    }
}