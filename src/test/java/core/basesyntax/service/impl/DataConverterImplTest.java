package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_ValidData_ReturnsCorrectTransactions() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "p,apple,10"
        );
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(input);
        assertEquals(2, transactions.size());
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
    }

    @Test
    void convertToTransaction_InvalidHeader_ThrowsRuntimeException() {
        List<String> input = List.of(
                "invalid,header",
                "b,banana,20"
        );
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(input));
    }

    @Test
    void convertToTransaction_InvalidLineFormat_ThrowsRuntimeException() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana"
        );
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(input));
    }

    @Test
    void convertToTransaction_InvalidQuantityFormat_ThrowsRuntimeException() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,apple,ten"
        );
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(input),
                "Should throw RuntimeException when quantity is not an integer.");
    }

    @Test
    void convertToTransaction_EmptyFruitName_ThrowsRuntimeException() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,,10"
        );
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(input),
                "Should throw RuntimeException when fruit name is empty.");
    }
}
