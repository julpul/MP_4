package model.publishing;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "autorzy")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 50)
    @Column(nullable = false, length = 50)
    private String imie;

    @NotBlank
    @Size(min = 1, max = 50)
    @Column(nullable = false, length = 50)
    private String nazwisko;

    @NotNull
    @Past
    @Column(nullable = false)
    private LocalDate dataUrodzenia;

    @OneToMany(
        mappedBy = "autor",
        fetch = FetchType.LAZY,
        cascade = { CascadeType.PERSIST, CascadeType.MERGE }
    )
    private Set<Ksiazka> ksiazki = new HashSet<>();

    protected Autor() { }

    private Autor(String imie, String nazwisko, LocalDate dataUrodzenia) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.dataUrodzenia = dataUrodzenia;
    }

    public static Autor createAutor(String imie, String nazwisko, LocalDate dataUrodzenia) {
        validateImie(imie);
        validateNazwisko(nazwisko);
        validateDataUrodzenia(dataUrodzenia);
        return new Autor(imie, nazwisko, dataUrodzenia);
    }

    public void dodajKsiazke(Ksiazka ksiazka) {
        if (ksiazka == null) throw new IllegalArgumentException("ksiazka wymagana");

        if (!this.ksiazki.contains(ksiazka)) {
            this.ksiazki.add(ksiazka);
            if (ksiazka.getAutor() != this) {
                ksiazka.zmienAutora(this);
            }
        }
    }

    public void usunKsiazke(Ksiazka ksiazka) {
        if (ksiazka == null) return;

        if (this.ksiazki.contains(ksiazka)) {
            this.ksiazki.remove(ksiazka);
        }
    }

    private static void validateImie(String imie) {
        if (imie == null || imie.isBlank()) throw new IllegalArgumentException("imie wymagane");
        if (imie.length() > 50) throw new IllegalArgumentException("imie max 50 znakow");
    }

    private static void validateNazwisko(String nazwisko) {
        if (nazwisko == null || nazwisko.isBlank()) throw new IllegalArgumentException("nazwisko wymagane");
        if (nazwisko.length() > 50) throw new IllegalArgumentException("nazwisko max 50 znakow");
    }

    private static void validateDataUrodzenia(LocalDate data) {
        if (data == null) throw new IllegalArgumentException("dataUrodzenia wymagana");
        if (!data.isBefore(LocalDate.now())) throw new IllegalArgumentException("dataUrodzenia musi byc w przeszlosci");
    }

    public Long getId() { return id; }
    public String getImie() { return imie; }
    public String getNazwisko() { return nazwisko; }
    public LocalDate getDataUrodzenia() { return dataUrodzenia; }
    public Set<Ksiazka> getKsiazki() { return Collections.unmodifiableSet(ksiazki); }

    @Override
    public String toString() {
        return "Autor{id=" + id + ", " + imie + " " + nazwisko + "}";
    }
}
