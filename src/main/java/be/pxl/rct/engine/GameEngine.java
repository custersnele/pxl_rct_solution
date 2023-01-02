package be.pxl.rct.engine;

import be.pxl.rct.attraction.RollercoasterType;
import be.pxl.rct.command.*;
import be.pxl.rct.exception.InvalidCommandException;
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
    private Path savedThemeparksDir;
    private final List<RollercoasterType> attractionTypes = new ArrayList<>();
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
            String commandDetails = command.substring(command.indexOf(" ") + 1).trim();
            switch (data[0]) {
                case "create": {
                    CreateThemeparkCommand createThemeparkCommand = new CreateThemeparkCommand(initialCash);
                    themepark = createThemeparkCommand.execute(commandDetails);
                    break;
                }
                case "show": {
                    if (commandDetails.contains("-type shop")) {
                        ShowShopTypesCommand showShopTypesCommand = new ShowShopTypesCommand();
                        showShopTypesCommand.execute().forEach(System.out::println);
                    } else if (commandDetails.contains("-type rollercoaster")) {
                        ShowRollercoasterTypesCommand showAttractionTypesCommand = new ShowRollercoasterTypesCommand(attractionTypes);
                        showAttractionTypesCommand.execute(commandDetails).forEach(System.out::println);
                    } else {
                        throw new InvalidCommandException("Invalid command");
                    }
                    break;
                }
                case "open": {
                    boolean debug = commandDetails.contains("-debug");
                    SimulateDayThread simulateDayThread = new SimulateDayThread(themepark, oneDayInMillis, debug);
                    simulateDayThread.start();
                    if (debug) {
                        try {
                            simulateDayThread.join();
                        } catch (InterruptedException e) {
                            System.out.println("SimulateDayThread interrupted.");
                        }
                    }
                    break;
                }
                case "describe": {
                    ShowThemeparkDetails showThemeparkDetails = new ShowThemeparkDetails();
                    showThemeparkDetails.execute(themepark);
                    break;
                }
                case "add-rollercoaster": {
                    AddAttractionCommand addAttractionCommand = new AddAttractionCommand(attractionTypes);
                    addAttractionCommand.execute(themepark, commandDetails);
                    break;
                }
                case "add-shop": {
                    AddShopCommand addShopCommand = new AddShopCommand();
                    addShopCommand.execute(themepark, commandDetails);
                    break;
                }
                case "set": {
                    SetCommand setCommand = new SetCommand();
                    setCommand.execute(themepark, commandDetails);
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
                        throw new InvalidCommandException("Error while writing file...");
                    }
                    break;
                }
                case "load": {
                    Path filename = savedThemeparksDir.resolve(Path.of(command.substring(command.indexOf(" ") + 1)));
                    try {
                        ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(filename));
                        themepark = (Themepark) objectInputStream.readObject();
                    } catch (IOException | ClassNotFoundException e) {
                        throw new InvalidCommandException("Error while reading file...");
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