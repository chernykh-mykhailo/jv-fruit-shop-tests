package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private OperationHandler handler;

    @BeforeEach
    void setUp() {
        handler = new PurchaseOperation();
    }

    @Test
    void handle_validPurchase_subtractsQuantityCorrectly() {
        Storage.setFruitQuantity("grape", 50);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "grape", 10);
        handler.handle(transaction);

        assertEquals(40, Storage.getFruitQuantity("grape"));
    }

    @Test
    void handle_insufficientStock_throwsRuntimeException() {
        Storage.setFruitQuantity("kiwi", 5);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "kiwi", 10);

        assertThrows(RuntimeException.class, () -> handler.handle(transaction),
                "Should throw RuntimeException when attempting to purchase more than stock.");
    }
}
