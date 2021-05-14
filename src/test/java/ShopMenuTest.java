import controller.ShopMenuController;
import exeptions.InvalidCardName;
import exeptions.NotEnoughMoney;
import exeptions.NotExisting;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class ShopMenuTest {
    @Test
    public void wrongCardName() throws NotExisting, NotEnoughMoney {
        ShopMenuController.checkBuying("wrong name");
        Assertions.assertThrows(InvalidCardName.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                System.out.println("hi"); //todo : what should I do !?
            }
        });
    }
}
