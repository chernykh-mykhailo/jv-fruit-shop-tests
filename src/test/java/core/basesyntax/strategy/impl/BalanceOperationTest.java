package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private OperationHandler handler;

    @BeforeEach
    void setUp() {
        handler = new BalanceOperation();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void handle_validTransaction_setsCorrectQuantity() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100);
        handler.handle(transaction);
        assertEquals(100, Storage.getFruitQuantity("apple"));
    }
}
