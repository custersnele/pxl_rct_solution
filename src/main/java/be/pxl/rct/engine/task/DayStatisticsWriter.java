package be.pxl.rct.engine.task;

import be.pxl.rct.exception.RCTException;
import be.pxl.rct.themepark.Themepark;
import be.pxl.rct.visitor.Visitor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

public class DayStatisticsWriter {

    public static void writeDayStatistics(Path folder, Themepark themepark, List<Visitor> visitors) throws RCTException {
        Path logFile = folder.resolve(Path.of(themepark.getName() + "_" + themepark.getDaysOpen() + ".log"));
        try (BufferedWriter writer = Files.newBufferedWriter(logFile)) {
            writer.write("\uD83C\uDFA1 " + themepark.getName() + " @ dag " + themepark.getDaysOpen() + " \uD83D\uDCC5");
            writer.newLine();
            writer.write("\uD83D\uDCB0 Cash: " + String.format("%,.2f", themepark.getCash()));
            writer.newLine();
            writer.write("\uD83E\uDDCD Amount of visitors: " + visitors.size());
            writer.newLine();
            writer.write("\uD83E\uDD70 Average happiness: " + String.format("%,.2f", visitors.stream().mapToDouble(Visitor::getHappinessLevel).average().getAsDouble()));
            writer.newLine();
            writer.write("\uD83D\uDE03 Happiest visitor: " + visitors.stream().max(Comparator.comparing(Visitor::getHappinessLevel)).get().getFirstname() + " " + String.format("%,.2f", visitors.stream().mapToDouble(Visitor::getHappinessLevel).max().getAsDouble()));
            writer.newLine();
            writer.write("\uD83D\uDE21 Unhappiest visitor: " + visitors.stream().min(Comparator.comparing(Visitor::getHappinessLevel)).get().getFirstname() + " " + String.format("%,.2f", visitors.stream().mapToDouble(Visitor::getHappinessLevel).min().getAsDouble()));
            writer.newLine();
            writer.write("\uD83C\uDFA2 Average amount of rides per visitor " + String.format("%,.2f", visitors.stream().mapToDouble(Visitor::getNumberOfRides).average().getAsDouble()));
            writer.newLine();
            writer.write("\uD83E\uDD11 Total money spend by visitors: " + String.format("%,.2f", visitors.stream().mapToDouble(Visitor::getCashSpent).sum()));
            writer.newLine();
            writer.write("\uD83D\uDD5C Average amount of time spend in " + themepark.getName() + ": " + String.format("%,.2f", visitors.stream().mapToLong(Visitor::getTimeInPark).average().getAsDouble()) + " ms");
            writer.newLine();
            writer.write("\uD83E\uDDD3 Longest visit to " + themepark.getName() + ": " + visitors.stream().min(Comparator.comparing(Visitor::getTimeInPark)).get().getFirstname() + " " + String.format("%,.2f", visitors.stream().mapToDouble(Visitor::getTimeInPark).max().getAsDouble()));
        } catch (IOException e) {
            throw new RCTException("Error while writing statistics of day " + themepark.getDaysOpen());
        }
    }
}
