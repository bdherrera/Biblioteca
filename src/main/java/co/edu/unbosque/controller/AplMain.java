package co.edu.unbosque.controller;

/**
 * Clase principal que sirve como punto de entrada para la aplicación.
 * Inicia el flujo del programa a través del controlador.
 */
public class AplMain {

    /**
     * Métod principal (main) que ejecuta la aplicación.
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {

        // Instancia el controlador principal
        Controller C = new Controller();

        // Inicia la ejecución de la lógica del programa
        C.run();

    }
}