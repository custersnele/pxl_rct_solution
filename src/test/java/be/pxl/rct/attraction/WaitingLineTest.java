package be.pxl.rct.attraction;

import be.pxl.rct.visitor.Visitor;
import be.pxl.rct.visitor.VisitorFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WaitingLineTest {

    @Test
    public void waitingLineWithIntegers() {
        WaitingLine<Integer> waitingLine = new WaitingLine<>(null);
        waitingLine.enterWaitingLine(15);
        waitingLine.enterWaitingLine(20);
        assertEquals(2, waitingLine.getWaitingLineSize());
    }

    @Test
    public void waitingLineWithVisitors() {
        WaitingLine<Visitor> waitingLine = new WaitingLine<>(new RollerCoaster("rollercoaster", new RollercoasterType(7, RideGenre.ROLLER_COASTER, 2500)));
        waitingLine.enterWaitingLine(VisitorFactory.createVisitor());
        waitingLine.enterWaitingLine(VisitorFactory.createVisitor());
        waitingLine.enterWaitingLine(VisitorFactory.createVisitor());
        assertEquals(3, waitingLine.getWaitingLineSize());
    }
}
