package exeptions;

import view.messageviewing.PrintError;

public class InvalidCommand extends Exception {
    public InvalidCommand() {
        super("invalid command");
    }
}
