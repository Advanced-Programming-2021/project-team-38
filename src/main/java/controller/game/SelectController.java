package controller.game;


import exceptions.InvalidSelection;
import exceptions.NoCardFound;
import model.CardAddress;
import model.Enums.ZoneName;
import model.Player;
import model.card.Card;
import model.card.CardType;
import model.card.cardinusematerial.CardInUse;
import model.card.monster.Monster;
import model.card.monster.MonsterType;
import model.card.monster.PreMonsterCard;
import view.Menus.DuelMenu;
import view.Print;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class SelectController {
    private final ArrayList<ZoneName> zoneNames;
    private final RoundController roundController;
    private CardType cardType = null;
    private final Player selector;
    int upperLevelBound = 100;
    int lowerLevelBound = 0;
    private ArrayList<MonsterType> monsterTypes;

    public SelectController(ArrayList<ZoneName> zoneNames, RoundController roundController, Player selector) {
        this.zoneNames = zoneNames;
        this.roundController = roundController;
        this.selector = selector;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public void setBounds(int lowerLevelBound, int upperLevelBound) {
        this.lowerLevelBound = lowerLevelBound;
        this.upperLevelBound = upperLevelBound;
    }

    public void setMonsterTypes(MonsterType[] monsterTypes) {
        this.monsterTypes = new ArrayList<>();
        Collections.addAll(this.monsterTypes, monsterTypes);
        if (monsterTypes.length == 0) this.monsterTypes = null;

    }

    //this is the function that should be called if we want to get the card!
    public Card getTheCard() {
        while (true) {
            CardAddress cardAddress = getForcedCardAddress();
            if (cardAddress == null) return null; //means that the process is canceled by user
            Card toReturn;
            try {
                toReturn = getCardByAddress(cardAddress);
                if (toReturn != null) return toReturn;
                else throw new InvalidSelection();
            } catch (InvalidSelection | NoCardFound invalidSelection) {
                DuelMenu.showException(invalidSelection);
            }
        }
    }

    public CardInUse getTheCardInUse() {
        return roundController.findCardsCell(getTheCard());
    }

    private CardAddress getForcedCardAddress() {
        return DuelMenu.forceGetCardAddress();
    }

    private Card getCardByAddress(CardAddress cardAddress) throws InvalidSelection, NoCardFound {
        if (cardAddress == null) return null;
        ZoneName zoneName = cardAddress.getZoneName();
        if (!zoneNames.contains(zoneName)) return null;
        Card toReturn;
        boolean isForOpponent = cardAddress.isForOpponent();
        toReturn = getCardByAddress(cardAddress, zoneName, isForOpponent, selector);
        Print.print("card selected");

        /* checking the errors */
        if (cardType != null) {
            if (!toReturn.getPreCardInGeneral().getCardType().equals(cardType)) throw new InvalidSelection();
            if (cardType.equals(CardType.MONSTER)) {
                Monster returningMonster = (Monster) toReturn;
                PreMonsterCard preMonster = (PreMonsterCard) returningMonster.getPreCardInGeneral();
                if (!isLevelBoundFine(returningMonster)) throw new InvalidSelection();
                if (monsterTypes != null && !monsterTypes.isEmpty() && !monsterTypes.contains(preMonster.getMonsterType()))
                    throw new InvalidSelection();
            }
        }
        return toReturn;
    }

    private Card getCardByAddress(CardAddress cardAddress, ZoneName zoneName, boolean isForOpponent, Player selector) throws InvalidSelection, NoCardFound {
        Card toReturn;
        switch (zoneName) {
            case HAND:
                if (cardAddress.isForOpponent()) throw new InvalidSelection();
                toReturn = selector.getHand().getCardWithNumber(cardAddress.getIndex());
                break;
            case MY_MONSTER_ZONE:
                if (isForOpponent)
                    toReturn = roundController.getMyRival(selector).getBoard().getCardInUse(cardAddress.getIndex(), true).getThisCard();
                else toReturn = selector.getBoard().getCardInUse(cardAddress.getIndex(), true).getThisCard();
                break;
            case MY_SPELL_ZONE:
                if (isForOpponent)
                    toReturn = roundController.getMyRival(selector).getBoard().getCardInUse(cardAddress.getIndex(), false).getThisCard();
                else toReturn = selector.getBoard().getCardInUse(cardAddress.getIndex(), false).getThisCard();
                break;
            case MY_FIELD:
                if (isForOpponent)
                    toReturn = roundController.getMyRival(selector).getBoard().getFieldCard().getThisCard();
                else toReturn = selector.getBoard().getFieldCard().getThisCard();
                break;
            case MY_GRAVEYARD:
                if (isForOpponent)
                    toReturn = roundController.getMyRival(selector).getBoard().getGraveYard().getCard(cardAddress.getIndex());
                else toReturn = selector.getBoard().getGraveYard().getCard(cardAddress.getIndex());
                break;
            default:
                throw new InvalidSelection();
        }
        return toReturn;
    }


    private HashMap<Card, CardAddress> getPossibleChoices() {
        HashMap<Card, CardAddress> possibleChoices = new HashMap<>();
        for (ZoneName zoneName : zoneNames) {
            switch (zoneName) {
                case HAND:
                    getCardAddressesIn(possibleChoices, "--hand ", selector.getHand().getCardsInHand().size());
                    break;
                case MY_MONSTER_ZONE:
                    getCardAddressesIn(possibleChoices, "--monster ", selector.getBoard().getMonsterZone().length);
                    break;
                case MY_SPELL_ZONE:
                    getCardAddressesIn(possibleChoices, "--spell ", selector.getBoard().getSpellTrapZone().length);
                    break;
                case MY_FIELD:
                    getCardAddressesIn(possibleChoices, "--field ", 1);
                    break;
                case MY_GRAVEYARD:
                    //todo  is grave yard supported in cardAddress?
                    getCardAddressesIn(possibleChoices, "--graveYard ", selector.getBoard().getGraveYard().getCardsInGraveYard().size());
                    break;
                case RIVAL_MONSTER_ZONE:
                    getCardAddressesIn(possibleChoices, "--monster --opponent", roundController.getMyRival(selector).getBoard().getMonsterZone().length);
                    break;
                case RIVAL_SPELL_ZONE:
                    getCardAddressesIn(possibleChoices, "--spell --opponent", roundController.getMyRival(selector).getBoard().getSpellTrapZone().length);
                    break;
                case RIVAL_FIELD:
                    getCardAddressesIn(possibleChoices, "--field --opponent ", 1);
                    break;
                case RIVAL_GRAVEYARD:
                    getCardAddressesIn(possibleChoices, "--graveYard --opponent ", roundController.getMyRival(selector).getBoard().getGraveYard().getCardsInGraveYard().size());
                    break;
            }
        }
        handleExtraFeatures(possibleChoices);
        return possibleChoices;
    }

    private void handleExtraFeatures(HashMap<Card, CardAddress> possibleChoices) {
        if (cardType != null) {
            for (Card card : possibleChoices.keySet()) {
                if (!card.getPreCardInGeneral().getCardType().equals(cardType))
                    possibleChoices.remove(card);//todo: fine?
            }
        }
    }

    private boolean isLevelBoundFine(Card card) {
        if (card instanceof Monster) {
            Monster monster = (Monster) card;
            return monster.getLevel() >= lowerLevelBound && monster.getLevel() <= upperLevelBound;
        }
        return false;
    }

    private void getCardAddressesIn(HashMap<Card, CardAddress> possibleChoices, String zoneNameString, int size) {
//        for (int i = 1; i <= size; i++) {
//            try {
//                CardAddress cardAddress = new CardAddress(zoneNameString + i);
//                possibleAddresses.add(cardAddress);
//            } catch (InvalidSelection invalidSelection) {
//                invalidSelection.printStackTrace();
//            }
//        }
    }
}
