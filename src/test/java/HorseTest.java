import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HorseTest {

    @Mock
    private Horse horse;

    @Test
    public void horseConstructorNullNameTest(){
        String expectedMessage = "Name cannot be null.";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
        assertEquals(expectedMessage, exception.getMessage());
    }
    @ParameterizedTest
    @ValueSource(strings = {"", "\t", " ", "        "})
    public void horseConstructorEmptyNameTest(String name){
        String expectedMessage = "Name cannot be blank.";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 1));
        assertEquals(expectedMessage, exception.getMessage());
    }
    @Test
    public void horseConstructorSpeedTest(){
        String expectedMessage = "Speed cannot be negative.";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse("SomeName", -1, 1));
        assertEquals(expectedMessage, exception.getMessage());
    }
    @Test
    public void horseConstructorDistanceTest(){
        String expectedMessage = "Distance cannot be negative.";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse("SomeName", 1, -1));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getNameTest() {
        String name = "SomeName";
        horse = new Horse(name, 1, 1);
        assertEquals(name, horse.getName());
    }

    @Test
    void getSpeedTest() {
        int speed = 100;
        horse = new Horse( "SomeName", speed, 1);
        assertEquals(speed, horse.getSpeed());
    }

    @Test
    void getDistanceTest() {
        int distance = 100;
        horse = new Horse( "SomeName", 1, distance);
        assertEquals(distance, horse.getDistance());
        horse = new Horse( "SomeName", 1);
        assertEquals(0, horse.getDistance());
    }

    @Test
    void moveTest() {
        double min = 0.2, max = 0.9, distance = 1.0;
        int speed = 1;
        horse = new Horse("SomeName", speed, distance);
        try(MockedStatic<Horse> mockedHorse = mockStatic(Horse.class)) {
            horse.move();
            mockedHorse.verify(()->Horse.getRandomDouble(min, max), times(1));
        }
    }
    @ParameterizedTest
    @ValueSource(doubles = {0.5, 0.6, 0.7, 0.8})
    void getRandomDoubleTest(double randomResult){
        double min = 0.2, max = 0.9, distance = 1.0, speed = 31;
        horse = new Horse("SomeName", speed, distance);
        try(MockedStatic<Horse> mockedHorse = mockStatic(Horse.class)) {
            mockedHorse.when(()->Horse.getRandomDouble(min, max)).thenReturn(randomResult);
            horse.move();
            Double expectedDistance = distance + speed*randomResult;
            assertEquals(expectedDistance, horse.getDistance());
        }

    }
}