package be.pxl.rct.attraction;

import be.pxl.rct.visitor.Visitor;

import java.util.LinkedList;
import java.util.Queue;

public class WaitingLine<T> extends Thread  {

    private long closingTime;
    private QueueArea<T> waitingFor;
    private Queue<T> waitingLine = new LinkedList<>();

    public WaitingLine(QueueArea<T> waitingFor) {
       this.waitingFor = waitingFor;
    }


    public synchronized void enterWaitingLine(T visitor) {
        waitingLine.add(visitor);
        //visitor.setInWaitingLine(true);
    }

    @Override
    public void run() {
        while (System.currentTimeMillis() < closingTime) {
            //if (!waitingLine.isEmpty()) {
                //System.out.println("Fill with passengers: " + attractionType.getPassengers());
                for (int i = 0; i < waitingFor.getAllowed(); i++) {
                        T visitor = waitingLine.poll();
                        if (visitor != null) {
                            waitingFor.enter(visitor);
                            //System.out.println("take ride");
                        }
                }
                //System.out.println("Do ride");
                try {
                    Thread.sleep(waitingFor.getTime());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
           // }
        }
        System.out.println("Roller coaster closed");
        waitingLine = new LinkedList<>(); // clear the waitingline at the end of day
    }

    public int getWaitingLineSize() {
        return waitingLine.size();
    }

    public String toString() {
        return "  --> " + getWaitingLineSize();
    }

    public void setClosingTime(long closingTime) {
        this.closingTime = closingTime;
    }
}
