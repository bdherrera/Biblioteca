package co.edu.unbosque.model.persistence;


import co.edu.unbosque.model.Devolucion;
import co.edu.unbosque.model.Libro;
import co.edu.unbosque.model.Prestamo;
import co.edu.unbosque.model.Usuario;
import co.edu.unbosque.model.dto.DevolucionDTO;
import co.edu.unbosque.model.dto.LibroDTO;
import co.edu.unbosque.model.dto.PrestamoDTO;
import co.edu.unbosque.model.dto.UsuarioDTO;

import java.util.ArrayList;
import java.util.List;

public class DataMapper {

    /*
    Funciones para Usuario
     */

    /**
     * Metodo para pasar de DTO a Entidad
     * @param dto dto a mapear
     * @return Entidad, retorna la entidad mapeado
     */
    public static Usuario usuarioDTOToUsuario(UsuarioDTO dto) {
        Usuario entidad;
        entidad = new Usuario(dto.getId(), dto.getNombre(), dto.getApellido(), dto.getFechaNacimiento(), dto.getEmail(), dto.getTelefono(), dto.getDireccion(), dto.isEstadoCuenta(), dto.getFechaRegistro());
        return entidad;
    }

    /**
     * Metodo para pasar de entidad a DTO
     * @param entidad usuario a ser mapeado
     * @return usurio mapeado a DTO
     */
    public static UsuarioDTO usuarioToUsuarioDTO(Usuario entidad) {
        UsuarioDTO dto;
        dto = new UsuarioDTO(entidad.getId(), entidad.getNombre(), entidad.getApellido(), entidad.getFechaNacimiento(), entidad.getEmail(), entidad.getTelefono(), entidad.getDireccion(), entidad.isEstadoCuenta(), entidad.getFechaRegistro());
        return dto;
    }

    /**
     * Metodo para pasar una lista de DTOs a una lista de entidades
     * @param dtos dtos a mapear
     * @return dto mapeado a entidad
     */
    public static List<Usuario> listUserDTOToListUser(List<UsuarioDTO> dtos) {
        List<Usuario> entidades = new ArrayList<>();
        for (UsuarioDTO dtoUsuario : dtos) {
            entidades.add(usuarioDTOToUsuario(dtoUsuario));
        }
        return entidades;
    }

    /**
     * Metodo para pasar una lista de entidaddes a una lista de DTOs
     * @param entidades lista de entidades a mapear
     * @return lista de entidades mapeadas a dtos
     */
    public static List<UsuarioDTO> listUserTolistUserDTO(List<Usuario> entidades) {

        List<UsuarioDTO> dtos = new ArrayList<>();
        for (Usuario entidad : entidades) {
            dtos.add(usuarioToUsuarioDTO(entidad));
        }
        return dtos;

    }


    // --- Libro ---
    public static Libro libroDTOToLibro(LibroDTO d) {
        return new Libro(d.id, d.titulo, d.autor, d.isbn,
                d.ejemplaresTotales, d.ejemplaresDisponibles, d.activo, d.fechaRegistro);
    }

    public static LibroDTO libroToLibroDTO(Libro e) {
        return new LibroDTO(e.getId(), e.getTitulo(), e.getAutor(), e.getIsbn(),
                e.getEjemplaresTotales(), e.getEjemplaresDisponibles(), e.isActivo(), e.getFechaRegistro());
    }

    // --- Prestamo ---
    public static Prestamo prestamoDTOToPrestamo(PrestamoDTO d) {
        return new Prestamo(d.id, d.idUsuario, d.idLibro, d.fechaPrestamo, d.fechaVencimiento, d.estado);
    }

    public static PrestamoDTO prestamoToPrestamoDTO(Prestamo e) {
        return new PrestamoDTO(e.getId(), e.getIdUsuario(), e.getIdLibro(),
                e.getFechaPrestamo(), e.getFechaVencimiento(), e.getEstado());
    }

    // --- Devolucion ---
    public static Devolucion devolucionDTOToDevolucion(DevolucionDTO d) {
        return new Devolucion(d.id, d.idPrestamo, d.fechaDevolucion, d.multa, d.estadoLibro, d.comentarios);
    }

    public static DevolucionDTO devolucionToDevolucionDTO(Devolucion e) {
        return new DevolucionDTO(e.getId(), e.getIdPrestamo(), e.getFechaDevolucion(),
                e.getMulta(), e.getEstadoLibro(), e.getComentarios());
    }

}