import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class MainTest {
    @Test
    @Timeout(21)
    @Disabled
    void mainTimeoutTest(){
        try {
            Main.main(new String[]{});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}