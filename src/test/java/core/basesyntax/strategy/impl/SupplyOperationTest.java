package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private OperationHandler handler;

    @BeforeEach
    void setUp() {
        handler = new SupplyOperation();
    }

    @Test
    void handle_supplyToExistingFruit_addsQuantityCorrectly() {
        Storage.setFruitQuantity("apple", 100);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 50);
        handler.handle(transaction);

        assertEquals(150, Storage.getFruitQuantity("apple"));
    }

    @Test
    void handle_supplyNewFruit_setsQuantityCorrectly() {
        Storage.setFruitQuantity("banana", 0);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 30);
        handler.handle(transaction);

        assertEquals(30, Storage.getFruitQuantity("banana"));
    }
}
