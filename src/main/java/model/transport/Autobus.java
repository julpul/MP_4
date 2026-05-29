package model.transport;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "autobusy")
public class Autobus extends Pojazd {

    @Min(10)
    @Max(120)
    @Column(nullable = false)
    private int liczbaMiejsc;

    @Column(nullable = false)
    private boolean czyPietrowy;

    protected Autobus() { }

    private Autobus(String nrRejestracyjny, int rokProdukcji, String marka,
                    int liczbaMiejsc, boolean czyPietrowy) {
        super(nrRejestracyjny, rokProdukcji, marka);
        this.liczbaMiejsc = liczbaMiejsc;
        this.czyPietrowy = czyPietrowy;
    }

    public static Autobus createAutobus(String nrRejestracyjny, int rokProdukcji, String marka,
                                        int liczbaMiejsc, boolean czyPietrowy) {
        validateLiczbaMiejsc(liczbaMiejsc);
        return new Autobus(nrRejestracyjny, rokProdukcji, marka, liczbaMiejsc, czyPietrowy);
    }

    @Override
    public double obliczRocznyKosztUbezpieczenia() {
        double baza = 1500.0;
        double zaMiejsce = 25.0 * liczbaMiejsc;
        double pietrowyMnoznik = czyPietrowy ? 1.3 : 1.0;
        return (baza + zaMiejsce) * pietrowyMnoznik;
    }

    private static void validateLiczbaMiejsc(int liczba) {
        if (liczba < 10 || liczba > 120) throw new IllegalArgumentException("liczbaMiejsc 10..120");
    }

    public int getLiczbaMiejsc() { return liczbaMiejsc; }
    public boolean isCzyPietrowy() { return czyPietrowy; }

    @Override
    public String toString() {
        return "Autobus{id=" + getId() + ", " + getNrRejestracyjny() + ", " + getMarka()
                + ", miejsc=" + liczbaMiejsc + ", pietrowy=" + czyPietrowy + "}";
    }
}
