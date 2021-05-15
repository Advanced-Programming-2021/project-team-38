package exceptions;

public class InvalidAddress extends Exception {
    public InvalidAddress() {
        super("there no monsters on this address");
    }
}
