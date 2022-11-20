package be.pxl.rct.engine.task;

import be.pxl.rct.themepark.Themepark;
import be.pxl.rct.visitor.Visitor;
import be.pxl.rct.visitor.VisitorFactory;

import java.io.BufferedWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreateVisitorsTask implements Runnable {
    private static final Random RANDOM = new Random();
    private VisitorFactory visitorFactory = new VisitorFactory();
    private Themepark themepark;
    private long oneDayInMillis;

    public CreateVisitorsTask(Themepark themepark, long oneDayInMillis) {
        this.themepark = themepark;
        this.oneDayInMillis = oneDayInMillis;
    }

    @Override
    public void run() {
        long endTime = System.currentTimeMillis() + oneDayInMillis;
        themepark.open(endTime);
        List<Visitor> visitorsForToday = new ArrayList<>();
        while (System.currentTimeMillis() < endTime) {
            Visitor visitor = visitorFactory.createVisitor();
            // start visitor for a given time > 1 min and < tijd dat pretpark nog open is
            visitorsForToday.add(visitor);
            visitor.startVisit(themepark, 1000); // TODO change timeInPark
            try {
                Thread.sleep(RANDOM.nextInt(100));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        themepark.close();

        DayStatisticsWriter.writeDayStatistics(Path.of("src/main/resources/logs/"), themepark, visitorsForToday);
    }
}
