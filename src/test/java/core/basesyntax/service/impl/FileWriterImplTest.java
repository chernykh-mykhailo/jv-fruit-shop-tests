package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String TEST_FILE_PATH = "src/test/resources/test_output.csv";
    private FileWriter fileWriter;
    private Path testPath;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
        testPath = Path.of(TEST_FILE_PATH);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(testPath);
    }

    @Test
    void write_ValidData_WritesToFile() throws IOException {
        String testData = "test data";
        fileWriter.write(testData, TEST_FILE_PATH);
        assertEquals(testData, Files.readString(testPath));
    }

    @Test
    void write_NullFilePath_ThrowsRuntimeException() {
        assertThrows(RuntimeException.class, () -> fileWriter.write("data", null));
    }
}
