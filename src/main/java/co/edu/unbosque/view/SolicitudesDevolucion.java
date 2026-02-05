package co.edu.unbosque.view;

import co.edu.unbosque.model.EstadoLibro;

public class SolicitudesDevolucion {

    private final Consola consola;

    public SolicitudesDevolucion(Consola consola) {
        this.consola = consola;
    }

    public EstadoLibro pedirEstadoLibro() {
        consola.imprimirConSalto("Estado del libro:");
        consola.imprimirConSalto("1. BUENO");
        consola.imprimirConSalto("2. DANADO");
        consola.imprimirConSalto("3. PERDIDO");

        int op = consola.leerEnteroSeguro("- ", "Opción inválida");

        return switch (op) {
            case 1 -> EstadoLibro.BUENO;
            case 2 -> EstadoLibro.DANADO;
            case 3 -> EstadoLibro.PERDIDO;
            default -> EstadoLibro.BUENO;
        };
    }

    public float pedirMulta() {
        return consola.leerFloatSeguro("Multa (0 si no aplica): ", "Debe ser un número.");
    }

    public String pedirComentarios() {
        return consola.leerString("Comentarios (opcional): ");
    }
}
