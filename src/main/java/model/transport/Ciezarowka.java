package model.transport;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "ciezarowki")
public class Ciezarowka extends Pojazd {

    @Min(500)
    @Max(40000)
    @Column(nullable = false)
    private int ladownoscKg;

    @Min(2)
    @Max(6)
    @Column(nullable = false)
    private int liczbaOsi;

    protected Ciezarowka() { }

    private Ciezarowka(String nrRejestracyjny, int rokProdukcji, String marka,
                       int ladownoscKg, int liczbaOsi) {
        super(nrRejestracyjny, rokProdukcji, marka);
        this.ladownoscKg = ladownoscKg;
        this.liczbaOsi = liczbaOsi;
    }

    public static Ciezarowka createCiezarowka(String nrRejestracyjny, int rokProdukcji, String marka,
                                              int ladownoscKg, int liczbaOsi) {
        validateLadownosc(ladownoscKg);
        validateLiczbaOsi(liczbaOsi);
        return new Ciezarowka(nrRejestracyjny, rokProdukcji, marka, ladownoscKg, liczbaOsi);
    }

    @Override
    public double obliczRocznyKosztUbezpieczenia() {
        double baza = 2000.0;
        double zaTone = 0.15 * ladownoscKg;
        double zaOs = 300.0 * liczbaOsi;
        return baza + zaTone + zaOs;
    }

    private static void validateLadownosc(int ladownosc) {
        if (ladownosc < 500 || ladownosc > 40000) throw new IllegalArgumentException("ladownoscKg 500..40000");
    }

    private static void validateLiczbaOsi(int osie) {
        if (osie < 2 || osie > 6) throw new IllegalArgumentException("liczbaOsi 2..6");
    }

    public int getLadownoscKg() { return ladownoscKg; }
    public int getLiczbaOsi() { return liczbaOsi; }

    @Override
    public String toString() {
        return "Ciezarowka{id=" + getId() + ", " + getNrRejestracyjny() + ", " + getMarka()
                + ", ladownosc=" + ladownoscKg + "kg, osi=" + liczbaOsi + "}";
    }
}
