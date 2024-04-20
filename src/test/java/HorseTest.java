import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

class HorseTest {

    @Test
    void constructorFirstParamNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 3d));
        assertEquals("Name cannot be null.", exception.getMessage());
    }
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  " })
    void constructorFirstParamSpace(String str) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse(str, 3d));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }
    @Test
    void constructorTwoParam() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse("Sasha", -1d));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }
    @Test
    void constructorSecondParam() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse("Sasha", 1d, -1d));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void getName() {
        String name = "Sasha";
        Horse horse = new Horse(name, 3d);
        assertEquals(name, horse.getName());
    }

    @Test
    void getSpeed() {
        double speed = 1d;
        Horse horse = new Horse("Sasha", speed);
        assertEquals(speed, horse.getSpeed());
    }

    @Test
    void getDistanceWithParam() {
        double distance = 1d;
        Horse horse = new Horse("Sasha", 5d, distance);
        assertEquals(distance, horse.getDistance());
    }
    @Test
    void getDistance() {
        Horse horse = new Horse("Sasha", 5d);
        assertEquals(0, horse.getDistance());
    }

    @Test
    void testMoveRandomInside() {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.8);
            Horse horse = new Horse("Sasha", 5d);
            horse.move();
            mockedStatic.verify(
                    () -> Horse.getRandomDouble(0.2, 0.9),
                    times(1));
        }
    }
    @ParameterizedTest
    @ValueSource(doubles = {0.3, 0.4, 0.5})
    void testMoveSumDistance(double random) {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);
            Horse horse = new Horse("Sasha", 5d);
            double result = horse.getDistance() + horse.getSpeed() * Horse.getRandomDouble(0.2, 0.9);
            horse.move();
            assertEquals(result, horse.getDistance());
        }
    }

}