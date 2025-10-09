package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class FileWriterImpl implements FileWriter {
    @Override
    public void write(String data, String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new RuntimeException("File path cannot be null or blank.");
        }
        if (data == null) {
            throw new RuntimeException("Data to write cannot be null.");
        }
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
            writer.write(data);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file: " + filePath, e);
        }
    }
}
