package kg.home.gav.service;

import kg.home.gav.entity.Bot;
import kg.home.gav.entity.FeedingEvent;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Date;
import java.util.List;

public interface FeedingEventService {
    void feedCat(String button, CallbackQuery callbackQuery, Bot bot);
    void save(FeedingEvent feedingEvent);
    List<FeedingEvent> getAllByDateTimeAfter(Date date);
}
