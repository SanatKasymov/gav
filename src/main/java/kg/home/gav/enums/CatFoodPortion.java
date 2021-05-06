package kg.home.gav.enums;

import java.util.HashMap;
import java.util.Map;

public enum CatFoodPortion {
    QUARTER("1/4", "20 грамм (стандартная упаковка)"),
    THIRD("1/3", "30 грамм (стандартная упаковка)"),
    HALF("1/2", "40 грамм (стандартная упаковка)"),
    TWO_THIRDS("2/3", "60 грамм (стандартная упаковка)"),
    FULL("1", "85 грамм (стандартная упаковка)")
    ;

    private static final Map<String, CatFoodPortion> BY_NAME = new HashMap<>();

    public final String value;
    public final String description;

    CatFoodPortion(String value, String description) {
        this.value = value;
        this.description = description;
    }

    static {
        for (CatFoodPortion type: values()) {
            BY_NAME.put(type.value, type);
        }
    }

    public static CatFoodPortion valueOfName(String name) {
        return BY_NAME.get(name);
    }
}
