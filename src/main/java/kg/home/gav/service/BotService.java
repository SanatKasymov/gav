package kg.home.gav.service;

import kg.home.gav.entity.Bot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface BotService {
    void processMessage(Update update, Bot bot) throws TelegramApiException;
}
