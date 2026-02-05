package co.edu.unbosque.model.dto;

import co.edu.unbosque.model.EstadoPrestamo;
import java.time.LocalDate;

public class PrestamoDTO {
    public String id, idUsuario, idLibro;
    public LocalDate fechaPrestamo, fechaVencimiento;
    public EstadoPrestamo estado;

    public PrestamoDTO(String id, String idUsuario, String idLibro,
                       LocalDate fechaPrestamo, LocalDate fechaVencimiento,
                       EstadoPrestamo estado) {
        this.id = id; this.idUsuario = idUsuario; this.idLibro = idLibro;
        this.fechaPrestamo = fechaPrestamo; this.fechaVencimiento = fechaVencimiento;
        this.estado = estado;
    }
}
