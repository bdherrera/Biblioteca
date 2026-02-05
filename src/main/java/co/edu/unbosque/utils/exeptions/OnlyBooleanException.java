package co.edu.unbosque.utils.exeptions;

public class OnlyBooleanException extends RuntimeException {
    public OnlyBooleanException() {
        super("Solo se puede poner si o no");
    }
}
