package co.edu.unbosque.model.persistence.dao;

import co.edu.unbosque.model.Usuario;
import co.edu.unbosque.model.dto.UsuarioDTO;
import co.edu.unbosque.model.persistence.DataMapper;
import co.edu.unbosque.model.persistence.FileManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class UsuarioDAO implements OperationDAO<UsuarioDTO, Usuario> {

    private static final String FILE_NAME = "Usuarios.csv";
    private static final String SEP = ";";

    // NUEVO CSV (sin libros en Usuario)
    private static final String HEADER = String.join(SEP,
            "id", "nombre", "apellido", "fechaNacimiento", "email", "telefono",
            "direccion", "estadoCuenta", "fechaRegistro"
    );

    // Índice por id: evita duplicados y acelera find/get/remove/update
    private final Map<String, Usuario> usuariosById = new LinkedHashMap<>();
    private final Path filePath;

    public UsuarioDAO() {
        FileManager.crearCarpeta(); // crea src/archivos si no existe
        this.filePath = Paths.get("src", "archivos", FILE_NAME);
        load();
    }

    @Override
    public boolean add(UsuarioDTO dto) {
        if (dto == null || dto.getId() == null || dto.getId().isBlank()) return false;

        String id = dto.getId().trim();
        if (usuariosById.containsKey(id)) return false;

        Usuario entidad = DataMapper.usuarioDTOToUsuario(dto);
        entidad.setId(id); // asegurar consistencia del id

        usuariosById.put(id, entidad);
        return true;
    }

    @Override
    public boolean remove(String id) {
        if (id == null || id.isBlank()) return false;
        return usuariosById.remove(id.trim()) != null;
    }

    @Override
    public boolean update(UsuarioDTO dto, String id) {
        if (dto == null || id == null || id.isBlank()) return false;

        String key = id.trim();
        if (!usuariosById.containsKey(key)) return false;

        Usuario nuevo = DataMapper.usuarioDTOToUsuario(dto);
        nuevo.setId(key); // NO permitir cambiar el id

        usuariosById.put(key, nuevo);
        return true;
    }

    @Override
    public UsuarioDTO get(String id) {
        Usuario u = find(id);
        return (u == null) ? null : DataMapper.usuarioToUsuarioDTO(u);
    }

    @Override
    public Usuario find(String id) {
        if (id == null || id.isBlank()) return null;
        return usuariosById.get(id.trim());
    }

    @Override
    public List<UsuarioDTO> getAll() {
        List<UsuarioDTO> out = new ArrayList<>();
        for (Usuario u : usuariosById.values()) {
            out.add(DataMapper.usuarioToUsuarioDTO(u));
        }
        return out;
    }

    /** Carga desde CSV (si el CSV viejo trae columna "libros", se ignora). */
    public void load() {
        usuariosById.clear();

        try {
            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath.getParent());
                Files.writeString(
                        filePath,
                        HEADER + System.lineSeparator(),
                        StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING
                );
                return;
            }

            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            if (lines.isEmpty()) return;

            int start = 0;
            if (lines.get(0).toLowerCase().startsWith("id" + SEP)) start = 1; // omitir header

            for (int i = start; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line == null || line.trim().isEmpty()) continue;

                Usuario u = parseUsuario(line);
                if (u != null && u.getId() != null && !u.getId().isBlank()) {
                    usuariosById.put(u.getId().trim(), u);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error cargando " + FILE_NAME, e);
        }
    }

    /** Persiste a CSV (sin libros). */
    public void persist() {
        try {
            Files.createDirectories(filePath.getParent());

            StringBuilder sb = new StringBuilder();
            sb.append(HEADER).append(System.lineSeparator());

            for (Usuario u : usuariosById.values()) {
                sb.append(escape(u.getId())).append(SEP)
                        .append(escape(u.getNombre())).append(SEP)
                        .append(escape(u.getApellido())).append(SEP)
                        .append(escape(String.valueOf(u.getFechaNacimiento()))).append(SEP)
                        .append(escape(u.getEmail())).append(SEP)
                        .append(escape(u.getTelefono())).append(SEP)
                        .append(escape(u.getDireccion())).append(SEP)
                        .append(u.isEstadoCuenta()).append(SEP)
                        .append(escape(String.valueOf(u.getFechaRegistro())))
                        .append(System.lineSeparator());
            }

            Files.writeString(
                    filePath,
                    sb.toString(),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );

        } catch (IOException e) {
            throw new RuntimeException("Error persistiendo " + FILE_NAME, e);
        }
    }

    /**
     * Acepta 2 esquemas:
     * - NUEVO:  id;nombre;apellido;fechaNacimiento;email;telefono;direccion;estadoCuenta;fechaRegistro
     * - VIEJO:  id;...;estadoCuenta;libros;fechaRegistro  -> libros se ignora
     */
    private Usuario parseUsuario(String line) {
        String[] c = splitSafe(line);

        if (c.length < 8) return null;

        // viejo: ...;estadoCuenta;libros;fechaRegistro (>=10)
        // nuevo: ...;estadoCuenta;fechaRegistro (>=9)
        String fechaRegistro = "";
        if (c.length >= 10) fechaRegistro = c[9];
        else if (c.length >= 9) fechaRegistro = c[8];

        Usuario u = new Usuario();
        u.setId(unescape(c[0]));
        u.setNombre(unescape(c[1]));
        u.setApellido(unescape(c[2]));

        // Si en tu modelo son LocalDate:
        // u.setFechaNacimiento(LocalDate.parse(unescape(c[3])));
        u.setFechaNacimiento(LocalDate.parse(unescape(c[3])));

        u.setEmail(unescape(c[4]));
        u.setTelefono(unescape(c[5]));
        u.setDireccion(unescape(c[6]));
        u.setEstadoCuenta(Boolean.parseBoolean(c[7]));

        // Si en tu modelo es LocalDate:
        // u.setFechaRegistro(LocalDate.parse(unescape(fechaRegistro)));
        u.setFechaRegistro(LocalDate.parse(unescape(fechaRegistro)));

        return u;
    }

    // --- CSV helpers (soporta comillas si aparecen) ---

    private String[] splitSafe(String line) {
        // Como separador real es ';', esta versión es suficiente si NO hay ';' dentro de campos.
        // Si tus campos pueden tener ';', te paso un parser completo.
        return line.split(SEP, -1);
    }

    private String escape(String s) {
        if (s == null) return "";
        boolean needQuotes = s.contains(SEP) || s.contains("\"") || s.contains("\n") || s.contains("\r");
        String v = s.replace("\"", "\"\"");
        return needQuotes ? "\"" + v + "\"" : v;
    }

    private String unescape(String s) {
        if (s == null) return "";
        String v = s.trim();
        if (v.startsWith("\"") && v.endsWith("\"") && v.length() >= 2) {
            v = v.substring(1, v.length() - 1).replace("\"\"", "\"");
        }
        return v;
    }
}

