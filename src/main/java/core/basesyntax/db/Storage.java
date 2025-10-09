package core.basesyntax.db;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    private static final Map<String, Integer> fruitStorage = new HashMap<>();

    public static void setFruitQuantity(String fruit, int quantity) {
        if (fruit == null || fruit.isBlank()) {
            throw new RuntimeException("Fruit name cannot be null or blank.");
        }
        if (quantity < 0) {
            throw new RuntimeException("Fruit quantity cannot be negative.");
        }
        fruitStorage.put(fruit, quantity);
    }

    public static int getFruitQuantity(String fruit) {
        if (fruit == null || fruit.isBlank()) {
            throw new RuntimeException("Fruit name cannot be null or blank.");
        }
        return fruitStorage.getOrDefault(fruit, 0);
    }

    public static Map<String, Integer> getAllFruits() {
        return Map.copyOf(fruitStorage);
    }
}
