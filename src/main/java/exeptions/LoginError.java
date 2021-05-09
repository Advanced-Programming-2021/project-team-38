package exeptions;

import view.messageviewing.PrintError;

public class LoginError extends Exception {
    public LoginError() {
        super("Username and password didn't match!");
    }
}
