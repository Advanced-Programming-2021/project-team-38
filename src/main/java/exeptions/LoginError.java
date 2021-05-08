package exeptions;

import view.messageviewing.PrintError;

public class LoginError {
    public LoginError() {
        PrintError.print("Username and password didn't match!");
    }
}
