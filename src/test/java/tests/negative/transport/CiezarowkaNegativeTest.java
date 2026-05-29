package tests.negative.transport;

import model.transport.Ciezarowka;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CiezarowkaNegativeTest {

    @Test
    void ladownosc_tooSmall_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Ciezarowka.createCiezarowka("KR99001", 2020, "Volvo", 499, 3));
    }

    @Test
    void ladownosc_tooBig_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Ciezarowka.createCiezarowka("KR99001", 2020, "Volvo", 40001, 3));
    }

    @Test
    void liczbaOsi_tooSmall_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Ciezarowka.createCiezarowka("KR99001", 2020, "Volvo", 1000, 1));
    }

    @Test
    void liczbaOsi_tooBig_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Ciezarowka.createCiezarowka("KR99001", 2020, "Volvo", 1000, 7));
    }
}
