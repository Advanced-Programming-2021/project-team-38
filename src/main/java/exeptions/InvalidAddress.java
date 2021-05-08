package exeptions;

import view.messageviewing.PrintError;

public class InvalidAddress {
    public InvalidAddress() {
        PrintError.print("there no monsters on this address");
    }
}
