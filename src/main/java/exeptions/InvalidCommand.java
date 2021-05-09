package exeptions;

import view.PrintError;

public class InvalidCommand extends Exception {
    public InvalidCommand() {
        super("invalid command");
    }
}
