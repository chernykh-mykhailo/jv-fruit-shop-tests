package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String TEST_FILE_PATH = "src/test/resources/test_input.csv";
    private FileReader fileReader;
    private Path testPath;

    @BeforeEach
    void setUp() throws IOException {
        fileReader = new FileReaderImpl();
        testPath = Path.of(TEST_FILE_PATH);
        Files.createDirectories(testPath.getParent());
        Files.deleteIfExists(testPath);
        Files.createFile(testPath);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(testPath);
    }

    @Test
    void read_ValidFile_ReturnsContent() throws IOException {
        List<String> expectedLines = List.of("line1", "line2");
        Files.write(testPath, expectedLines);

        List<String> actualLines = fileReader.read(TEST_FILE_PATH);
        assertEquals(expectedLines, actualLines, "Content read should match content written.");
    }

    @Test
    void read_NonExistentFile_ThrowsRuntimeException() throws IOException {
        Files.deleteIfExists(testPath);

        assertThrows(RuntimeException.class, () -> fileReader.read(TEST_FILE_PATH),
                "Should throw RuntimeException for a non-existent file.");
    }
}
