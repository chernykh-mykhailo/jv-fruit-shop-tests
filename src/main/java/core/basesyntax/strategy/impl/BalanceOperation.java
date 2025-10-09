package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class BalanceOperation implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        if (transaction == null) {
            throw new RuntimeException("Transaction cannot be null.");
        }
        Storage.setFruitQuantity(transaction.getFruit(), transaction.getQuantity());
    }
}
