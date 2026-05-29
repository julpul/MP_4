package tests.positive.pizzeria;

import model.pizzeria.Pizza;
import model.pizzeria.Skladnik;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkladnikPositiveTest {

    @Test
    void create_viaPizza_setsFields() {
        Pizza p = Pizza.createPizza("Pi", 20.0, 30);
        Skladnik s = p.dodajSkladnik("Mozzarella", 120.0, true);
        assertEquals("Mozzarella", s.getNazwa());
        assertEquals(120.0, s.getGramatura());
        assertTrue(s.isCzyWegetarianski());
        assertSame(p, s.getPizza());
    }
}
