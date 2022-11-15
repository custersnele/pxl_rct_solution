package be.pxl.rct.engine;

import be.pxl.rct.command.*;
import be.pxl.rct.engine.task.CreateVisitorsTask;
import be.pxl.rct.engine.task.ThemeparkLogger;
import be.pxl.rct.exception.InvalidCommandException;
import be.pxl.rct.service.ThemeparkService;
import be.pxl.rct.themepark.Themepark;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

public class GameEngine {
    private static final Path PROPERTIES = Path.of("src/main/resources/rct.properties");
    private static final Random RANDOM = new Random();
    private Scanner scanner;
    private Themepark themepark;
    private long oneDayInMillis;
    private double initialCash;
    private ThemeparkService themeparkService = new ThemeparkService();
    private boolean running = true;


    public GameEngine(Scanner scanner) throws IOException {
        this.scanner = scanner;
        //commandEngine.addCommand("load", new LoadThemeparkCommand());
        Properties properties = new Properties();
        properties.load(Files.newInputStream(PROPERTIES));
        initialCash = Double.parseDouble(properties.getProperty("rct.initial_cash"));
        oneDayInMillis = Long.parseLong(properties.getProperty("rct.one_day_millis"));
        themeparkService.init(Path.of(properties.getProperty("rct.rollercoasters")));
    }


    public void start() {
        System.out.println("Type 'help' for information...");
    }


    /**
     * Handles the command given the player is in currentRoom.
     * @param command a command: take, transform, items, north, east, south or west.
     * @return the room where the player is situated after executing the given command.
     */
    public void executeCommand(String command) {
        try {
            String[] data = command.split(" ");
            switch (data[0]) {
                case "create": {
                    CreateThemeparkCommand createThemeparkCommand = new CreateThemeparkCommand(initialCash);
                    createThemeparkCommand.execute(data[1]);
                    Themepark themepark = createThemeparkCommand.getThemepark();
                    // TODO refactor this
                    themeparkService.setThemepark(themepark);
                    this.themepark = themepark;
                    break;
                }
                case "show-types": {
                    ShowAttractionTypesCommand showAttractionTypesCommand = new ShowAttractionTypesCommand(themeparkService, scanner);
                    showAttractionTypesCommand.execute(command);
                    break;
                }
                case "open": {
                    ThemeparkLogger logger = new ThemeparkLogger(Path.of("src/main/resources/logs/log_" + System.currentTimeMillis() + ".log"));
                    CreateVisitorsTask createVisitorsTask = new CreateVisitorsTask(themeparkService.getThemepark(), oneDayInMillis, logger);
                    new Thread(createVisitorsTask).start();
                    break;
                }
                case "show": {
                    ShowThemeparkDetails showThemeparkDetails = new ShowThemeparkDetails(themeparkService);
                    showThemeparkDetails.execute(data[0]);
                    break;
                }
                case "add-rollercoaster": {
                    AddAttractionCommand addAttractionCommand = new AddAttractionCommand(themeparkService, scanner);
                    addAttractionCommand.execute(command);
                    break;
                }
                case "add-shop": {
                    AddShopCommand addShopCommand = new AddShopCommand(themeparkService, scanner);
                    addShopCommand.execute(command);
                    break;
                }
                case "quit": {
                    running = false;
                    break;
                }
            }
        } catch (InvalidCommandException e) {
                System.out.println(e.getMessage());
        }
    }

    public boolean isRunning() {
        // TODO implement quit command
        return running;
    }
}