package tests.positive.university;

import model.university.Kurs;
import model.university.Student;
import model.university.Zapis;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ZapisPositiveTest {

    private Student student(String nr) {
        return Student.createStudent(nr, "Anna", "K");
    }

    private Kurs kurs(String kod) {
        return Kurs.createKurs("Nazwa", kod, 5);
    }

    @Test
    void createZapis_bindsBothSides() {
        Student s = student("100001");
        Kurs k = kurs("BD01");
        Zapis z = Zapis.createZapis(s, k, LocalDate.of(2026, 2, 20), 4.0, 80);

        assertSame(s, z.getStudent());
        assertSame(k, z.getKurs());
        assertTrue(s.getZapisy().contains(z));
        assertTrue(k.getZapisy().contains(z));
    }

    @Test
    void equals_differentInstance_falseEvenSameData() {
        Student s = student("100001");
        Kurs k = kurs("KOD");
        LocalDate d = LocalDate.of(2026, 1, 1);
        Zapis a = Zapis.createZapis(s, k, d, 3.0, 50);
        Zapis b = Zapis.createZapis(student("100002"), kurs("KOD2"), d, 3.0, 50);
        assertNotEquals(a, b);
    }

    @Test
    void set_holdsTwoDifferentZapisy() {
        Set<Zapis> set = new HashSet<>();
        Zapis a = Zapis.createZapis(student("100001"), kurs("KO1"), LocalDate.of(2026, 1, 1), 3.0, 50);
        Zapis b = Zapis.createZapis(student("100002"), kurs("KO2"), LocalDate.of(2026, 1, 1), 3.0, 50);
        set.add(a);
        set.add(b);
        assertEquals(2, set.size());
    }

    @Test
    void boundary_ocena_min_max() {
        Zapis a = Zapis.createZapis(student("100001"), kurs("KO1"), LocalDate.of(2026, 1, 1), 2.0, 0);
        Zapis b = Zapis.createZapis(student("100002"), kurs("KO2"), LocalDate.of(2026, 1, 1), 5.0, 100);
        assertEquals(2.0, a.getOcena());
        assertEquals(5.0, b.getOcena());
    }
}
