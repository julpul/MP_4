package tests.positive.publishing;

import model.publishing.Autor;
import model.publishing.Ksiazka;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AutorPositiveTest {

    @Test
    void createAutor_setsFields() {
        Autor a = Autor.createAutor("Adam", "Mickiewicz", LocalDate.of(1798, 12, 24));
        assertEquals("Adam", a.getImie());
        assertEquals("Mickiewicz", a.getNazwisko());
        assertEquals(LocalDate.of(1798, 12, 24), a.getDataUrodzenia());
        assertTrue(a.getKsiazki().isEmpty());
    }

    @Test
    void dodajKsiazke_addsBidirectional() {
        Autor a = Autor.createAutor("Stanislaw", "Lem", LocalDate.of(1921, 9, 13));
        Ksiazka k = Ksiazka.createKsiazka("Solaris", 280, 1961, a);
        assertTrue(a.getKsiazki().contains(k));
        assertSame(a, k.getAutor());
    }

    @Test
    void usunKsiazke_removesFromSet() {
        Autor a = Autor.createAutor("A", "B", LocalDate.of(1900, 1, 1));
        Ksiazka k = Ksiazka.createKsiazka("T", 100, 2000, a);
        a.usunKsiazke(k);
        assertFalse(a.getKsiazki().contains(k));
    }

    @Test
    void getKsiazki_immutable() {
        Autor a = Autor.createAutor("A", "B", LocalDate.of(1900, 1, 1));
        Set<Ksiazka> s = a.getKsiazki();
        assertThrows(UnsupportedOperationException.class, () -> s.add(null));
    }
}
