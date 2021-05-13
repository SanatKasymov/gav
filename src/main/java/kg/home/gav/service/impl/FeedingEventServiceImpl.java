package kg.home.gav.service.impl;

import kg.home.gav.entity.Bot;
import kg.home.gav.entity.CatFood;
import kg.home.gav.entity.FeedingEvent;
import kg.home.gav.enums.CatFoodPortion;
import kg.home.gav.repository.FeedingEventRepo;
import kg.home.gav.service.*;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static lombok.AccessLevel.PRIVATE;

@Data
@Service
@Slf4j
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class FeedingEventServiceImpl implements FeedingEventService {
    @Autowired
    CatFoodService catFoodService;
    @Autowired
    KeyboardService keyboardService;
    @Autowired
    MessageExecuteService messageExecuteService;
    @Autowired
    FeedingEventRepo feedingEventRepo;

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final String SUCCESS_FEED_MSG = "Вы покормили кота:\n" +
            "количество: %s,\n" +
            "время: %s";
    private static final String NOT_ENOUGH_FOOD = "Недостаточно еды:\n" +
            "выбрано: %s,\n" +
            "имеется в запасе: %s " + "грамм";

    @Override
    public void feedCat(String button, CallbackQuery callbackQuery, Bot bot) {
        CatFoodPortion foodPortion = CatFoodPortion.valueOf(button);
        double portion = 0;
        switch (foodPortion) {
            case QUARTER:
                portion = 0.25;
                break;
            case THIRD:
                portion = 0.35;
                break;
            case HALF:
                portion = 0.5;
                break;
            case TWO_THIRDS:
                portion = 0.75;
                break;
            case FULL:
                portion = 1.0;
                break;
            case OTHER:
            default:
        }
        feedCat(portion, foodPortion, callbackQuery, bot);
    }

    @Override
    public void save(FeedingEvent feedingEvent) {
        feedingEventRepo.save(feedingEvent);
    }

    @Override
    public List<FeedingEvent> getAllByDateTimeAfter(Date date) {
        return feedingEventRepo.findAllByDateTimeAfter(date);
    }

    private void feedCat(double portion, CatFoodPortion foodPortion, CallbackQuery callbackQuery, Bot bot) {
        String message;
        CatFood catFood = catFoodService.getCatFood();
        if (catFood == null || catFood.getAmount() < portion) {
            message = String.format(NOT_ENOUGH_FOOD, foodPortion.value, catFood != null ? catFood.getAmount() : 0);
        } else {
            catFood.setAmount(catFood.getAmount() - portion);
            catFoodService.save(catFood);
            dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Bishkek"));
            String dateTime = dateFormat.format(new Date());
            FeedingEvent feedingEvent = FeedingEvent.builder()
                    .dateTime(new Date())
                    .username(callbackQuery.getFrom().getFirstName())
                    .portion(foodPortion)
                    .build();
            save(feedingEvent);
            message = String.format(SUCCESS_FEED_MSG, foodPortion.value, dateTime);
        }
        messageExecuteService.pushMessage(message, bot);
        keyboardService.getMainKeyboard(bot);
    }
}
