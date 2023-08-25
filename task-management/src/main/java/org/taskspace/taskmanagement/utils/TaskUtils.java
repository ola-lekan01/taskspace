package org.taskspace.taskmanagement.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class TaskUtils {
    public static String generateTaskId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

    public static LocalDateTime parseDateString(String dateString) {
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        try {
            return LocalDateTime.parse(dateString, formatter1);
        } catch (DateTimeParseException e1) {
            try {
                return LocalDateTime.parse(dateString, formatter2);
            } catch (DateTimeParseException e2) {
                throw new IllegalArgumentException("Invalid date format: " + dateString);
            }
        }
    }
}
