package exeptions;

import view.messageviewing.PrintError;

public class MenuNavigationError {
    public MenuNavigationError() {
        PrintError.print("menu navigation is not possible");
    }
}
