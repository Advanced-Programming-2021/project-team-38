package exeptions;

public class UnableToChangePosition extends Exception {
    public UnableToChangePosition() {
        super("you can’t change this card position");
    }
}
