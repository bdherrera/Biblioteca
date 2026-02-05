package co.edu.unbosque.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * Clase encargada de gestionar la interacción con el usuario a través de la consola.
 * Proporciona métodos para leer diferentes tipos de datos con validaciones básicas.
 */
public class Consola {

    private final Scanner lector;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Consola() {
        lector = new Scanner(System.in);
    }

    // --- básicos (si los usas, recuerda manejar buffer) ---
    public int leerEntero() { return lector.nextInt(); }
    public float leerFloat() { return lector.nextFloat(); }
    public String leerLinea() { return lector.nextLine(); }

    public void quemarLinea() {
        if (lector.hasNextLine()) lector.nextLine();
    }

    public void imprimirConSalto(String texto) {
        System.out.println(texto);
    }

    public String leerString(String mensaje) {
        while (true) {
            imprimirConSalto(mensaje);
            String entrada = leerLinea();
            if (entrada != null && !entrada.trim().isEmpty()) {
                return entrada.trim();
            }
            imprimirConSalto("Entrada vacía. Intente de nuevo.");
        }
    }

    public int leerEnteroSeguro(String mensaje, String mensajeError) {
        while (true) {
            try {
                imprimirConSalto(mensaje);
                int numero = leerEntero();
                quemarLinea(); // limpia el salto de línea
                return numero;
            } catch (InputMismatchException e) {
                imprimirConSalto(mensajeError);
                quemarLinea(); // consume lo inválido
            }
        }
    }

    public float leerFloatSeguro(String mensaje, String mensajeError) {
        while (true) {
            try {
                imprimirConSalto(mensaje);
                float numero = leerFloat();
                quemarLinea();
                return numero;
            } catch (InputMismatchException e) {
                imprimirConSalto(mensajeError);
                quemarLinea();
            }
        }
    }

    /** Antes se caía si el usuario digitaba mal. Ahora repregunta. */
    public LocalDate leerFechaSegura(String mensaje) {
        while (true) {
            imprimirConSalto(mensaje + " (Formato: dd/MM/yyyy)");
            String entrada = leerLinea();
            try {
                return LocalDate.parse(entrada.trim(), FORMATO_FECHA);
            } catch (DateTimeParseException e) {
                imprimirConSalto("Fecha inválida. Ejemplo válido: 05/02/2026");
            }
        }
    }
}
