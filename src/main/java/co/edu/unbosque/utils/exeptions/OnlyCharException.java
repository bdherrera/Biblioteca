package co.edu.unbosque.utils.exeptions;

public class OnlyCharException extends RuntimeException {
    public OnlyCharException() {
        super("Ingrese solo una letra");
    }
}
