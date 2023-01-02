package be.pxl.rct.attraction;

import java.util.LinkedList;
import java.util.Queue;

public class WaitingLine<T> extends Thread {

    private long closingTime;
    private QueueArea<T> waitingFor;
    private Queue<T> waitingLine = new LinkedList<>();
    private boolean debug;

    public WaitingLine(QueueArea<T> waitingFor) {
        this.waitingFor = waitingFor;
    }

    public synchronized void enterWaitingLine(T visitor) {
        waitingLine.add(visitor);
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    @Override
    public void run() {
        while (System.currentTimeMillis() < closingTime) {
            for (int i = 0; i < waitingFor.getAllowed(); i++) {
                T visitor = waitingLine.poll();
                if (visitor != null) {
                    waitingFor.enter(visitor);
                }
            }
            if (debug) {
                System.out.println(waitingFor.getName() + " starts");
            }
            try {
                Thread.sleep(waitingFor.getTime());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (debug) {
                System.out.println(waitingFor.getName() + " ends");
            }
        }
        if (debug) {
            System.out.println(waitingFor.getName() + " closed");
        }
        waitingLine = new LinkedList<>();
    }

    public int getWaitingLineSize() {
        return waitingLine.size();
    }

    public void setClosingTime(long closingTime) {
        this.closingTime = closingTime;
    }
}
