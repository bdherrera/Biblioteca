package co.edu.unbosque.model.dto;

import co.edu.unbosque.model.Libro;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Data Transfer Object (DTO) que representa a un usuario en el sistema.
 * Contiene información personal, de contacto y el estado de sus préstamos.
 */
public class UsuarioDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;

    private String email;
    private String telefono;
    private String direccion;

    private boolean estadoCuenta;
    private LocalDate fechaRegistro;

    /**
     * Constructor vacío para la serialización y frameworks.
     */
    public UsuarioDTO() {
    }

    /**
     * Constructor con parámetros básicos (inicializa la lista de libros vacía).
     * @param id Identificador único
     * @param nombre Nombre del usuario
     * @param apellido Apellido del usuario
     * @param fechaNacimiento Fecha de nacimiento
     * @param email Correo electrónico
     * @param telefono Teléfono
     * @param direccion Dirección
     * @param estadoCuenta Estado inicial de la cuenta
     * @param fechaRegistro Fecha de registro en el sistema
     */
    public UsuarioDTO(String id, String nombre, String apellido, LocalDate fechaNacimiento, String email, String telefono, String direccion, boolean estadoCuenta, LocalDate fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.estadoCuenta = estadoCuenta;
        this.fechaRegistro = fechaRegistro;


    }

    /**
     * Constructor completo con lista de libros incluida.
     * @param id Identificador único
     * @param nombre Nombre del usuario
     * @param apellido Apellido del usuario
     * @param fechaNacimiento Fecha de nacimiento
     * @param email Correo electrónico
     * @param telefono Teléfono
     * @param direccion Dirección
     * @param estadoCuenta Estado de la cuenta
     * @param libros Lista de libros asociados
     * @param fechaRegistro Fecha de registro
     */
    public UsuarioDTO(String id, String nombre, String apellido, LocalDate fechaNacimiento, String email, String telefono, String direccion, boolean estadoCuenta, List<Libro> libros, LocalDate fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.estadoCuenta = estadoCuenta;
        this.fechaRegistro = fechaRegistro;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el estado de la cuenta.
     * @return true si está activo, false de lo contrario.
     */
    public boolean isEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(boolean estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }



    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Retorna una representación en cadena de texto con el formato de ficha del usuario.
     * @return String con los datos detallados del usuario.
     */
    @Override
    public String toString() {
        return "\n==============================" +
                "\n       DATOS DEL USUARIO      " +
                "\n==============================" +
                "\n ID:               " + id +
                "\n Nombre Completo:  " + nombre + " " + apellido +
                "\n F. Nacimiento:    " + fechaNacimiento +
                "\n Email:            " + email +
                "\n Teléfono:         " + telefono +
                "\n Dirección:        " + direccion +
                "\n Estado Cuenta:    " + (estadoCuenta ? "Activo" : "Inactivo") +
                "\n F. Registro:      " + fechaRegistro +
                "\n==============================\n";
    }
}
