package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class ReturnOperation implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        if (transaction == null) {
            throw new RuntimeException("Transaction cannot be null.");
        }
        int currentQuantity = Storage.getFruitQuantity(transaction.getFruit());
        int newQuantity = currentQuantity + transaction.getQuantity();
        Storage.setFruitQuantity(transaction.getFruit(), newQuantity);
    }
}
