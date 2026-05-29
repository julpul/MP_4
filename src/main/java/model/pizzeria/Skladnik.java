package model.pizzeria;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "skladniki")
public class Skladnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50)
    private String nazwa;

    @DecimalMin("0.1")
    @DecimalMax("1000.0")
    @Column(nullable = false)
    private double gramatura;

    @Column(nullable = false)
    private boolean czyWegetarianski;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pizza_id", nullable = false)
    private Pizza pizza;

    protected Skladnik() { }

    Skladnik(Pizza pizza, String nazwa, double gramatura, boolean czyWegetarianski) {
        validatePizza(pizza);
        validateNazwa(nazwa);
        validateGramatura(gramatura);
        this.pizza = pizza;
        this.nazwa = nazwa;
        this.gramatura = gramatura;
        this.czyWegetarianski = czyWegetarianski;
    }

    private static void validateNazwa(String nazwa) {
        if (nazwa == null || nazwa.isBlank()) throw new IllegalArgumentException("nazwa wymagana");
        if (nazwa.length() < 2 || nazwa.length() > 50) throw new IllegalArgumentException("nazwa 2..50");
    }

    private static void validateGramatura(double gramatura) {
        if (gramatura < 0.1 || gramatura > 1000.0) throw new IllegalArgumentException("gramatura 0.1..1000");
    }

    private static void validatePizza(Pizza pizza) {
        if (pizza == null) throw new IllegalArgumentException("pizza wymagana (kompozycja)");
    }

    public Long getId() { return id; }
    public String getNazwa() { return nazwa; }
    public double getGramatura() { return gramatura; }
    public boolean isCzyWegetarianski() { return czyWegetarianski; }
    public Pizza getPizza() { return pizza; }

    @Override
    public String toString() {
        return "Skladnik{id=" + id + ", " + nazwa + ", " + gramatura + "g, veg=" + czyWegetarianski + "}";
    }
}
