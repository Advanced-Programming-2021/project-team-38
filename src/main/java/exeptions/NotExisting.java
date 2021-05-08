package exeptions;

import view.messageviewing.PrintError;

public class NotExisting {
    public NotExisting(String type, String name) {
        PrintError.print(String.format("%s with name %s does not exist", type, name));
    }
}
