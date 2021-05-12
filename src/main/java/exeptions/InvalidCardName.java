package exeptions;

public class InvalidCardName extends Exception {
    public InvalidCardName() {
        super("there is no card with this name");
    }
}
