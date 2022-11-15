package be.pxl.rct.command;

@FunctionalInterface
public interface OtherCommand<T, D> {
    void execute(T item, D data);
}
