package tests.negative.university;

import model.university.Kurs;
import model.university.Student;
import model.university.Zapis;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ZapisNegativeTest {

    private Student student() {
        return Student.createStudent("123456", "A", "B");
    }

    private Kurs kurs() {
        return Kurs.createKurs("Nazwa", "KOD", 5);
    }

    @Test
    void student_null_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Zapis.createZapis(null, kurs(), LocalDate.of(2026, 1, 1), 4.0, 80));
    }

    @Test
    void data_future_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Zapis.createZapis(student(), kurs(), LocalDate.now().plusDays(1), 4.0, 80));
    }

    @Test
    void ocena_aboveMax_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Zapis.createZapis(student(), kurs(), LocalDate.of(2026, 1, 1), 5.01, 80));
    }

    @Test
    void frekwencja_above100_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Zapis.createZapis(student(), kurs(), LocalDate.of(2026, 1, 1), 4.0, 101));
    }
}
