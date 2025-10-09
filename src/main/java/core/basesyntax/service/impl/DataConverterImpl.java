package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class DataConverterImpl implements DataConverter {
    private static final String LINE_SEPARATOR = ",";
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final int HEADER_LINE = 0;
    private static final int PART_COUNT = 3;
    private static final int MIN_QUANTITY = 0;

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> inputReport) {
        if (inputReport == null || inputReport.isEmpty()) {
            throw new RuntimeException("Input report cannot be null or empty");
        }
        if (!inputReport.get(HEADER_LINE).trim().equals("type,fruit,quantity")) {
            throw new RuntimeException("Incorrect file header format.");
        }
        AtomicInteger lineCounter = new AtomicInteger(1);
        return inputReport.stream()
                .skip(1)
                .map(line -> parseLine(line, lineCounter.getAndIncrement()))
                .collect(Collectors.toList());
    }

    private FruitTransaction parseLine(String line, int lineNumber) {
        if (line == null) {
            throw new RuntimeException("Line " + lineNumber + ": Input line cannot be null.");
        }
        String[] parts = line.split(LINE_SEPARATOR);
        if (parts.length != PART_COUNT) {
            throw new RuntimeException("Line " + lineNumber + ": Invalid line format.");
        }
        try {
            String operationCode = parts[OPERATION_INDEX].trim();
            String fruit = parts[FRUIT_INDEX].trim();
            int quantity = Integer.parseInt(parts[QUANTITY_INDEX].trim());

            FruitTransaction.Operation operation = FruitTransaction.Operation
                    .getByCode(operationCode);
            if (quantity < MIN_QUANTITY) {
                throw new RuntimeException("Quantity cannot be negative: " + line);
            }
            if (fruit.isBlank()) {
                throw new RuntimeException("Fruit name cannot be blank: " + line);
            }
            return new FruitTransaction(operation, fruit, quantity);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error parsing line: " + line, e);
        }
    }
}
