package model.university;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "kursy")
public class Kurs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(nullable = false, length = 100)
    private String nazwa;

    @NotBlank
    @Size(min = 3, max = 10)
    @Column(nullable = false, length = 10, unique = true)
    private String kodKursu;

    @Min(1)
    @Max(30)
    @Column(nullable = false)
    private int liczbaECTS;

    @OneToMany(
        mappedBy = "kurs",
        fetch = FetchType.LAZY
    )
    private Set<Zapis> zapisy = new HashSet<>();

    protected Kurs() { }

    private Kurs(String nazwa, String kodKursu, int liczbaECTS) {
        this.nazwa = nazwa;
        this.kodKursu = kodKursu;
        this.liczbaECTS = liczbaECTS;
    }

    public static Kurs createKurs(String nazwa, String kodKursu, int liczbaECTS) {
        validateNazwa(nazwa);
        validateKodKursu(kodKursu);
        validateLiczbaECTS(liczbaECTS);
        return new Kurs(nazwa, kodKursu, liczbaECTS);
    }

    void dodajZapis(Zapis zapis) {
        if (zapis == null) throw new IllegalArgumentException("zapis wymagany");
        this.zapisy.add(zapis);
    }

    private static void validateNazwa(String nazwa) {
        if (nazwa == null || nazwa.isBlank()) throw new IllegalArgumentException("nazwa wymagana");
        if (nazwa.length() < 2 || nazwa.length() > 100) throw new IllegalArgumentException("nazwa 2..100");
    }

    private static void validateKodKursu(String kod) {
        if (kod == null || kod.isBlank()) throw new IllegalArgumentException("kodKursu wymagany");
        if (kod.length() < 3 || kod.length() > 10) throw new IllegalArgumentException("kodKursu 3..10");
    }

    private static void validateLiczbaECTS(int ects) {
        if (ects < 1 || ects > 30) throw new IllegalArgumentException("liczbaECTS 1..30");
    }

    public Long getId() { return id; }
    public String getNazwa() { return nazwa; }
    public String getKodKursu() { return kodKursu; }
    public int getLiczbaECTS() { return liczbaECTS; }
    public Set<Zapis> getZapisy() { return Collections.unmodifiableSet(zapisy); }

    @Override
    public String toString() {
        return "Kurs{id=" + id + ", " + kodKursu + " " + nazwa + ", ECTS=" + liczbaECTS + "}";
    }
}
