package be.pxl.rct.command;

import be.pxl.rct.exception.InvalidCommandException;
import be.pxl.rct.themepark.Themepark;

public class SetCommand {

    public void execute(Themepark themepark, String data) {
        String[] setDetails = data.split(" ");
        if (setDetails[1].equals("entrance-fee")) {
            try {
                themepark.setEntranceFee(Double.parseDouble(setDetails[2]));
            } catch (NumberFormatException e) {
                throw new InvalidCommandException("You should provide the entrance-fee.");
            }
        } else {
            throw new InvalidCommandException("That's not a valid command.");
        }
    }
}
