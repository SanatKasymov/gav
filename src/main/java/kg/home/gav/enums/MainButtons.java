package kg.home.gav.enums;

import java.util.HashMap;
import java.util.Map;

public enum MainButtons {
    FEED_CAT ("Покормить кота"),
    REPLENISH_FOOD ("Пополнить корм"),
    FEED_HISTORY ("История кормления"),
    EMPTY("Пока не придумал для чего");

    private static final Map<String, MainButtons> BY_NAME = new HashMap<>();

    public final String value;

    MainButtons(String value) {
        this.value = value;
    }

    static {
        for (MainButtons type: values()) {
            BY_NAME.put(type.toString(), type);
        }
    }

    public static MainButtons valueOfName(String name) {
        return BY_NAME.get(name);
    }
}
