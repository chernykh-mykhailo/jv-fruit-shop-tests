package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String APPLE_LINE = "apple,30" + System.lineSeparator();
    private static final String BANANA_LINE = "banana,50" + System.lineSeparator();
    private static final String HEADER = "fruit,quantity" + System.lineSeparator();

    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.setFruitQuantity("apple", 30);
        Storage.setFruitQuantity("banana", 50);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void generate_emptyStorage_returnsHeaderOnly() {
        Storage.setFruitQuantity("apple", 0);
        Storage.setFruitQuantity("banana", 0);

        String actualReport = reportGenerator.generate();
        assertTrue(actualReport.startsWith(HEADER), "Report must start with the header.");
        assertTrue(actualReport.contains("apple,0"), "Report must contain apple with "
                + "0 quantity.");
        assertTrue(actualReport.contains("banana,0"), "Report must contain banana with "
                + "0 quantity.");
    }

    @Test
    void generate_withFruits_containsAllPartsRegardlessOfOrder() {
        String actualReport = reportGenerator.generate();

        assertTrue(actualReport.startsWith(HEADER), "Report must start with the header.");

        assertTrue(actualReport.contains(APPLE_LINE.trim()),
                "Report must contain the correct line for apple.");

        assertTrue(actualReport.contains(BANANA_LINE.trim()),
                "Report must contain the correct line for banana.");

        long linesCount = actualReport.lines().count();
        assertEquals(3, linesCount, "Report should contain exactly 3 lines "
                + "(Header + 2 Fruits).");
    }
}
