package exeptions;

import view.messageviewing.PrintError;

public class AlreadyExistingError {

    public AlreadyExistingError(String type, String subType, String name) {
        PrintError.print(String.format("%s with %s %s already exists", type, subType, name));
    }
}
