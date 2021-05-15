package exeptions;

public class UnableToSummonMonster extends Exception {
    public UnableToSummonMonster(String flipOrSpace) {
        super("you can’t summon" + flipOrSpace + " this card");
    }
}
