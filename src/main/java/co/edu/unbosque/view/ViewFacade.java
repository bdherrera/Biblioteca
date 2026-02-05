package co.edu.unbosque.view;

public class ViewFacade {

    private final Consola consola;
    private final SolicitudesUsuario solicitudesUsuario;
    private final SolicitudesLibro solicitudesLibro;
    private final SolicitudesPrestamo solicitudesPrestamo;
    private final SolicitudesDevolucion solicitudesDevolucion;

    public ViewFacade() {
        consola = new Consola();
        solicitudesUsuario = new SolicitudesUsuario(consola);
        solicitudesLibro = new SolicitudesLibro(consola);
        solicitudesPrestamo = new SolicitudesPrestamo(consola);
        solicitudesDevolucion = new SolicitudesDevolucion(consola);
    }

    public Consola getConsola() { return consola; }

    public SolicitudesUsuario getSolicitudesUsuario() { return solicitudesUsuario; }

    public SolicitudesLibro getSolicitudesLibro() { return solicitudesLibro; }

    public SolicitudesPrestamo getSolicitudesPrestamo() { return solicitudesPrestamo; }

    public SolicitudesDevolucion getSolicitudesDevolucion() { return solicitudesDevolucion; }
}
