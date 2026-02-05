package co.edu.unbosque.utils.exeptions;

public class OnlyNumbersException extends RuntimeException {
    public OnlyNumbersException() {
        super("Solo se pueden ingresar numeros");
    }
}
