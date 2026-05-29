package tests.negative.publishing;

import model.publishing.Autor;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AutorNegativeTest {

    @Test
    void imie_blank_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Autor.createAutor("   ", "K", LocalDate.of(1900, 1, 1)));
    }

    @Test
    void imie_tooLong_throws() {
        String s = "a".repeat(51);
        assertThrows(IllegalArgumentException.class,
                () -> Autor.createAutor(s, "K", LocalDate.of(1900, 1, 1)));
    }

    @Test
    void nazwisko_blank_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Autor.createAutor("J", "", LocalDate.of(1900, 1, 1)));
    }

    @Test
    void dataUrodzenia_future_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Autor.createAutor("J", "K", LocalDate.now().plusDays(1)));
    }

    @Test
    void dodajKsiazke_null_throws() {
        Autor a = Autor.createAutor("J", "K", LocalDate.of(1900, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> a.dodajKsiazke(null));
    }
}
