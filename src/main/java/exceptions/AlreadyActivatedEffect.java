package exceptions;

public class AlreadyActivatedEffect extends Exception {
    public AlreadyActivatedEffect() {
        super("you have already activated this card");
    }
}
