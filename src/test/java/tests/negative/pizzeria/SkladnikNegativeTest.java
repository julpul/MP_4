package tests.negative.pizzeria;

import model.pizzeria.Pizza;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class SkladnikNegativeTest {

    private Pizza pizza() {
        return Pizza.createPizza("Pi", 20.0, 30);
    }

    @Test
    void nazwa_blank_throws() {
        Pizza p = pizza();
        assertThrows(IllegalArgumentException.class,
                () -> p.dodajSkladnik("  ", 100.0, true));
    }

    @Test
    void gramatura_tooSmall_throws() {
        Pizza p = pizza();
        assertThrows(IllegalArgumentException.class,
                () -> p.dodajSkladnik("Ser", 0.0, true));
    }

    @Test
    void gramatura_tooHigh_throws() {
        Pizza p = pizza();
        assertThrows(IllegalArgumentException.class,
                () -> p.dodajSkladnik("Ser", 1000.1, true));
    }
}
