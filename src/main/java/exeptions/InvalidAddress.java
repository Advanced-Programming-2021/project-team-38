package exeptions;

import view.PrintError;

public class InvalidAddress {
    public InvalidAddress() {
        PrintError.print("there no monsters on this address");
    }
}
