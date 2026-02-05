package co.edu.unbosque.view;

import java.time.LocalDate;

public class SolicitudesPrestamo {

    private final Consola consola;

    public SolicitudesPrestamo(Consola consola) {
        this.consola = consola;
    }

    public String pedirIdUsuario() {
        return consola.leerString("Cédula/ID del usuario: ");
    }

    public String pedirIdLibro() {
        return consola.leerString("ID del libro: ");
    }

    public LocalDate pedirFechaVencimiento() {
        return consola.leerFechaSegura("Fecha de vencimiento");
    }

    public String pedirIdPrestamo() {
        return consola.leerString("ID del préstamo: ");
    }
}
