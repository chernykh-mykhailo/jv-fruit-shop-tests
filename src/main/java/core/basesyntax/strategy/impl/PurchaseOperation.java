package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class PurchaseOperation implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        if (transaction == null) {
            throw new RuntimeException("Transaction cannot be null.");
        }
        int currentQuantity = Storage.getFruitQuantity(transaction.getFruit());
        int newQuantity = currentQuantity - transaction.getQuantity();
        if (newQuantity < 0) {
            throw new RuntimeException("Not enough fruits " + transaction.getFruit() + " available "
            + currentQuantity + ", but " + transaction.getQuantity() + " was bought.");
        }
        Storage.setFruitQuantity(transaction.getFruit(), newQuantity);
    }
}
