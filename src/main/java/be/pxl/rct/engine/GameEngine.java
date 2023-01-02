package be.pxl.rct.engine;

import be.pxl.rct.attraction.RollercoasterType;
import be.pxl.rct.command.*;
import be.pxl.rct.engine.task.CreateVisitorsTask;
import be.pxl.rct.exception.InvalidCommandException;
import be.pxl.rct.shop.ShopType;
import be.pxl.rct.themepark.Themepark;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GameEngine {
    private static final Path PROPERTIES = Path.of("src/main/resources/rct.properties");
    private Themepark themepark;
    private long oneDayInMillis;
    private double initialCash;
    private Path loggingDir;
    private Path savedThemeparksDir;
    private List<RollercoasterType> attractionTypes = new ArrayList<>();
    private boolean running = true;

    public void start() {
        // It is allowed to change the ASCII art
        System.out.println("\n" +
                " .----------------.  .----------------.  .----------------.     .----------------.  .----------------.  .----------------. \n" +
                "| .--------------. || .--------------. || .--------------. |   | .--------------. || .--------------. || .--------------. |\n" +
                "| |   ______     | || |  ____  ____  | || |   _____      | |   | |  _______     | || |     ______   | || |  _________   | |\n" +
                "| |  |_   __ \\   | || | |_  _||_  _| | || |  |_   _|     | |   | | |_   __ \\    | || |   .' ___  |  | || | |  _   _  |  | |\n" +
                "| |    | |__) |  | || |   \\ \\  / /   | || |    | |       | |   | |   | |__) |   | || |  / .'   \\_|  | || | |_/ | | \\_|  | |\n" +
                "| |    |  ___/   | || |    > `' <    | || |    | |   _   | |   | |   |  __ /    | || |  | |         | || |     | |      | |\n" +
                "| |   _| |_      | || |  _/ /'`\\ \\_  | || |   _| |__/ |  | |   | |  _| |  \\ \\_  | || |  \\ `.___.'\\  | || |    _| |_     | |\n" +
                "| |  |_____|     | || | |____||____| | || |  |________|  | |   | | |____| |___| | || |   `._____.'  | || |   |_____|    | |\n" +
                "| |              | || |              | || |              | |   | |              | || |              | || |              | |\n" +
                "| '--------------' || '--------------' || '--------------' |   | '--------------' || '--------------' || '--------------' |\n" +
                " '----------------'  '----------------'  '----------------'     '----------------'  '----------------'  '----------------' \n");
    }

    public void init() {
        Properties properties = new Properties();
        try {
            properties.load(Files.newInputStream(PROPERTIES));
            initialCash = Double.parseDouble(properties.getProperty("rct.initial_cash"));
            oneDayInMillis = Long.parseLong(properties.getProperty("rct.one_day_millis"));
            Path rollercoastersFile = Path.of(properties.getProperty("rct.rollercoasters"));
            loggingDir = Path.of(properties.getProperty("rct.log_folder"));
            savedThemeparksDir = Path.of(properties.getProperty("rct.themeparks_folder"));
            try (BufferedReader reader = Files.newBufferedReader(rollercoastersFile)) {
                String line = reader.readLine(); // ignore first line
                while ((line = reader.readLine()) != null) {
                    RollercoasterType attractionType = RollercoasterMapper.map(line);
                    attractionTypes.add(attractionType);
                }
            }
        } catch (IOException e) {
            System.out.println("Error initializing the game!");
        }
    }

    public void executeCommand(String command) {
        try {
            String[] data = command.split(" ");
            switch (data[0]) {
                case "create": {
                    CreateThemeparkCommand createThemeparkCommand = new CreateThemeparkCommand(initialCash);
                    createThemeparkCommand.execute(data[1]);
                    this.themepark =  createThemeparkCommand.getThemepark();
                    break;
                }
                case "show-types": {
                    ShowAttractionTypesCommand showAttractionTypesCommand = new ShowAttractionTypesCommand(attractionTypes);
                    showAttractionTypesCommand.execute(command);
                    break;
                }
                case "show-shops": {
                    for (ShopType shop : ShopType.values()) {
                        System.out.println(shop.ordinal() + " " + shop.name() + "[" + shop.getItemType() + "]");
                    }
                    break;
                }
                case "open": {
                    CreateVisitorsTask createVisitorsTask = new CreateVisitorsTask(themepark, oneDayInMillis);
                    new Thread(createVisitorsTask).start();
                    break;
                }
                case "describe": {
                    ShowThemeparkDetails showThemeparkDetails = new ShowThemeparkDetails();
                    showThemeparkDetails.execute(themepark);
                    break;
                }
                case "add-rollercoaster": {
                    AddAttractionCommand addAttractionCommand = new AddAttractionCommand(attractionTypes);
                    addAttractionCommand.execute(themepark, command.substring(command.indexOf(" ") + 1).trim());
                    break;
                }
                case "add-shop": {
                    AddShopCommand addShopCommand = new AddShopCommand(themepark);
                    addShopCommand.execute(command);
                    break;
                }
                case "set": {
                    SetCommand setCommand = new SetCommand();
                    setCommand.execute(themepark, command);
                    break;
                }
                case "quit": {
                    running = false;
                    break;
                }
                case "save": {
                    Path filename = savedThemeparksDir.resolve(Path.of(command.substring(command.indexOf(" ") + 1)));
                    try {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(filename));
                        objectOutputStream.writeObject(themepark);
                    } catch (IOException e) {
                        System.out.println("Error while writing file...");
                        e.printStackTrace();
                    }
                    break;
                }
                case "load": {
                    Path filename = savedThemeparksDir.resolve(Path.of(command.substring(command.indexOf(" ") + 1)));
                    try {
                        ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(filename));
                        themepark = (Themepark) objectInputStream.readObject();
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Error while reading file...");
                        e.printStackTrace();
                    }
                    break;
                }
            }
        } catch (InvalidCommandException e) {
            System.out.println(e.getMessage());
        }

    }

    public boolean isRunning() {
        return running;
    }
}