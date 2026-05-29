package model.university;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "studenci")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 6, max = 6)
    @Column(nullable = false, length = 6, unique = true)
    private String nrIndeksu;

    @NotBlank
    @Size(min = 1, max = 50)
    @Column(nullable = false, length = 50)
    private String imie;

    @NotBlank
    @Size(min = 1, max = 50)
    @Column(nullable = false, length = 50)
    private String nazwisko;

    @OneToMany(
        mappedBy = "student",
        fetch = FetchType.LAZY
    )
    private Set<Zapis> zapisy = new HashSet<>();

    protected Student() { }

    private Student(String nrIndeksu, String imie, String nazwisko) {
        this.nrIndeksu = nrIndeksu;
        this.imie = imie;
        this.nazwisko = nazwisko;
    }

    public static Student createStudent(String nrIndeksu, String imie, String nazwisko) {
        validateNrIndeksu(nrIndeksu);
        validateImie(imie);
        validateNazwisko(nazwisko);
        return new Student(nrIndeksu, imie, nazwisko);
    }

    void dodajZapis(Zapis zapis) {
        if (zapis == null) throw new IllegalArgumentException("zapis wymagany");
        this.zapisy.add(zapis);
    }

    private static void validateNrIndeksu(String nr) {
        if (nr == null || nr.length() != 6) throw new IllegalArgumentException("nrIndeksu = 6 znakow");
    }

    private static void validateImie(String imie) {
        if (imie == null || imie.isBlank()) throw new IllegalArgumentException("imie wymagane");
        if (imie.length() > 50) throw new IllegalArgumentException("imie max 50");
    }

    private static void validateNazwisko(String nazwisko) {
        if (nazwisko == null || nazwisko.isBlank()) throw new IllegalArgumentException("nazwisko wymagane");
        if (nazwisko.length() > 50) throw new IllegalArgumentException("nazwisko max 50");
    }

    public Long getId() { return id; }
    public String getNrIndeksu() { return nrIndeksu; }
    public String getImie() { return imie; }
    public String getNazwisko() { return nazwisko; }
    public Set<Zapis> getZapisy() { return Collections.unmodifiableSet(zapisy); }

    @Override
    public String toString() {
        return "Student{id=" + id + ", " + nrIndeksu + " " + imie + " " + nazwisko + "}";
    }
}
