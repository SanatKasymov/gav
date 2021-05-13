package kg.home.gav.service;

import kg.home.gav.entity.Bot;
import kg.home.gav.entity.CatFood;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface CatFoodService {
    CatFood getCatFood();
    void save(CatFood catFood);
    void replenishFood(Bot bot);
    void replenishFood(Message message, Bot bot);
}
