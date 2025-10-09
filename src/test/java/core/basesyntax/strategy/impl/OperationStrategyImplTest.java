package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void get_validOperation_returnsHandler() {
        OperationHandler handler = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertNotNull(handler);
        assertTrue(handler instanceof BalanceOperation);
    }

    @Test
    void get_nullOperation_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> operationStrategy.get(null));
    }

    @Test
    void get_unsupportedOperation_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> operationStrategy
                .get(FruitTransaction.Operation.PURCHASE));
    }

    @Test
    void get_unsupportedOperationCode_throwsRuntimeException() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(handlers);

        assertThrows(RuntimeException.class,
                () -> operationStrategy.get(FruitTransaction.Operation.PURCHASE),
                "Should throw exception for an operation type that has no handler.");
    }
}
