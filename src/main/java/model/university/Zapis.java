package model.university;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "zapisy",
       uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "kurs_id"}))
public class Zapis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private final UUID uuid = UUID.randomUUID();

    @NotNull
    @Column(nullable = false)
    private LocalDate dataZapisu;

    @DecimalMin("2.0")
    @DecimalMax("5.0")
    @Column(nullable = false)
    private double ocena;

    @Min(0)
    @Max(100)
    @Column(nullable = false)
    private int frekwencja;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kurs_id", nullable = false)
    private Kurs kurs;

    protected Zapis() { }

    private Zapis(Student student, Kurs kurs, LocalDate dataZapisu, double ocena, int frekwencja) {
        this.student = student;
        this.kurs = kurs;
        this.dataZapisu = dataZapisu;
        this.ocena = ocena;
        this.frekwencja = frekwencja;
    }

    public static Zapis createZapis(Student student, Kurs kurs, LocalDate dataZapisu, double ocena, int frekwencja) {
        validateStudent(student);
        validateKurs(kurs);
        validateDataZapisu(dataZapisu);
        validateOcena(ocena);
        validateFrekwencja(frekwencja);
        Zapis z = new Zapis(student, kurs, dataZapisu, ocena, frekwencja);
        student.dodajZapis(z);
        kurs.dodajZapis(z);
        return z;
    }

    private static void validateStudent(Student s) {
        if (s == null) throw new IllegalArgumentException("student wymagany");
    }

    private static void validateKurs(Kurs k) {
        if (k == null) throw new IllegalArgumentException("kurs wymagany");
    }

    private static void validateDataZapisu(LocalDate data) {
        if (data == null) throw new IllegalArgumentException("dataZapisu wymagana");
        if (data.isAfter(LocalDate.now())) throw new IllegalArgumentException("dataZapisu nie moze byc w przyszlosci");
    }

    private static void validateOcena(double ocena) {
        if (ocena < 2.0 || ocena > 5.0) throw new IllegalArgumentException("ocena 2.0..5.0");
    }

    private static void validateFrekwencja(int frekwencja) {
        if (frekwencja < 0 || frekwencja > 100) throw new IllegalArgumentException("frekwencja 0..100");
    }

    public Long getId() { return id; }
    public LocalDate getDataZapisu() { return dataZapisu; }
    public double getOcena() { return ocena; }
    public int getFrekwencja() { return frekwencja; }
    public Student getStudent() { return student; }
    public Kurs getKurs() { return kurs; }

    @Override
    public String toString() {
        return "Zapis{id=" + id + ", student=" + (student != null ? student.getNrIndeksu() : null)
                + ", kurs=" + (kurs != null ? kurs.getKodKursu() : null)
                + ", ocena=" + ocena + ", frekw=" + frekwencja + "%}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zapis zapis = (Zapis) o;
        return Objects.equals(uuid, zapis.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
