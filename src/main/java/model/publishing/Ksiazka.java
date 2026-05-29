package model.publishing;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "ksiazki")
public class Ksiazka {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 200)
    @Column(nullable = false, length = 200)
    private String tytul;



    @Min(1)
    @Max(20000)
    @Column(nullable = false)
    private int liczbaStron;

    @Min(1500)
    @Max(2026)
    @Column(nullable = false)
    private int rokWydania;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    protected Ksiazka() { }

    private Ksiazka(String tytul, int liczbaStron, int rokWydania, Autor autor) {
        this.tytul = tytul;
        this.liczbaStron = liczbaStron;
        this.rokWydania = rokWydania;
        this.autor = autor;
    }

    public static Ksiazka createKsiazka(String tytul, int liczbaStron, int rokWydania, Autor autor) {
        validateTytul(tytul);
        validateLiczbaStron(liczbaStron);
        validateRokWydania(rokWydania);
        validateAutor(autor);
        Ksiazka k = new Ksiazka(tytul, liczbaStron, rokWydania, autor);
        autor.dodajKsiazke(k);
        return k;
    }

    public void zmienAutora(Autor nowyAutor) {
        if (nowyAutor == null) throw new IllegalArgumentException("Autor nie moze byc null");
        if (this.autor == nowyAutor) return;

        if (this.autor != null) {
            Autor staryAutor = this.autor;
            this.autor = null;
            staryAutor.usunKsiazke(this);
        }

        this.autor = nowyAutor;

        if (!nowyAutor.getKsiazki().contains(this)) {
            nowyAutor.dodajKsiazke(this);
        }
    }

    private static void validateTytul(String tytul) {
        if (tytul == null || tytul.isBlank()) throw new IllegalArgumentException("tytul wymagany");
        if (tytul.length() > 200) throw new IllegalArgumentException("tytul max 200 znakow");
    }
    private static void validateLiczbaStron(int liczbaStron) {
        if (liczbaStron < 1 || liczbaStron > 20000) throw new IllegalArgumentException("liczbaStron 1..20000");
    }

    private static void validateRokWydania(int rok) {
        if (rok < 1500 || rok > 2026) throw new IllegalArgumentException("rok Wydania 1500..2026");
    }

    private static void validateAutor(Autor autor) {
        if (autor == null) throw new IllegalArgumentException("autor wymagany");
    }

    public Long getId() { return id; }
    public String getTytul() { return tytul; }
    public int getLiczbaStron() { return liczbaStron; }
    public int getRokWydania() { return rokWydania; }
    public Autor getAutor() { return autor; }

    @Override
    public String toString() {
        return "Ksiazka{id=" + id + ", tytul='" + tytul + ", autor_id=" + (autor != null ? autor.getId() : null) + "}";
    }
}
