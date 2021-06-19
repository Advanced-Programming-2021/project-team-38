package model;

import exceptions.InvalidSelection;
import lombok.Getter;
import model.Enums.ZoneName;
import model.card.cardinusematerial.MonsterCardInUse;
import view.Menu;

import java.util.regex.Matcher;

@Getter
public class CardAddress {
    ZoneName zoneName;
    int index; //index is from 1 to the size of the thing! (monster zone, spell zone, graveyard, hand, deck)
    boolean isForOpponent;

    public CardAddress(String address) throws InvalidSelection {
        address = address.concat(" ");
        isForOpponent = false;
        Matcher flagMatcher = Menu.getCommandMatcher(address, "--(<field>\\S+) ");
        while (flagMatcher.find()) {
            String field = flagMatcher.group("field");
            if (field.equals("opponent")) {
                if (!isForOpponent) isForOpponent = true;
                else throw new InvalidSelection();
            } else {
                if (zoneName != null) throw new InvalidSelection();
                zoneName = ZoneName.getZoneName(field, isForOpponent);
            }
        }
        if (zoneName == null) throw new InvalidSelection();
        try {
            index = Integer.parseInt(address);
        } catch (Exception e) {
            if (!zoneName.equals(ZoneName.MY_FIELD))
                throw new InvalidSelection();
            index = -1;
        }
    }

    public MonsterCardInUse getMonsterCardInUseInAddress(MonsterCardInUse[] monstersInBoard) throws InvalidSelection {
        if (zoneName != ZoneName.MY_MONSTER_ZONE) throw new InvalidSelection();
        if (index < 1 || index > 5) throw new InvalidSelection();
        return monstersInBoard[index - 1];
    }

    @Override
    public String toString() {
        String toReturn = "--" + zoneName.toString();
        if (index != -1 && index != 0) toReturn = toReturn + " " + index;
        if (isForOpponent) toReturn = toReturn + " --opponent";
        return toReturn;
    }
}
