package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String HEADER = "fruit,quantity";
    private static final String COMMA_SEPARATOR = ",";

    @Override
    public String generate() {
        StringBuilder report = new StringBuilder(HEADER + System.lineSeparator());
        for (Map.Entry<String, Integer> entry : Storage.getAllFruits().entrySet()) {
            report.append(entry.getKey())
                    .append(COMMA_SEPARATOR)
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }
        return report.toString();
    }
}
