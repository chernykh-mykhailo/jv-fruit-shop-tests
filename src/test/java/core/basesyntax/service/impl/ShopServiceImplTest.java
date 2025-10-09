package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperation;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static final String APPLE = "apple";

    private ShopService shopService;

    @BeforeEach
    void setUp() {
        // 1. Створюємо справжню мапу обробників
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        // ... додайте всі інші обробники ...

        // 2. Створюємо справжню стратегію
        OperationStrategy operationStrategy = new OperationStrategyImpl(handlers);

        // 3. Створюємо справжній сервіс
        shopService = new ShopServiceImpl(operationStrategy);

        // Оскільки ми не можемо очистити сховище, ми встановлюємо початкове значення 0
        Storage.setFruitQuantity(APPLE, 0);
    }

    @Test
    void processTransactions_validList_updatesStorageCorrectly() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, APPLE, 50)
        );

        shopService.process(transactions);

        assertEquals(150, Storage.getFruitQuantity(APPLE), "Storage should reflect"
                + " all transactions.");
    }

    @Test
    void processTransactions_nullList_shouldThrowRuntimeException() {
        assertThrows(RuntimeException.class,
                () -> shopService.process(null),
                "Should throw exception when transaction list is null.");
    }
}
