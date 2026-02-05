package co.edu.unbosque.model.services;

import co.edu.unbosque.model.EstadoLibro;
import co.edu.unbosque.model.EstadoPrestamo;
import co.edu.unbosque.model.Libro;
import co.edu.unbosque.model.Prestamo;
import co.edu.unbosque.model.dto.DevolucionDTO;
import co.edu.unbosque.model.dto.PrestamoDTO;
import co.edu.unbosque.model.persistence.dao.DevolucionDAO;
import co.edu.unbosque.model.persistence.dao.LibroDAO;
import co.edu.unbosque.model.persistence.dao.PrestamoDAO;
import co.edu.unbosque.model.persistence.dao.UsuarioDAO;

import java.time.LocalDate;
import java.util.UUID;

public class PrestamoService {
    
    private final UsuarioDAO usuarioDAO;
    private final LibroDAO libroDAO;
    private final PrestamoDAO prestamoDAO;
    private final DevolucionDAO devolucionDAO;

    public PrestamoService(UsuarioDAO usuarioDAO, LibroDAO libroDAO,
                           PrestamoDAO prestamoDAO, DevolucionDAO devolucionDAO) {
        this.usuarioDAO = usuarioDAO;
        this.libroDAO = libroDAO;
        this.prestamoDAO = prestamoDAO;
        this.devolucionDAO = devolucionDAO;
    }

    public String prestar(String idUsuario, String idLibro, LocalDate fechaVencimiento) {
        if (usuarioDAO.find(idUsuario) == null) return "Usuario no existe.";
        Libro libro = libroDAO.find(idLibro);
        if (libro == null) return "Libro no existe.";
        if (!libro.isActivo()) return "Libro inactivo.";
        if (libro.getEjemplaresDisponibles() <= 0) return "No hay ejemplares disponibles.";

        String idPrestamo = "p-" + UUID.randomUUID();
        PrestamoDTO p = new PrestamoDTO(
                idPrestamo,
                idUsuario,
                idLibro,
                LocalDate.now(),
                fechaVencimiento,
                EstadoPrestamo.PRESTADO
        );

        boolean okPrestamo = prestamoDAO.add(p);
        boolean okStock = libroDAO.ajustarDisponibles(idLibro, -1);

        if (!okPrestamo || !okStock) return "No se pudo registrar el préstamo.";
        return "Préstamo creado: " + idPrestamo;
    }

    public String devolver(String idPrestamo, EstadoLibro estadoLibro, float multa, String comentarios) {
        Prestamo prestamo = prestamoDAO.find(idPrestamo);
        if (prestamo == null) return "Préstamo no existe.";
        if (prestamo.getEstado() != EstadoPrestamo.PRESTADO) return "El préstamo ya fue devuelto.";

        if (devolucionDAO.findByPrestamo(idPrestamo) != null) return "Ya existe devolución para ese préstamo.";

        String idDevolucion = "d-" + UUID.randomUUID();
        DevolucionDTO d = new DevolucionDTO(
                idDevolucion,
                idPrestamo,
                LocalDate.now(),
                multa,
                estadoLibro,
                comentarios
        );

        boolean okDev = devolucionDAO.add(d);

        // cambiar estado del préstamo
        PrestamoDTO actualizado = new PrestamoDTO(
                prestamo.getId(),
                prestamo.getIdUsuario(),
                prestamo.getIdLibro(),
                prestamo.getFechaPrestamo(),
                prestamo.getFechaVencimiento(),
                EstadoPrestamo.DEVUELTO
        );
        boolean okUpd = prestamoDAO.update(actualizado, prestamo.getId());

        // devolver stock si no es perdido
        boolean okStock = true;
        if (estadoLibro != EstadoLibro.PERDIDO) {
            okStock = libroDAO.ajustarDisponibles(prestamo.getIdLibro(), +1);
        }

        if (!okDev || !okUpd || !okStock) return "No se pudo completar la devolución.";
        return "Devolución registrada: " + idDevolucion;
    }
}