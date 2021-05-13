package kg.home.gav.service.impl;

import kg.home.gav.entity.Bot;
import kg.home.gav.entity.FeedingEvent;
import kg.home.gav.service.FeedingEventService;
import kg.home.gav.service.HistoryService;
import kg.home.gav.service.MessageExecuteService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    FeedingEventService feedingEventService;
    @Autowired
    MessageExecuteService messageExecuteService;

    private static final String MSG_PATTERN = "Дата и время: %s \nПользователь: %s \nКоличество: %s \n";
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    public void showHistoryForThreeDays(Bot bot) {
        LocalDateTime ldt = LocalDateTime.now().minusDays(3);
        Date threeDaysAgo = Date.from(ldt.atZone(TimeZone.getTimeZone("Asia/Bishkek").toZoneId()).toInstant());
        List<FeedingEvent> feedingEvents =
                feedingEventService.getAllByDateTimeAfter(threeDaysAgo);
        String message = createMessage(feedingEvents);
        messageExecuteService.pushMessage(message, bot);
    }

    private String createMessage(List<FeedingEvent> feedingEvents) {
        if (feedingEvents == null || feedingEvents.size() == 0) {
            return "Нет истории на данный момент";
        }
        StringBuilder message = new StringBuilder();
        for (FeedingEvent feedingEvent: feedingEvents) {
            dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Bishkek"));
            String strDate = dateFormat.format(feedingEvent.getDateTime());
            String username = feedingEvent.getUsername();
            String portion = feedingEvent.getPortion().value;
            message.append(String.format(MSG_PATTERN, strDate, username, portion));
            message.append(System.getProperty("line.separator"));
        }
        return message.toString();
    }
}
