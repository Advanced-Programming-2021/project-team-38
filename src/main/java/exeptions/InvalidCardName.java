package exeptions;

import view.PrintError;

public class InvalidCardName {
    public InvalidCardName() {
        PrintError.print("there is no card with this name");
    }
}
