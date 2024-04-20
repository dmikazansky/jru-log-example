import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
@ExtendWith(MockitoExtension.class)
class HippodromeTest {

    @ParameterizedTest
    @EmptySource
    void hippodromeEmptyConstructor(List<Horse> horses) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }
    @ParameterizedTest
    @NullSource
    void hippodromeNullConstructor(List<Horse> horses) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }


    @Test
    void getHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse(String.valueOf(i), (double) i * 0.1));
        }
        Hippodrome h = new Hippodrome(horses);
        assertEquals(horses, h.getHorses());
    }
    @Test
    void move() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.spy(new Horse(String.valueOf(i), (double) i * 0.1)));
        }
        Hippodrome h = new Hippodrome(horses);
        h.move();
        for (Horse horse : horses) {
            Mockito.verify(horse, times(1)).move();
        }
    }

    @Test
    void getWinner() {
        List<Horse> horses = new ArrayList<>(List.of(new Horse("Sasha", 2, 3),
                new Horse("Masha", 3, 4),
                new Horse("Arkasha", 4, 5)));
        Horse horse = new Horse("Ivan", 20, 30);
        horses.add(horse);
        Hippodrome h = new Hippodrome(horses);
        assertEquals(horse, h.getWinner());
    }
}