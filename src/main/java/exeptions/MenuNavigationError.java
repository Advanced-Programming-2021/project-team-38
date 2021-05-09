package exeptions;

import view.messageviewing.PrintError;

public class MenuNavigationError extends Exception {
    public MenuNavigationError() {
        super("menu navigation is not possible");
    }
}
