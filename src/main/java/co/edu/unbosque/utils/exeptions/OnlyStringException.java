package co.edu.unbosque.utils.exeptions;

public class OnlyStringException extends RuntimeException {
    public OnlyStringException() {
        super("No se puede ingresar numeros o caracteres");
    }
}
