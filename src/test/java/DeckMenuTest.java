//3 methods of deck menu controller tested

import controller.DeckMenuController;
import exceptions.AlreadyExistingError;
import exceptions.NotExisting;
import model.Deck;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class DeckMenuTest {  //controller

    @Test
    @DisplayName("create deck")
    public void createDeck() throws AlreadyExistingError {
        User user = new User("hasti", "123", "hk");
        DeckMenuController.setUser(user);
        DeckMenuController.createDeck("deck1");
        Assertions.assertNotNull(user.findDeckByName("deck1"));
        Assertions.assertThrows(AlreadyExistingError.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                DeckMenuController.createDeck("deck1");
            }
        });
    }

    @Test
    @DisplayName("delete deck")
    public void deleteDeck() throws AlreadyExistingError, NotExisting {
        User user = new User("hasti", "123", "hk");
        DeckMenuController.setUser(user);
        DeckMenuController.createDeck("deck1");
        Assertions.assertNull(user.findDeckByName("deck2"));
        Assertions.assertThrows(NotExisting.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                DeckMenuController.deleteDeck("deck2");
            }
        });

        DeckMenuController.deleteDeck("deck1");
        Assertions.assertNull(user.findDeckByName("deck1"));
    }

    @Test
    @DisplayName("set active deck")
    public void setDeck() throws AlreadyExistingError, NotExisting {
        User user = new User("hasti", "123", "hk");
        DeckMenuController.setUser(user);
        DeckMenuController.createDeck("deck1");
        Deck deck = user.findDeckByName("deck1");
        DeckMenuController.chooseActiveDeck("deck1");
        Assertions.assertEquals(deck, user.getActiveDeck());
    }
}
