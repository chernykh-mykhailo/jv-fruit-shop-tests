package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class SupplyOperation implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        if (transaction == null) {
            throw new RuntimeException("Transaction cannot be null.");
        }
        if (transaction.getFruit() == null || transaction.getFruit().isBlank()) {
            throw new RuntimeException("Fruit name in transaction cannot be null or blank.");
        }
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Quantity in transaction cannot be negative.");
        }
        int currentQuantity = Storage.getFruitQuantity(transaction.getFruit());
        int newQuantity = currentQuantity + transaction.getQuantity();
        Storage.setFruitQuantity(transaction.getFruit(), newQuantity);
    }
}
