package tests.negative.publishing;

import model.publishing.Autor;
import model.publishing.Ksiazka;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class KsiazkaNegativeTest {

    private Autor autor() {
        return Autor.createAutor("J", "K", LocalDate.of(1900, 1, 1));
    }

    @Test
    void tytul_blank_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Ksiazka.createKsiazka("   ", 100, 2000, autor()));
    }

    @Test
    void liczbaStron_zero_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Ksiazka.createKsiazka("T", 0, 2000, autor()));
    }

    @Test
    void rokWydania_tooLate_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Ksiazka.createKsiazka("T", 100, 2027, autor()));
    }

    @Test
    void autor_null_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Ksiazka.createKsiazka("T", 100, 2000, null));
    }

    @Test
    void zmienAutora_null_throws() {
        Ksiazka k = Ksiazka.createKsiazka("T", 100, 2000, autor());
        assertThrows(IllegalArgumentException.class, () -> k.zmienAutora(null));
    }
}
