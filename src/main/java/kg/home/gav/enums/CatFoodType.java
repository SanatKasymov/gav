package kg.home.gav.enums;

import java.util.HashMap;
import java.util.Map;

public enum CatFoodType {
    WET_CAT_FOOD("Жидкий корм"),
    DRY_CAT_FOOD("Сухой корм");

    private static final Map<String, CatFoodType> BY_NAME = new HashMap<>();

    public final String value;

    CatFoodType(String value) {
        this.value = value;
    }

    static {
        for (CatFoodType type: values()) {
            BY_NAME.put(type.value, type);
        }
    }

    public static CatFoodType valueOfName(String name) {
        return BY_NAME.get(name);
    }

}
