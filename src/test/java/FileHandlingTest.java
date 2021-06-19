import controller.FileHandler;
import model.User;
import org.junit.jupiter.api.Test;

public class FileHandlingTest {
    @Test
    public void createUses() {
        new User("something", "123", "justLikeThis");
        new User("somethingElse", "123", "justLikeThat");
        FileHandler.saveUsers();

    }
}
