package exeptions;

import view.messageviewing.PrintError;

public class InvalidCardName {
    public InvalidCardName() {
        PrintError.print("there is no card with this name");
    }
}
