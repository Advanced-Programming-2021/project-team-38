package view.messageviewing;

import model.card.cardinusematerial.CardInUse;
import view.Print;

public enum Winner {
    AGAINST_AO_WINS("your opponent’s monster is destroyed and your opponent" +
            " receives %d battle damage"),//

    AGAINST_AO_LOSE("Your monster card is destroyed and you received %d battle" +
            " damage"),//

    AGAINST_AO_NONE("both you and your opponent monster cards are destroyed" +
            " and no one receives damage"),//S

    AGAINST_DO_WINS("the defense position monster is destroyed"),//S

    AGAINST_DO_LOSE("no card is destroyed and you received %d battle damage"),//

    AGAINST_DO_NONE("no card is destroyed"),//S

    AGAINST_DH_NONE("opponent’s monster card was %s and no card is " +
            "destroyed"),//T

    AGAINST_DH_LOSE("opponent’s monster card was %s and no card is " +
                            "destroyed and you received %d battle damage"),//M

    AGAINST_DH_WINS("opponent’s monster card was %s and the defense" +
            " position monster is destroyed");//T



    String message;

    Winner(String message){
        this.message = message;
    }

    public static void setWinner(Winner winner, int damage, CardInUse attacker) {
        switch (winner) {
            case AGAINST_AO_WINS:
            case AGAINST_AO_LOSE:
            case AGAINST_DO_LOSE:
                Print.print(String.format(winner.message, damage));
                break;
            case AGAINST_AO_NONE:
            case AGAINST_DO_WINS:
            case AGAINST_DO_NONE:
                Print.print(winner.message);
                break;
            case AGAINST_DH_NONE:
            case AGAINST_DH_WINS:
                Print.print(String.format(
                        winner.message, attacker.getThisCard().getName()));
                break;
            case AGAINST_DH_LOSE:
                Print.print(String.format(winner.message,
                        attacker.getThisCard().getName(), damage));
        }
    }
}
