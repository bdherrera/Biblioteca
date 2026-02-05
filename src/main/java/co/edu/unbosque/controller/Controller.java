package co.edu.unbosque.controller;

import co.edu.unbosque.model.ModelFacade;
import co.edu.unbosque.model.dto.LibroDTO;
import co.edu.unbosque.model.dto.PrestamoDTO;
import co.edu.unbosque.model.dto.UsuarioDTO;
import co.edu.unbosque.view.ViewFacade;

import java.util.List;

public class Controller {

    private final ViewFacade vf;
    private final ModelFacade mf;

    public Controller() {
        mf = new ModelFacade();
        vf = new ViewFacade();
        mf.load();
    }

    public void run() {

        boolean salirPrincipal = false;

        while (!salirPrincipal) {

            vf.getConsola().imprimirConSalto("\n--- MENÚ PRINCIPAL ---");
            vf.getConsola().imprimirConSalto("1. Usuarios");
            vf.getConsola().imprimirConSalto("2. Libros");
            vf.getConsola().imprimirConSalto("3. Préstamos / Devoluciones");
            vf.getConsola().imprimirConSalto("4. Salir");

            int opcion = vf.getConsola().leerEnteroSeguro("- ", "Opción no válida");

            switch (opcion) {
                case 1 -> menuUsuarios();
                case 2 -> menuLibros();
                case 3 -> menuPrestamos();
                case 4 -> {
                    salirPrincipal = true;
                    mf.persist();
                    vf.getConsola().imprimirConSalto("Saliendo del sistema...");
                }
                default -> vf.getConsola().imprimirConSalto("Opción no válida.");
            }
        }
    }

    // ------------------ USUARIOS ------------------

    private void menuUsuarios() {
        boolean volver = false;

        while (!volver) {
            vf.getConsola().imprimirConSalto("\n--- GESTIÓN DE USUARIOS ---");
            vf.getConsola().imprimirConSalto("1. Crear");
            vf.getConsola().imprimirConSalto("2. Modificar");
            vf.getConsola().imprimirConSalto("3. Eliminar");
            vf.getConsola().imprimirConSalto("4. Buscar");
            vf.getConsola().imprimirConSalto("5. Volver");

            int op = vf.getConsola().leerEnteroSeguro("- ", "Número no válido");

            switch (op) {
                case 1 -> {
                    UsuarioDTO nuevo = vf.getSolicitudesUsuario().addUsuario();
                    boolean ok = mf.getUsuarioDAO().add(nuevo);
                    if (ok) {
                        mf.persist();
                        vf.getConsola().imprimirConSalto("Usuario registrado ✅");
                    } else vf.getConsola().imprimirConSalto("No se pudo registrar (posible duplicado).");
                }
                case 2 -> {
                    String id = vf.getConsola().leerString("Cédula a editar: ");
                    UsuarioDTO actualizado = vf.getSolicitudesUsuario().update(id);
                    boolean ok = mf.getUsuarioDAO().update(actualizado, id);
                    if (ok) {
                        mf.persist();
                        vf.getConsola().imprimirConSalto("Usuario modificado ✅");
                    } else vf.getConsola().imprimirConSalto("No se pudo modificar (no existe).");
                }
                case 3 -> {
                    String id = vf.getConsola().leerString("Cédula a eliminar: ");
                    boolean ok = mf.getUsuarioDAO().remove(id);
                    if (ok) {
                        mf.persist();
                        vf.getConsola().imprimirConSalto("Usuario eliminado ✅");
                    } else vf.getConsola().imprimirConSalto("No se pudo eliminar (no existe).");
                }
                case 4 -> {
                    String id = vf.getConsola().leerString("Cédula a buscar: ");
                    UsuarioDTO user = mf.getUsuarioDAO().get(id);
                    vf.getConsola().imprimirConSalto(user != null ? user.toString() : "Usuario no encontrado.");
                }
                case 5 -> volver = true;
                default -> vf.getConsola().imprimirConSalto("Opción no válida.");
            }
        }
    }

    // ------------------ LIBROS ------------------

