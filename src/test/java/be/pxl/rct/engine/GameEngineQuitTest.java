package be.pxl.rct.engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameEngineQuitTest {

    @Test
    public void quitGameEngineSetRunningToFalse() {
        GameEngine gameEngine = new GameEngine();
        assertTrue(gameEngine.isRunning());
        gameEngine.executeCommand("quit");
        assertFalse(gameEngine.isRunning());
    }
}
