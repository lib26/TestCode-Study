package sample.cafekiosk.unit.beverage;

import org.junit.jupiter.api.Test;
import sample.unit.beverage.Americano;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AmericanoTest {

    @Test
    void getName() {
        Americano americano = new Americano();

        assertEquals(americano.getName(), "아메리카노"); // JUnit
        assertThat(americano.getName()).isEqualTo("아메리카노"); //AssertJ -> 좀 더 명시적
    }
}