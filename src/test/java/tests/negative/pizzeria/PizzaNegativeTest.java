package tests.negative.pizzeria;

import model.pizzeria.Pizza;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PizzaNegativeTest {

    @Test
    void nazwa_blank_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Pizza.createPizza("  ", 20.0, 30));
    }

    @Test
    void nazwa_tooShort_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Pizza.createPizza("A", 20.0, 30));
    }

    @Test
    void cena_zero_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Pizza.createPizza("Pi", 0.0, 30));
    }

    @Test
    void srednica_tooBig_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Pizza.createPizza("Pi", 20.0, 61));
    }
}
