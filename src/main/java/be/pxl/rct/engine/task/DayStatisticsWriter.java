package be.pxl.rct.engine.task;

import be.pxl.rct.shop.Shop;
import be.pxl.rct.themepark.Themepark;
import be.pxl.rct.visitor.Visitor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.List;

public class DayStatisticsWriter {

    public static void writeDayStatistics(Path folder, Themepark themepark, List<Visitor> visitors) {
        Path logFile = folder.resolve(Path.of(themepark.getName() + "_" + themepark.getDaysOpen() + ".log"));
        System.out.println(logFile.toAbsolutePath());
        try (BufferedWriter writer = Files.newBufferedWriter(logFile)) {
            writer.write("Day " + themepark.getDaysOpen() + " @ " + themepark.getName());
            writer.newLine();
            writer.write("Cash: " + themepark.getCash());
            writer.newLine();
            writer.write("Number of visitors: " + visitors.size());
            writer.newLine();
            writer.write("Total cash spent: " + visitors.stream().mapToDouble(Visitor::getCashSpent).sum());
            writer.newLine();
            IntSummaryStatistics happinessStatistics = visitors.stream().mapToInt(Visitor::getHappinessLevel).summaryStatistics();
            writer.write("Happiness statistics: " + happinessStatistics);
            writer.newLine();
            IntSummaryStatistics rideStatistics = visitors.stream().mapToInt(Visitor::getNumberOfRides).summaryStatistics();
            writer.write("Ride statistics: " + rideStatistics);
            writer.newLine();
            DoubleSummaryStatistics moneyStatistics = visitors.stream().mapToDouble(Visitor::getCashSpent).summaryStatistics();
            writer.write("Money statistics: " + moneyStatistics);
            writer.newLine();
            themepark.getShops().stream()
                    .sorted(Comparator.comparingInt(Shop::getItemsSold))
                    .forEach(s -> {
                        try {
                            writer.write(s.getDayDetails());
                            writer.newLine();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException e) {
            System.out.println("Error while writing statistics of day " + themepark.getDaysOpen());
        }
    }

}
