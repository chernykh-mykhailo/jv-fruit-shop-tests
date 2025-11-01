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
    void convertToTransaction_ValidData_ReturnsCorrectList() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "p,apple,10"
        );

        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 10)
        );

        List<FruitTransaction> actual = dataConverter.convertToTransaction(input);

        assertEquals(expected, actual,
                "The list of the transaction should match expected");
    }

    @Test
    void convertToTransaction_InvalidHeader_ThrowsRuntimeException() {
        List<String> input = List.of(
                "invalid,header",
                "b,banana,20"
        );

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> dataConverter.convertToTransaction(input),
                "A RuntimeException should be thrown for an invalid header."
        );

        String expectedMessage = "Incorrect file header format.";
        String actualMessage = thrown.getMessage();
        assertEquals(expectedMessage, actualMessage,
                "The exception message should exactly indicate the header format error.");
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
