package exeptions;

import view.PrintError;

public class UnableToSummonMonster extends Exception {
    public UnableToSummonMonster() {
        super("you can’t summon this card");
    }
}
