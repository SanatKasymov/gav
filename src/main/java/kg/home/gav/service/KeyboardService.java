package kg.home.gav.service;

import kg.home.gav.entity.Bot;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface KeyboardService {
    void getMainKeyboard(Bot bot);
    void getCatFeedKeyboard(Bot bot);
}
