package be.pxl.rct.engine;

import be.pxl.rct.themepark.Themepark;
import be.pxl.rct.visitor.Visitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DayStatisticsTest {

    private DayStatistics dayStatistics;

    @BeforeEach
    public void init() {
        List<Visitor> visitors = new ArrayList<>();
        Visitor v1 = new Visitor("v1", 100);
        v1.updateHappiness(-70);
        v1.pay(15);
        v1.takeRide(); // happiness +10
        v1.takeRide(); // happiness +10

        Visitor v2 = new Visitor("v2", 250);
        v2.updateHappiness(-50);
        v2.pay(25);
        v2.takeRide();
        v2.takeRide();

        Visitor v3 = new Visitor("v3", 250);
        v3.updateHappiness(-50);
        v3.pay(18);
        v3.takeRide();

        visitors.add(v1);
        visitors.add(v2);
        visitors.add(v3);
        dayStatistics = new DayStatistics(new Themepark("test park", 1000), visitors);
    }

    @Test
    public void testNumberOfVisitors() {
        assertEquals(3, dayStatistics.getNumberOfVisitors());
    }

    @Test
    public void getTotalMoneySpent() {
        assertEquals(58, dayStatistics.getTotalMoneySpent());
    }

    @Test
    public void getAverageHappiness() {
        assertEquals(60.0, dayStatistics.getAverageHappiness());
    }

    @Test
    public void testHappiestVisitor() {
        Optional<Visitor> happiestVisitor = dayStatistics.getHappiestVisitor();
        assertTrue(happiestVisitor.isPresent());
        assertEquals("v2", happiestVisitor.get().getFirstname());
        assertEquals(70, happiestVisitor.get().getHappinessLevel());
    }

    @Test
    public void testGetAverageAmountOfRides() {
       assertEquals(1.66, dayStatistics.getAverageAmountOfRides(), 0.01);
    }

    @Test
    public void testUnhappiestVisitor() {
        Optional<Visitor> unhappiestVisitor = dayStatistics.getUnhappiestVisitor();
        assertTrue(unhappiestVisitor.isPresent());
        assertEquals("v1", unhappiestVisitor.get().getFirstname());
        assertEquals(50, unhappiestVisitor.get().getHappinessLevel());
    }
}
