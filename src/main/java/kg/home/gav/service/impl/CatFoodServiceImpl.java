package kg.home.gav.service.impl;

import kg.home.gav.entity.Bot;
import kg.home.gav.entity.CatFood;
import kg.home.gav.repository.CatFoodRepo;
import kg.home.gav.service.CatFoodService;
import kg.home.gav.service.KeyboardService;
import kg.home.gav.service.MessageExecuteService;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import static lombok.AccessLevel.PRIVATE;

@Data
@Service
@Slf4j
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CatFoodServiceImpl implements CatFoodService {

    private static final Long CAT_FOOD_ID = 1L;
    private static String REPLENISH_MESSAGE = "Введите количество еды для пополнения (только цифра): ";
    private static String REPLENISH_MESSAGE_SUCCESS = "Количесто еды успешно пополнено на %.2f,\nОбщее количество: %.2f";

    @Autowired
    CatFoodRepo catFoodRepo;
    @Autowired
    MessageExecuteService messageExecuteService;
    @Autowired
    KeyboardService keyboardService;

    @Override
    public CatFood getCatFood() {
        return catFoodRepo.findById(CAT_FOOD_ID).orElse(null);
    }

    @Override
    public void save(CatFood catFood) {
        catFoodRepo.save(catFood);
    }

    @Override
    public void replenishFood(Bot bot) {
        messageExecuteService.pushMessage(REPLENISH_MESSAGE, bot);
        bot.setInReplenishMode(!bot.isInReplenishMode());
    }

    @Override
    public void replenishFood(Message message, Bot bot) {
        String messageInfo;
        try {
            double replenishAmount = Double.
                    parseDouble(message.getText().replaceAll("\\s+", ""));
            CatFood catFood = getCatFood();
            if (catFood != null) {
                catFood.setAmount(catFood.getAmount()+replenishAmount);
            } else {
                catFood = CatFood.builder()
                        .amount(replenishAmount)
                        .build();
            }
            catFoodRepo.save(catFood);
            messageInfo = String.format(REPLENISH_MESSAGE_SUCCESS, replenishAmount, catFood.getAmount());
        } catch (Exception e) {
            log.error("replenishFood: ", e);
            messageInfo = "Неверно введено значение (Необходимо ввести только число, пример: 8)";
        }
        messageExecuteService.pushMessage(messageInfo, bot);
        keyboardService.getMainKeyboard(bot);
        bot.setInReplenishMode(!bot.isInReplenishMode());
    }
}
