package model.card.spelltrap;

import lombok.Getter;
import model.card.Card;
import model.card.CardType;
import model.card.PreCard;
import java.util.ArrayList;

@Getter
public class PreSpellTrapCard extends PreCard {
    private CardStatus status;  //limit
    private CardIcon icon;


    public PreSpellTrapCard(String[] cardData) {
        //Name, Type, Icon (Property), Description, Status, Price
        name = cardData[0];
        cardType = CardType.valueOf(cardData[1].toUpperCase());
        icon = CardIcon.getEnum(cardData[2]);
        description = cardData[3];
        status = CardStatus.getEnum(cardData[4]);
        price = Integer.parseInt(cardData[5]);

        allPreCardsInstances.put(this, null);
    }

    @Override
    public Card newCard(String name) {
        return null;
    }
}
