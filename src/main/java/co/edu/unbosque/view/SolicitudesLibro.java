package co.edu.unbosque.view;

import co.edu.unbosque.model.dto.LibroDTO;

import java.time.LocalDate;

public class SolicitudesLibro {

    private final Consola consola;

    public SolicitudesLibro(Consola consola) {
        this.consola = consola;
    }

    public LibroDTO addLibro() {
        String id = consola.leerString("ID del libro: ");
        String titulo = consola.leerString("Título: ");
        String autor = consola.leerString("Autor: ");
        String isbn = consola.leerString("ISBN (opcional): ");

        int totales = consola.leerEnteroSeguro("Ejemplares totales: ", "Debe ser un número.");
        int disponibles = consola.leerEnteroSeguro("Ejemplares disponibles: ", "Debe ser un número.");

        boolean activo = true;
        LocalDate fechaRegistro = LocalDate.now();

        return new LibroDTO(id, titulo, autor, isbn, totales, disponibles, activo, fechaRegistro);
    }

    public LibroDTO updateLibro(String idLibro) {
        String titulo = consola.leerString("Nuevo título: ");
        String autor = consola.leerString("Nuevo autor: ");
        String isbn = consola.leerString("Nuevo ISBN (opcional): ");

        int totales = consola.leerEnteroSeguro("Nuevos ejemplares totales: ", "Debe ser un número.");
        int disponibles = consola.leerEnteroSeguro("Nuevos ejemplares disponibles: ", "Debe ser un número.");

        boolean activo = true;
        LocalDate fechaRegistro = LocalDate.now();

        return new LibroDTO(idLibro, titulo, autor, isbn, totales, disponibles, activo, fechaRegistro);
    }
}
