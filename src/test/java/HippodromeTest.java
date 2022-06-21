import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {
    @Test
    void hippodromeConstructorTest(){
        String expected = "Horses cannot be null.";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals(expected, exception.getMessage());
        expected = "Horses cannot be empty.";
        exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void getHorses() {
        int countOfHorses = 30;
        List<Horse> myHorsesList = generateListOfHorses(countOfHorses);
        Hippodrome hippodrome = new Hippodrome(myHorsesList);
        List<Horse> hippodromeHorses = hippodrome.getHorses();
        for (int i = 0; i < myHorsesList.size(); i++) {
            assertEquals(myHorsesList.get(i), hippodromeHorses.get(i));
        }
        //assertSame(myHorsesList, hippodrome.getHorses());
    }


    @Test
    void move() {
        int countOfHorses = 50;
        List<Horse> myHorsesList = generateListOfHorses(countOfHorses);
        Hippodrome hippodrome = new Hippodrome(myHorsesList);
        hippodrome.move();
        hippodrome.getHorses().forEach((horse -> Mockito.verify(horse).move()));
    }

    @Test
    void getWinner() {
        int countOfHorses = 20;
        List<Horse> myHorsesList = generateListOfHorses(countOfHorses);
        Hippodrome hippodrome = new Hippodrome(myHorsesList);
        Double maxDistance = myHorsesList.stream().map(Horse::getDistance).max(Double::compareTo).get();
        assertEquals(maxDistance, hippodrome.getWinner().getDistance());
    }


    private List<Horse> generateListOfHorses(int count) {
        List<Horse> horseList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            horseList.add(Mockito.spy(new Horse(generateRandomName(8),(int)(Math.random()*10), Math.random()*10)));
        }
        return horseList;
    }

    private String generateRandomName(int count) {
        String pattern = "абвгдеёжзийклмнопрстуфхцчьъэюя";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(pattern.charAt((int)(Math.random() * pattern.length())));
        }
        return sb.toString();
    }
}