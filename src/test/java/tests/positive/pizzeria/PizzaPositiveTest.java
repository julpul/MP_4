package tests.positive.pizzeria;

import model.pizzeria.Pizza;
import model.pizzeria.Skladnik;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PizzaPositiveTest {

    @Test
    void createPizza_setsFields() {
        Pizza p = Pizza.createPizza("Margherita", 28.00, 32);
        assertEquals("Margherita", p.getNazwa());
        assertEquals(28.00, p.getCena());
        assertEquals(32, p.getSrednicaCm());
        assertTrue(p.getSkladniki().isEmpty());
    }

    @Test
    void dodajSkladnik_addsAndBindsBack() {
        Pizza p = Pizza.createPizza("Pi", 20.0, 30);
        Skladnik s = p.dodajSkladnik("Ser", 100.0, true);
        assertSame(p, s.getPizza());
        assertEquals(1, p.getSkladniki().size());
    }

    @Test
    void usunSkladnik_removes() {
        Pizza p = Pizza.createPizza("Pi", 20.0, 30);
        Skladnik s = p.dodajSkladnik("Ser", 100.0, true);
        p.usunSkladnik(s);
        assertTrue(p.getSkladniki().isEmpty());
    }

    @Test
    void getSkladniki_immutable() {
        Pizza p = Pizza.createPizza("Pi", 20.0, 30);
        List<Skladnik> list = p.getSkladniki();
        assertThrows(UnsupportedOperationException.class, () -> list.add(null));
    }
}
