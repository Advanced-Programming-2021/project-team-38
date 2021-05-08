import controller.ScoreBoardMenuController;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class test1 {
    @Test
    public void scoreBoardTest() {
        new User("negar", " 123", "bsh");
        new User("mehrdad", " 123", "bsh");
        String scoreBoard = ScoreBoardMenuController.showScoreBoard();
        Assertions.assertEquals(scoreBoard, "1- mehrdad: 0\n1- negar: 0\n");
    }
}
