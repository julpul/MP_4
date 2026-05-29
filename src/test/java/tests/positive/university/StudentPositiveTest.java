package tests.positive.university;

import model.university.Student;
import model.university.Zapis;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StudentPositiveTest {

    @Test
    void createStudent_setsFields() {
        Student s = Student.createStudent("123456", "Anna", "Kowalska");
        assertEquals("123456", s.getNrIndeksu());
        assertEquals("Anna", s.getImie());
        assertEquals("Kowalska", s.getNazwisko());
        assertTrue(s.getZapisy().isEmpty());
    }

    @Test
    void getZapisy_immutable() {
        Student s = Student.createStudent("123456", "Anna", "Kowalska");
        Set<Zapis> set = s.getZapisy();
        assertThrows(UnsupportedOperationException.class, () -> set.add(null));
    }
}
