package tests.positive.transport;

import model.transport.Ciezarowka;
import model.transport.Pojazd;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CiezarowkaPositiveTest {

    @Test
    void createCiezarowka_setsFields() {
        Ciezarowka c = Ciezarowka.createCiezarowka("KR99001", 2020, "Volvo", 18000, 3);
        assertEquals("KR99001", c.getNrRejestracyjny());
        assertEquals("Volvo", c.getMarka());
        assertEquals(18000, c.getLadownoscKg());
        assertEquals(3, c.getLiczbaOsi());
        assertTrue(c instanceof Pojazd);
    }

    @Test
    void kosztUbezpieczenia_formula() {
        Ciezarowka c = Ciezarowka.createCiezarowka("KR99001", 2020, "Volvo", 18000, 3);
        // 2000 + 0.15*18000 + 300*3 = 5600
        assertEquals(5600.0, c.obliczRocznyKosztUbezpieczenia(), 0.0001);
    }
}
