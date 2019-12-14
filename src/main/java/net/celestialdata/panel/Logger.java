package net.celestialdata.panel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mm:ss a");
    private static DateTimeFormatter fileFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    public static void networkLog(String newEntry) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("logs/network/" + fileFormatter.format(LocalDateTime.now()) + "_network.log", true))) {
            writer.write(formatter.format(LocalDateTime.now()) + " --> " + newEntry);
        } catch (Exception ignored) {}
    }

    static void useLog(String newEntry) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("logs/use/" + fileFormatter.format(LocalDateTime.now()) + "_use.log", true))) {
            writer.write(formatter.format(LocalDateTime.now()) + newEntry);
        } catch (Exception ignored) {}
    }

    static void launchLog() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("logs/launches.log", true))) {
            writer.write(formatter.format(LocalDateTime.now()) + " --> System Started\n");
        } catch (Exception ignored) { }
    }
}