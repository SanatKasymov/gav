package kg.home.gav.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kg.home.gav.dto.CallbackDto;
import kg.home.gav.entity.Bot;
import kg.home.gav.enums.MainButtons;
import kg.home.gav.service.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CallbackHandlerServiceImpl implements CallbackHandlerService {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    FeedingEventService feedingEventService;
    @Autowired
    KeyboardService keyboardService;
    @Autowired
    HistoryService historyService;
    @Autowired
    CatFoodService catFoodService;


    @Override
    public void processCallback(CallbackQuery callbackQuery, Bot bot) {
        CallbackDto callback = new CallbackDto();
        try {
            callback = objectMapper.readValue(callbackQuery.getData(), CallbackDto.class);
        } catch (JsonProcessingException e) {
            log.error("processCallback: ", e);
        }
        switch (callback.getCallbackType()) {
            case MAIN:
                processMainCallback(callback, bot);
                break;
            case FOOD_PORTION:
                processCatFeedCallback(callback.getCallbackButton(), callbackQuery, bot);
                break;
            default:
                break;
        }
    }

    private void processMainCallback(CallbackDto callbackDto, Bot bot) {
        MainButtons button = MainButtons.valueOfName(callbackDto.getCallbackButton());
        switch (button) {
            case FEED_CAT:
                keyboardService.getCatFeedKeyboard(bot);
                break;
            case REPLENISH_FOOD:
                catFoodService.replenishFood(bot);
                break;
            case FEED_HISTORY:
                historyService.showHistoryForThreeDays(bot);
                break;
            case EMPTY:
            default:
        }
    }

    private void processCatFeedCallback(String button, CallbackQuery callbackQuery, Bot bot) {
        feedingEventService.feedCat(button, callbackQuery, bot);
    }
}
