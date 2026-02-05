package co.edu.unbosque.model;

import java.time.LocalDate;

public class Devolucion {
    private String id;
    private String idPrestamo;
    private LocalDate fechaDevolucion;
    private float multa;
    private EstadoLibro estadoLibro;
    private String comentarios;

    public Devolucion() {}

    public Devolucion(String id, String idPrestamo, LocalDate fechaDevolucion,
                      float multa, EstadoLibro estadoLibro, String comentarios) {
        this.id = id;
        this.idPrestamo = idPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.multa = multa;
        this.estadoLibro = estadoLibro;
        this.comentarios = comentarios;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getIdPrestamo() { return idPrestamo; }
    public void setIdPrestamo(String idPrestamo) { this.idPrestamo = idPrestamo; }

    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }

    public float getMulta() { return multa; }
    public void setMulta(float multa) { this.multa = multa; }

    public EstadoLibro getEstadoLibro() { return estadoLibro; }
    public void setEstadoLibro(EstadoLibro estadoLibro) { this.estadoLibro = estadoLibro; }

    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }
}
