package core.basesyntax.strategy.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<FruitTransaction.Operation, OperationHandler> operationHandlers;

    public OperationStrategyImpl(Map<FruitTransaction.Operation, OperationHandler>
                                         operationHandlers) {
        if (operationHandlers == null) {
            throw new RuntimeException("Operation handlers map cannot be null.");
        }
        this.operationHandlers = Map.copyOf(operationHandlers);
    }

    @Override
    public OperationHandler get(FruitTransaction.Operation operation) {
        if (operation == null) {
            throw new RuntimeException("Operation is null. Cannot get handler.");
        }
        OperationHandler handler = operationHandlers.get(operation);
        if (handler == null) {
            throw new RuntimeException("No handler found for operation: " + operation);
        }
        return handler;
    }
}
