package kg.home.gav.service;

import kg.home.gav.entity.Bot;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface MessageExecuteService {
    void pushKeyboard(InlineKeyboardMarkup inlineKeyboardMarkup, Bot bot);
    void pushMessage(String message, Bot bot);
}
