package tests.positive.transport;

import model.transport.Autobus;
import model.transport.Pojazd;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AutobusPositiveTest {

    @Test
    void createAutobus_setsFields() {
        Autobus a = Autobus.createAutobus("WAW1234", 2022, "Solaris", 60, false);
        assertEquals("WAW1234", a.getNrRejestracyjny());
        assertEquals(2022, a.getRokProdukcji());
        assertEquals("Solaris", a.getMarka());
        assertEquals(60, a.getLiczbaMiejsc());
        assertFalse(a.isCzyPietrowy());
        assertTrue(a instanceof Pojazd);
    }

    @Test
    void kosztUbezpieczenia_niePietrowy() {
        Autobus a = Autobus.createAutobus("WAW1234", 2022, "Solaris", 60, false);
        // 1500 + 25*60 = 3000
        assertEquals(3000.0, a.obliczRocznyKosztUbezpieczenia(), 0.0001);
    }

    @Test
    void kosztUbezpieczenia_pietrowy() {
        Autobus a = Autobus.createAutobus("WAW1234", 2022, "MAN", 80, true);
        // (1500 + 25*80) * 1.3 = 4550
        assertEquals(4550.0, a.obliczRocznyKosztUbezpieczenia(), 0.0001);
    }
}
