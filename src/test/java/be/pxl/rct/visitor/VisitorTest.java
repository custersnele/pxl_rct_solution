package be.pxl.rct.visitor;

import be.pxl.rct.exception.UnsufficientCashException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VisitorTest {

    @Test
    public void visitorCanPayAnAmount() {
        Visitor visitor = new Visitor("Abby", 1000);
        visitor.pay(5);
        visitor.pay(3);
        assertEquals(992, visitor.getCashAvailable());
        assertEquals(8, visitor.getCashSpent());
    }

    @Test
    public void anExceptionIsThrownIfVisitorCannotPay() {
        Visitor visitor = new Visitor("Abby", 10);
        assertThrows(UnsufficientCashException.class, () -> visitor.pay(20));
    }


    @Test
    public void visitorCanTakeARide() {
        Visitor visitor = new Visitor("Abby", 25);
        visitor.takeRide();
        assertEquals(1, visitor.getNumberOfRides());
        assertFalse(visitor.isInWaitingLine());
    }
}
