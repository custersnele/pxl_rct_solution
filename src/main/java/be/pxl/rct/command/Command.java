package be.pxl.rct.command;

@FunctionalInterface
public interface Command<T> {
    void execute(T data);
}
