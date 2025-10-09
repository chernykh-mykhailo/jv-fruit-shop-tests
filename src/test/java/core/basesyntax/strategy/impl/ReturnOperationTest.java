package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private OperationHandler handler;

    @BeforeEach
    void setUp() {
        handler = new ReturnOperation();
    }

    @Test
    void handle_returnToExistingFruit_addsQuantityCorrectly() {
        Storage.setFruitQuantity("orange", 10);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "orange", 5);
        handler.handle(transaction);

        assertEquals(15, Storage.getFruitQuantity("orange"));
    }
}
