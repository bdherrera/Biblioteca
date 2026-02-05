package co.edu.unbosque.model.dto;

import java.time.LocalDate;

public class LibroDTO {
    public String id, titulo, autor, isbn;
    public int ejemplaresTotales, ejemplaresDisponibles;
    public boolean activo;
    public LocalDate fechaRegistro;

    public LibroDTO(String id, String titulo, String autor, String isbn,
                    int ejemplaresTotales, int ejemplaresDisponibles,
                    boolean activo, LocalDate fechaRegistro) {
        this.id = id; this.titulo = titulo; this.autor = autor; this.isbn = isbn;
        this.ejemplaresTotales = ejemplaresTotales;
        this.ejemplaresDisponibles = ejemplaresDisponibles;
        this.activo = activo;
        this.fechaRegistro = fechaRegistro;
    }
}