    private void menuLibros() {
        boolean volver = false;

        while (!volver) {
            vf.getConsola().imprimirConSalto("\n--- GESTIÓN DE LIBROS ---");
            vf.getConsola().imprimirConSalto("1. Crear");
            vf.getConsola().imprimirConSalto("2. Modificar");
            vf.getConsola().imprimirConSalto("3. Eliminar (desactivar)");
            vf.getConsola().imprimirConSalto("4. Buscar por ID");
            vf.getConsola().imprimirConSalto("5. Buscar por título");
            vf.getConsola().imprimirConSalto("6. Listar todos");
            vf.getConsola().imprimirConSalto("7. Volver");

            int op = vf.getConsola().leerEnteroSeguro("- ", "Número no válido");

            switch (op) {
                case 1 -> {
                    LibroDTO nuevo = vf.getSolicitudesLibro().addLibro();
                    boolean ok = mf.getLibroDAO().add(nuevo);
                    if (ok) {
                        mf.persist();
                        vf.getConsola().imprimirConSalto("Libro registrado ✅");
                    } else vf.getConsola().imprimirConSalto("No se pudo registrar (ID duplicado).");
                }
                case 2 -> {
                    String id = vf.getConsola().leerString("ID del libro a editar: ");
                    LibroDTO actualizado = vf.getSolicitudesLibro().updateLibro(id);
                    boolean ok = mf.getLibroDAO().update(actualizado, id);
                    if (ok) {
                        mf.persist();
                        vf.getConsola().imprimirConSalto("Libro modificado ✅");
                    } else vf.getConsola().imprimirConSalto("No se pudo modificar (no existe).");
                }
                case 3 -> {
                    String id = vf.getConsola().leerString("ID del libro a desactivar: ");
                    boolean ok = mf.getLibroDAO().remove(id);
                    if (ok) {
                        mf.persist();
                        vf.getConsola().imprimirConSalto("Libro desactivado ✅");
                    } else vf.getConsola().imprimirConSalto("No se pudo desactivar (no existe).");
                }
                case 4 -> {
                    String id = vf.getConsola().leerString("ID del libro: ");
                    LibroDTO libro = mf.getLibroDAO().get(id);
                    if (libro == null) vf.getConsola().imprimirConSalto("Libro no encontrado.");
                    else imprimirLibro(libro);
                }
                case 5 -> {
                    String titulo = vf.getConsola().leerString("Título a buscar (contiene): ");
                    List<LibroDTO> resultados = mf.getLibroDAO().buscarPorTitulo(titulo);
                    if (resultados.isEmpty()) vf.getConsola().imprimirConSalto("Sin resultados.");
                    else resultados.forEach(this::imprimirLibro);
                }
                case 6 -> {
                    List<LibroDTO> all = mf.getLibroDAO().getAll();
                    if (all.isEmpty()) vf.getConsola().imprimirConSalto("No hay libros registrados.");
                    else all.forEach(this::imprimirLibro);
                }
                case 7 -> volver = true;
                default -> vf.getConsola().imprimirConSalto("Opción no válida.");
            }
        }
    }

    private void imprimirLibro(LibroDTO l) {
        vf.getConsola().imprimirConSalto(
                "Libro{" +
                        "id='" + l.id + '\'' +
                        ", titulo='" + l.titulo + '\'' +
                        ", autor='" + l.autor + '\'' +
                        ", isbn='" + l.isbn + '\'' +
                        ", totales=" + l.ejemplaresTotales +
                        ", disponibles=" + l.ejemplaresDisponibles +
                        ", activo=" + l.activo +
                        ", fechaRegistro=" + l.fechaRegistro +
                        "}"
        );
    }

    // ------------------ PRÉSTAMOS / DEVOLUCIONES ------------------

    private void menuPrestamos() {
        boolean volver = false;

        while (!volver) {
            vf.getConsola().imprimirConSalto("\n--- PRÉSTAMOS / DEVOLUCIONES ---");
            vf.getConsola().imprimirConSalto("1. Crear préstamo");
            vf.getConsola().imprimirConSalto("2. Registrar devolución");
            vf.getConsola().imprimirConSalto("3. Listar todos los préstamos");
            vf.getConsola().imprimirConSalto("4. Listar préstamos por usuario");
            vf.getConsola().imprimirConSalto("5. Volver");

            int op = vf.getConsola().leerEnteroSeguro("- ", "Número no válido");

            switch (op) {
                case 1 -> {
                    String idUsuario = vf.getSolicitudesPrestamo().pedirIdUsuario();
                    String idLibro = vf.getSolicitudesPrestamo().pedirIdLibro();
                    var venc = vf.getSolicitudesPrestamo().pedirFechaVencimiento();

                    String msg = mf.getPrestamoService().prestar(idUsuario, idLibro, venc);
                    vf.getConsola().imprimirConSalto(msg);

                    if (msg.startsWith("Préstamo creado")) mf.persist();
                }
                case 2 -> {
                    String idPrestamo = vf.getSolicitudesPrestamo().pedirIdPrestamo();
                    var estadoLibro = vf.getSolicitudesDevolucion().pedirEstadoLibro();
                    float multa = vf.getSolicitudesDevolucion().pedirMulta();
                    String comentarios = vf.getSolicitudesDevolucion().pedirComentarios();

                    String msg = mf.getPrestamoService().devolver(idPrestamo, estadoLibro, multa, comentarios);
                    vf.getConsola().imprimirConSalto(msg);

                    if (msg.startsWith("Devolución registrada")) mf.persist();
                }
                case 3 -> {
                    List<PrestamoDTO> all = mf.getPrestamoDAO().getAll();
                    if (all.isEmpty()) vf.getConsola().imprimirConSalto("No hay préstamos.");
                    else all.forEach(this::imprimirPrestamo);
                }
                case 4 -> {
                    String idUsuario = vf.getSolicitudesPrestamo().pedirIdUsuario();
                    var prestamos = mf.getPrestamoDAO().findByUsuario(idUsuario);
                    if (prestamos.isEmpty()) vf.getConsola().imprimirConSalto("Ese usuario no tiene préstamos registrados.");
                    else prestamos.forEach(p -> imprimirPrestamo(mf.getPrestamoDAO().get(p.getId())));
                }
                case 5 -> volver = true;
                default -> vf.getConsola().imprimirConSalto("Opción no válida.");
            }
        }
    }

    private void imprimirPrestamo(PrestamoDTO p) {
        if (p == null) return;
        vf.getConsola().imprimirConSalto(
                "Prestamo{" +
                        "id='" + p.id + '\'' +
                        ", idUsuario='" + p.idUsuario + '\'' +
                        ", idLibro='" + p.idLibro + '\'' +
                        ", fechaPrestamo=" + p.fechaPrestamo +
                        ", fechaVencimiento=" + p.fechaVencimiento +
                        ", estado=" + p.estado +
                        "}"
        );
    }
}
