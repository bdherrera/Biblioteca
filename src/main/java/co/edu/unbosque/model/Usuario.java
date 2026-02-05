package co.edu.unbosque.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Representa un usuario dentro del sistema de gestión de la biblioteca.
 * Almacena información personal, de contacto y el estado de sus préstamos.
 */
public class Usuario implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    //Identificaciones
    private String id;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;

    //Datos de contacto
    private String email;
    private String telefono;
    private String direccion;

    //Gestion de prestamo
    private boolean estadoCuenta;

    private LocalDate fechaRegistro;

    /**
     * Constructor vacío para la clase Usuario.
     */
    public Usuario() {
    }

    /**
     * Constructor con parámetros básicos para crear un nuevo usuario.
     *
     * @param id Identificación única del usuario.
     * @param nombre Nombre del usuario.
     * @param apellido Apellido del usuario.
     * @param fechaNacimiento Fecha de nacimiento.
     * @param email Correo electrónico de contacto.
     * @param telefono Número de teléfono.
     * @param direccion Dirección de residencia.
     * @param estadoCuenta Estado actual de la cuenta (activo/inactivo).
     * @param fechaRegistro Fecha en la que se registra en el sistema.
     */
    public Usuario(String id, String nombre, String apellido, LocalDate fechaNacimiento, String email, String telefono, String direccion, boolean estadoCuenta, LocalDate fechaRegistro) {
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
     * Constructor completo para la clase Usuario, incluyendo la lista de libros.
     *
     * @param id Identificación única del usuario.
     * @param nombre Nombre del usuario.
     * @param apellido Apellido del usuario.
     * @param fechaNacimiento Fecha de nacimiento.
     * @param email Correo electrónico.
     * @param telefono Número de teléfono.
     * @param direccion Dirección de residencia.
     * @param estadoCuenta Estado de la cuenta.
     * @param libros Lista de libros asociados al usuario.
     * @param fechaRegistro Fecha de registro.
     */
    public Usuario(String id, String nombre, String apellido, LocalDate fechaNacimiento, String email, String telefono, String direccion, boolean estadoCuenta, List<Libro> libros, LocalDate fechaRegistro) {
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
     * Devuelve una representación en cadena de texto con los datos del usuario formateados.
     * @return String con la información detallada del usuario.
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
