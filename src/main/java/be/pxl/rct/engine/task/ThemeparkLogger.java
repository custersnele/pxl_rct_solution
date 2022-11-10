package be.pxl.rct.engine.task;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ThemeparkLogger {

    private BufferedWriter writer;

    private Path path;

    public ThemeparkLogger(Path path) {
        this.path = path;
    }

    public void log(String s) {

    }
}
