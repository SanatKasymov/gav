package kg.home.gav.service;

import kg.home.gav.entity.Bot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackHandlerService {
    void processCallback(CallbackQuery callbackQuery, Bot bot);
}
