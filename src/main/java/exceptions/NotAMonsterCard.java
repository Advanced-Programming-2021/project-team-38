package exceptions;

public class NotAMonsterCard extends Exception {
    public NotAMonsterCard() {
        super("only monster cards can be selected!");
    }
}
