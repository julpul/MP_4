package tests.positive.university;

import model.university.Kurs;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KursPositiveTest {

    @Test
    void createKurs_setsFields() {
        Kurs k = Kurs.createKurs("Bazy Danych", "BD02", 5);
        assertEquals("Bazy Danych", k.getNazwa());
        assertEquals("BD02", k.getKodKursu());
        assertEquals(5, k.getLiczbaECTS());
        assertTrue(k.getZapisy().isEmpty());
    }

    @Test
    void boundary_ects_min_max() {
        Kurs a = Kurs.createKurs("Nazwa", "KO1", 1);
        Kurs b = Kurs.createKurs("Nazwa", "KO2", 30);
        assertEquals(1, a.getLiczbaECTS());
        assertEquals(30, b.getLiczbaECTS());
    }
}
