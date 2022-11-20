package be.pxl.rct;

import be.pxl.rct.engine.GameEngine;

import java.io.IOException;
import java.util.Scanner;

public class RollercoasterTycoon {

    public static void main(String[] args) throws IOException {
        Scanner keyboard = new Scanner(System.in);
        GameEngine gameEngine = new GameEngine();
        gameEngine.start();
        while (gameEngine.isRunning()) {
            System.out.print("rct> ");
            gameEngine.executeCommand(keyboard.nextLine());
        }
    }
}
