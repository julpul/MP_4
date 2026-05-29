package tests.negative.university;

import model.university.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class StudentNegativeTest {

    @Test
    void nrIndeksu_tooShort_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Student.createStudent("12345", "A", "B"));
    }

    @Test
    void nrIndeksu_tooLong_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Student.createStudent("1234567", "A", "B"));
    }

    @Test
    void imie_blank_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Student.createStudent("123456", "   ", "B"));
    }

    @Test
    void nazwisko_blank_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Student.createStudent("123456", "A", ""));
    }
}
