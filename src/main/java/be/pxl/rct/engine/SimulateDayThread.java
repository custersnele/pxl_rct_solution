package be.pxl.rct.engine;

import be.pxl.rct.exception.RCTException;
import be.pxl.rct.themepark.Themepark;
import be.pxl.rct.visitor.Visitor;
import be.pxl.rct.visitor.VisitorFactory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulateDayThread extends Thread {
    private static final Random RANDOM = new Random();
    private final Themepark themepark;
    private final long oneDayInMillis;
    private final boolean debug;

    public SimulateDayThread(Themepark themepark, long oneDayInMillis, boolean debug) {
        this.themepark = themepark;
        this.oneDayInMillis = oneDayInMillis;
        this.debug = debug;
    }

    @Override
    public void run() {
        long endTime = System.currentTimeMillis() + oneDayInMillis;
        if (debug) {
            System.out.println(themepark.getName() + " opens");
        }
        themepark.open(endTime, debug);
        List<Visitor> visitorsForToday = new ArrayList<>();
        while (System.currentTimeMillis() < endTime) {
            Visitor visitor = VisitorFactory.createVisitor();
            visitor.setDebug(debug);
            // start visitor for a given time > 1 min and < tijd dat pretpark nog open is
            visitorsForToday.add(visitor);
            long min = Math.min(System.currentTimeMillis() + 1000, endTime - 1);
            visitor.startVisit(themepark, RANDOM.nextLong(min, endTime));
            try {
                Thread.sleep(RANDOM.nextInt(100));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        themepark.close();
        if (debug) {
            System.out.println(themepark.getName() + " closes");
        }

        try {
            DayStatistics dayStatistics = new DayStatistics(themepark, visitorsForToday);
            dayStatistics.writeToFile(Path.of("src/main/resources/logs/"));
        } catch (RCTException e) {
            System.out.println("Error writing file with day statistics. " + e.getMessage());
        }
    }
}
