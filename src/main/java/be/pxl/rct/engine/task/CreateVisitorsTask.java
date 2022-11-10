package be.pxl.rct.engine.task;

import be.pxl.rct.themepark.Themepark;
import be.pxl.rct.visitor.Visitor;
import be.pxl.rct.visitor.VisitorFactory;

import java.io.BufferedWriter;
import java.util.Random;

public class CreateVisitorsTask implements Runnable {
    private static final Random RANDOM = new Random();
    private VisitorFactory visitorFactory = new VisitorFactory();
    private Themepark themepark;
    private long oneDayInMillis;

    private ThemeparkLogger logger;

    public CreateVisitorsTask(Themepark themepark, long oneDayInMillis, ThemeparkLogger logger) {
        this.themepark = themepark;
        this.oneDayInMillis = oneDayInMillis;
        this.logger = logger;
    }

    @Override
    public void run() {
        //themepark.open();
        long endTime = System.currentTimeMillis() + oneDayInMillis;
        while (System.currentTimeMillis() < endTime) {
            Visitor visitor = visitorFactory.createVisitor();
            // TODO create logging mechanism for threads
           logger.log("NEW VISITOR: " + visitor);
            // start visitor for a given time > 1 min and < tijd dat pretpark nog open is
            themepark.addVisitor(visitor);
            try {
                Thread.sleep(RANDOM.nextInt(30));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
