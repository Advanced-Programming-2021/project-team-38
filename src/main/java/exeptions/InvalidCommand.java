package exeptions;

import view.PrintError;

public class InvalidCommand {
    public InvalidCommand() {
        PrintError.print("invalid command");
    }
}
