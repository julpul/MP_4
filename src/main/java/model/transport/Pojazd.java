package model.transport;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "pojazdy")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pojazd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 4, max = 8)
    @Column(nullable = false, length = 8, unique = true)
    private String nrRejestracyjny;

    @Min(1950)
    @Max(2026)
    @Column(nullable = false)
    private int rokProdukcji;

    @NotBlank
    @Size(min = 2, max = 40)
    @Column(nullable = false, length = 40)
    private String marka;

    protected Pojazd() { }

    protected Pojazd(String nrRejestracyjny, int rokProdukcji, String marka) {
        validateNrRejestracyjny(nrRejestracyjny);
        validateRokProdukcji(rokProdukcji);
        validateMarka(marka);
        this.nrRejestracyjny = nrRejestracyjny;
        this.rokProdukcji = rokProdukcji;
        this.marka = marka;
    }

    public abstract double obliczRocznyKosztUbezpieczenia();

    protected static void validateNrRejestracyjny(String nr) {
        if (nr == null || nr.isBlank()) throw new IllegalArgumentException("nrRejestracyjny wymagany");
        if (nr.length() < 4 || nr.length() > 8) throw new IllegalArgumentException("nrRejestracyjny 4..8");
    }

    protected static void validateRokProdukcji(int rok) {
        if (rok < 1950 || rok > 2026) throw new IllegalArgumentException("rokProdukcji 1950..2026");
    }

    protected static void validateMarka(String marka) {
        if (marka == null || marka.isBlank()) throw new IllegalArgumentException("marka wymagana");
        if (marka.length() < 2 || marka.length() > 40) throw new IllegalArgumentException("marka 2..40");
    }

    public Long getId() { return id; }
    public String getNrRejestracyjny() { return nrRejestracyjny; }
    public int getRokProdukcji() { return rokProdukcji; }
    public String getMarka() { return marka; }
}
