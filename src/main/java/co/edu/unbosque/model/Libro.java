package co.edu.unbosque.model;

import java.time.LocalDate;

public class Libro {
    private String id;
    private String titulo;
    private String autor;
    private String isbn;
    private int ejemplaresTotales;
    private int ejemplaresDisponibles;
    private boolean activo;
    private LocalDate fechaRegistro;

    public Libro() {}

    public Libro(String id, String titulo, String autor, String isbn,
                 int ejemplaresTotales, int ejemplaresDisponibles,
                 boolean activo, LocalDate fechaRegistro) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.ejemplaresTotales = ejemplaresTotales;
        this.ejemplaresDisponibles = ejemplaresDisponibles;
        this.activo = activo;
        this.fechaRegistro = fechaRegistro;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public int getEjemplaresTotales() { return ejemplaresTotales; }
    public void setEjemplaresTotales(int ejemplaresTotales) { this.ejemplaresTotales = ejemplaresTotales; }

    public int getEjemplaresDisponibles() { return ejemplaresDisponibles; }
    public void setEjemplaresDisponibles(int ejemplaresDisponibles) { this.ejemplaresDisponibles = ejemplaresDisponibles; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
