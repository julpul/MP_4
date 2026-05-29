package tests.negative.university;

import model.university.Kurs;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class KursNegativeTest {

    @Test
    void nazwa_blank_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Kurs.createKurs("  ", "KOD", 5));
    }

    @Test
    void nazwa_tooShort_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Kurs.createKurs("A", "KOD", 5));
    }

    @Test
    void kod_tooShort_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Kurs.createKurs("Nazwa", "AB", 5));
    }

    @Test
    void kod_tooLong_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Kurs.createKurs("Nazwa", "ABCDEFGHIJK", 5));
    }

    @Test
    void ects_zero_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Kurs.createKurs("Nazwa", "KOD", 0));
    }

    @Test
    void ects_tooHigh_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Kurs.createKurs("Nazwa", "KOD", 31));
    }
}
