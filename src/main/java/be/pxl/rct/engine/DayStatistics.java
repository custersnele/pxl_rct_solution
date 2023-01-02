package be.pxl.rct.engine;

import be.pxl.rct.exception.RCTException;
import be.pxl.rct.themepark.Themepark;
import be.pxl.rct.visitor.Visitor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DayStatistics {

    private final List<Visitor> visitors;
    private final Themepark themepark;

    public DayStatistics(Themepark themepark, List<Visitor> visitors) {
        this.visitors = visitors;
        this.themepark = themepark;
    }

    public int getNumberOfVisitors() {
        return visitors.size();
    }

    public Optional<Visitor> getHappiestVisitor() {
        return visitors.stream().max(Comparator.comparing(Visitor::getHappinessLevel));
    }

    public Optional<Visitor> getUnhappiestVisitor() {
        return visitors.stream().min(Comparator.comparing(Visitor::getHappinessLevel));
    }

    public double getTotalMoneySpent() {
        return visitors.stream().mapToDouble(Visitor::getCashSpent).sum();
    }

    public double getAverageTimeSpent() {
        return visitors.stream().mapToLong(Visitor::getTimeInPark).average().orElse(0);
    }

    public double getAverageHappiness() {
        return visitors.stream().mapToInt(Visitor::getHappinessLevel).average().orElse(0);
    }

    public Optional<Visitor> getLongestVisit() {
        return visitors.stream().max(Comparator.comparingLong(Visitor::getTimeInPark));
    }

    public double getAverageAmountOfRides() {
        return visitors.stream().mapToInt(Visitor::getNumberOfRides).average().orElse(0);
    }

    public void writeToFile(Path folder) throws RCTException {
        Path logFile = folder.resolve(Path.of(themepark.getName() + "_" + themepark.getDaysOpen() + ".log"));
        try (BufferedWriter writer = Files.newBufferedWriter(logFile)) {
            writer.write("\uD83C\uDFA1 " + themepark.getName() + " @ dag " + themepark.getDaysOpen() + " \uD83D\uDCC5");
            writer.newLine();
            writer.write("\uD83D\uDCB0 Cash: " + String.format("%,.2f", themepark.getCash()));
            writer.newLine();
            writer.write("\uD83E\uDDCD Amount of visitors: " + getNumberOfVisitors());
            writer.newLine();
            writer.write("\uD83E\uDD70 Average happiness: " + String.format("%,.2f", getAverageHappiness()));
            writer.newLine();
            if (getHappiestVisitor().isPresent()) {
                writer.write("\uD83D\uDE03 Happiest visitor: " + getHappiestVisitor().get().getFirstname() + " " + getHappiestVisitor().get().getHappinessLevel());
            }
            writer.newLine();
            if (getUnhappiestVisitor().isPresent()) {
                writer.write("\uD83D\uDE21 Unhappiest visitor: " + getUnhappiestVisitor().get().getFirstname() + " " + getUnhappiestVisitor().get().getHappinessLevel());
            }
            writer.newLine();
            writer.write("\uD83C\uDFA2 Average amount of rides per visitor " + String.format("%,.2f",getAverageAmountOfRides()));
            writer.newLine();
            writer.write("\uD83E\uDD11 Total money spend by visitors: " + String.format("%,.2f", getTotalMoneySpent()));
            writer.newLine();
            writer.write("\uD83D\uDD5C Average amount of time spent in " + themepark.getName() + ": " + String.format("%,.2f", getAverageTimeSpent()) + " ms");
            writer.newLine();
            if (getLongestVisit().isPresent()) {
                writer.write("\uD83E\uDDD3 Longest visit to " + themepark.getName() + ": " + getLongestVisit().get().getFirstname() + " " + getLongestVisit().get().getTimeInPark());
            }
        } catch (IOException e) {
            throw new RCTException("Error while writing statistics of day " + themepark.getDaysOpen());
        }
    }
}
