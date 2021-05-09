package exeptions;

import view.PrintError;

public class InvalidAddress extends Exception {
    public InvalidAddress() {
        super("there no monsters on this address");
    }
}
