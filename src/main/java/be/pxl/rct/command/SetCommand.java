package be.pxl.rct.command;

import be.pxl.rct.themepark.Themepark;

public class SetCommand implements OtherCommand<Themepark, String> {
    @Override
    public void execute(Themepark themepark, String data) {
        String[] setDetails = data.split(" ");
        if (setDetails[1].equals("entrance-fee")) {
            themepark.setEntranceFee(Double.parseDouble(setDetails[2]));
        }
        // TODO error handling
    }
}
