package exeptions;

import view.messageviewing.PrintError;

public class InvalidCommand {
    public InvalidCommand() {
        PrintError.print("invalid command");
    }
}
