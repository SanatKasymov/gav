package kg.home.gav.service.impl;

import kg.home.gav.entity.Bot;
import kg.home.gav.service.*;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import static lombok.AccessLevel.PRIVATE;

@Data
@Service
@Slf4j
@FieldDefaults(level = PRIVATE)
public class BotServiceImpl implements BotService {

    @Autowired
    CatFoodService catFoodService;
    @Autowired
    CallbackHandlerService callbackHandlerService;
    @Autowired
    KeyboardService keyboardService;

    private boolean inReplenishMode;

    @Override
    public void processMessage(Update update, Bot bot) {

        if (update.hasCallbackQuery()) {
            bot.setChatId(update.getCallbackQuery().getMessage().getChatId());
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("processMessage: callbackQuery: {}");
            callbackHandlerService.processCallback(callbackQuery, bot);
        } else if (bot.isInReplenishMode()) {
            bot.setChatId(update.getMessage().getChatId());
            catFoodService.replenishFood(update.getMessage(), bot);
        } else {
            bot.setChatId(update.getMessage().getChatId());
            keyboardService.getMainKeyboard(bot);;
        }
    }
}
