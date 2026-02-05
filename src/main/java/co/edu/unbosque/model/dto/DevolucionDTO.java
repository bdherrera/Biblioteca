package co.edu.unbosque.model.dto;

import co.edu.unbosque.model.EstadoLibro;
import java.time.LocalDate;

public class DevolucionDTO {
    public String id, idPrestamo;
    public LocalDate fechaDevolucion;
    public float multa;
    public EstadoLibro estadoLibro;
    public String comentarios;

    public DevolucionDTO(String id, String idPrestamo, LocalDate fechaDevolucion,
                         float multa, EstadoLibro estadoLibro, String comentarios) {
        this.id = id; this.idPrestamo = idPrestamo; this.fechaDevolucion = fechaDevolucion;
        this.multa = multa; this.estadoLibro = estadoLibro; this.comentarios = comentarios;
    }
}
