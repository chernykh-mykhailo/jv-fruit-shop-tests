package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;

public class ShopServiceImpl implements ShopService {
    private final OperationStrategy operationStrategy;

    public ShopServiceImpl(OperationStrategy operationStrategy) {
        if (operationStrategy == null) {
            throw new RuntimeException("Operation strategy cannot be null.");
        }
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void process(List<FruitTransaction> transactions) {
        if (transactions == null) {
            throw new RuntimeException("Transaction list cannot be null.");
        }
        for (FruitTransaction transaction : transactions) {
            if (transaction == null) {
                throw new RuntimeException("Transaction in the list cannot be null");
            }
            if (transaction.getOperation() == null) {
                throw new RuntimeException("Transaction operation cannot be null.");
            }
            OperationHandler handler = operationStrategy.get(transaction.getOperation());
            handler.handle(transaction);
        }
    }
}
