package kg.home.gav.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kg.home.gav.dto.CallbackDto;
import kg.home.gav.entity.Bot;
import kg.home.gav.enums.CallbackLevel;
import kg.home.gav.enums.CatFoodPortion;
import kg.home.gav.enums.MainButtons;
import kg.home.gav.service.KeyboardService;
import kg.home.gav.service.MessageExecuteService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KeyboardServiceImpl implements KeyboardService {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MessageExecuteService messageExecuteService;

    @Override
    public void getMainKeyboard(Bot bot) {
        List<List<InlineKeyboardButton>> mainButtons = new ArrayList<>();
        try {
            mainButtons = getMainButtons();
        } catch (JsonProcessingException e) {
            log.error("getMainKeyboard(): ", e);
        }
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        inlineKeyboard.setKeyboard(mainButtons);
        messageExecuteService.pushKeyboard(inlineKeyboard, bot);
    }

    @Override
    public void getCatFeedKeyboard(Bot bot) {
        List<List<InlineKeyboardButton>> catFeedButtons = new ArrayList<>();
        try {
            catFeedButtons = getCatFeedButtons();
        } catch (JsonProcessingException e) {
            log.error("getCatFeedKeyboard: ", e);
        }
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        inlineKeyboard.setKeyboard(catFeedButtons);

        messageExecuteService.pushKeyboard(inlineKeyboard,  bot);
    }

    private List<List<InlineKeyboardButton>> getMainButtons() throws JsonProcessingException {
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();
        for (MainButtons button: MainButtons.values()) {
            InlineKeyboardButton inlineButton = new InlineKeyboardButton();
            inlineButton.setText(button.value);

            CallbackDto callbackDto = new CallbackDto(CallbackLevel.MAIN, button.toString());
            inlineButton.setCallbackData(objectMapper.writeValueAsString(callbackDto));
            inlineKeyboardButtons.add(Arrays.asList(inlineButton));
        }
        return inlineKeyboardButtons;
    }

    private List<List<InlineKeyboardButton>> getCatFeedButtons() throws JsonProcessingException {
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();
        boolean isOdd = false;
        InlineKeyboardButton prevButton = new InlineKeyboardButton();
        for (CatFoodPortion button: CatFoodPortion.values()) {
            InlineKeyboardButton inlineButton = new InlineKeyboardButton();
            inlineButton.setText(button.value);

            CallbackDto callbackDto = new CallbackDto(CallbackLevel.FOOD_PORTION, button.toString());
            inlineButton.setCallbackData(objectMapper.writeValueAsString(callbackDto));
            if (isOdd) {
                inlineKeyboardButtons.add(Arrays.asList(prevButton, inlineButton));
            }
            prevButton = inlineButton;
            isOdd = !isOdd;
        }
        return inlineKeyboardButtons;
    }
 }
