package tests.positive.publishing;

import model.publishing.Autor;
import model.publishing.Ksiazka;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class KsiazkaPositiveTest {

    private Autor autor() {
        return Autor.createAutor("Jan", "Kowalski", LocalDate.of(1900, 1, 1));
    }

    @Test
    void createKsiazka_setsFields() {
        Autor a = autor();
        Ksiazka k = Ksiazka.createKsiazka("Tytul", 100, 2000, a);
        assertEquals("Tytul", k.getTytul());
        assertEquals(100, k.getLiczbaStron());
        assertEquals(2000, k.getRokWydania());
        assertSame(a, k.getAutor());
    }

    @Test
    void zmienAutora_movesBookBetweenAutors() {
        Autor stary = autor();
        Autor nowy = Autor.createAutor("Adam", "Mickiewicz", LocalDate.of(1798, 12, 24));
        Ksiazka k = Ksiazka.createKsiazka("T", 100, 2000, stary);

        k.zmienAutora(nowy);

        assertSame(nowy, k.getAutor());
        assertTrue(nowy.getKsiazki().contains(k));
        assertFalse(stary.getKsiazki().contains(k));
    }

    @Test
    void boundary_rokWydania_max_liczbaStron_max() {
        Autor a = autor();
        Ksiazka k = Ksiazka.createKsiazka("T", 20000, 2026, a);
        assertEquals(2026, k.getRokWydania());
        assertEquals(20000, k.getLiczbaStron());
    }
}
