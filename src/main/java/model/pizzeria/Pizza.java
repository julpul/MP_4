package model.pizzeria;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "pizze")
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50)
    private String nazwa;

    @DecimalMin("0.01")
    @DecimalMax("999.99")
    @Column(nullable = false)
    private double cena;

    @Min(20)
    @Max(60)
    @Column(nullable = false)
    private int srednicaCm;

    @OneToMany(
        mappedBy = "pizza",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private List<Skladnik> skladniki = new ArrayList<>();

    protected Pizza() { }

    private Pizza(String nazwa, double cena, int srednicaCm) {
        this.nazwa = nazwa;
        this.cena = cena;
        this.srednicaCm = srednicaCm;
    }

    public static Pizza createPizza(String nazwa, double cena, int srednicaCm) {
        validateNazwa(nazwa);
        validateCena(cena);
        validateSrednica(srednicaCm);
        return new Pizza(nazwa, cena, srednicaCm);
    }

    public Skladnik dodajSkladnik(String nazwa, double gramatura, boolean czyWegetarianski) {
        Skladnik s = new Skladnik(this, nazwa, gramatura, czyWegetarianski);
        this.skladniki.add(s);
        return s;
    }

    public void usunSkladnik(Skladnik skladnik) {
        if (skladnik == null) return;
        this.skladniki.remove(skladnik);
    }

    private static void validateNazwa(String nazwa) {
        if (nazwa == null || nazwa.isBlank()) throw new IllegalArgumentException("nazwa wymagana");
        if (nazwa.length() < 2 || nazwa.length() > 50) throw new IllegalArgumentException("nazwa 2..50");
    }

    private static void validateCena(double cena) {
        if (cena < 0.01 || cena > 999.99) throw new IllegalArgumentException("cena 0.01..999.99");
    }

    private static void validateSrednica(int srednica) {
        if (srednica < 20 || srednica > 60) throw new IllegalArgumentException("srednicaCm 20..60");
    }

    public Long getId() { return id; }
    public String getNazwa() { return nazwa; }
    public double getCena() { return cena; }
    public int getSrednicaCm() { return srednicaCm; }
    public List<Skladnik> getSkladniki() { return Collections.unmodifiableList(skladniki); }

    @Override
    public String toString() {
        return "Pizza{id=" + id + ", " + nazwa + ", " + cena + "zl, " + srednicaCm + "cm}";
    }
}
