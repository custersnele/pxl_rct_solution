package be.pxl.rct.attraction;

public interface QueueArea<Q> {
    /**
     * Number of items of type Q that are allowed at the same time in the area.
     * @return the number of items allowed at the same time
     */
    int getAllowed();

    /**
     * The time that the items are allowed.
     * @return the time in milliseconds
     */
    int getTime();

    /**
     * Enter the area you were in line for.
     */
    void enter(Q guest);

    String getName();
}
