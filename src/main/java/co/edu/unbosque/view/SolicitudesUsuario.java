package co.edu.unbosque.view;

import co.edu.unbosque.model.dto.UsuarioDTO;
import java.time.LocalDate;

public class SolicitudesUsuario {

    private final Consola consola;

    // Inyección de dependencia: NO creamos Consola aquí
    public SolicitudesUsuario(Consola consola) {
        this.consola = consola;
    }

    public UsuarioDTO addUsuario() {

        String id = consola.leerString("Ingrese su cedula: ");
        String nombre = consola.leerString("Ingrese su nombre: ");
        String apellido = consola.leerString("Ingrese su apellido: ");
        LocalDate fechaNacimiento = consola.leerFechaSegura("Ingrese su fecha de nacimiento");

        String email = consola.leerString("Ingrese su email: ");
        String telefono = consola.leerString("Ingrese su telefono: ");
        String direccion = consola.leerString("Ingrese su direccion: ");

        boolean estadoCuenta = true;
        LocalDate fechaRegistro = LocalDate.now();

        return new UsuarioDTO(id, nombre, apellido, fechaNacimiento, email, telefono, direccion, estadoCuenta, fechaRegistro);
    }

    public UsuarioDTO update(String cedula) {

        String nombre = consola.leerString("Ingrese su nombre: ");
        String apellido = consola.leerString("Ingrese su apellido: ");
        LocalDate fechaNacimiento = consola.leerFechaSegura("Ingrese su fecha de nacimiento");

        String email = consola.leerString("Ingrese su email: ");
        String telefono = consola.leerString("Ingrese su telefono: ");
        String direccion = consola.leerString("Ingrese su direccion: ");

        boolean estadoCuenta = true;
        LocalDate fechaRegistro = LocalDate.now();

        return new UsuarioDTO(cedula, nombre, apellido, fechaNacimiento, email, telefono, direccion, estadoCuenta, fechaRegistro);
    }
}
