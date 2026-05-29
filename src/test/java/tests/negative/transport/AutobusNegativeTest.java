package tests.negative.transport;

import model.transport.Autobus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AutobusNegativeTest {

    @Test
    void liczbaMiejsc_tooSmall_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Autobus.createAutobus("AB12", 2000, "MAN", 9, false));
    }

    @Test
    void liczbaMiejsc_tooBig_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Autobus.createAutobus("AB12", 2000, "MAN", 121, false));
    }

    @Test
    void nrRej_tooShort_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Autobus.createAutobus("AB1", 2000, "MAN", 50, false));
    }

    @Test
    void rok_tooEarly_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Autobus.createAutobus("WX12345", 1949, "MAN", 50, false));
    }

    @Test
    void marka_blank_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Autobus.createAutobus("AB12", 2000, "  ", 50, false));
    }
}
