package co.edu.unbosque.model.persistence.dao;

import co.edu.unbosque.model.Prestamo;
import co.edu.unbosque.model.dto.PrestamoDTO;
import co.edu.unbosque.model.persistence.DataMapper;

import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class PrestamoDAO implements OperationDAO<PrestamoDTO, Prestamo> {

    private final List<Prestamo> prestamos = new ArrayList<>();
    private static final String FILE_NAME = "Prestamos.csv";
    private static final String SEP = ";";

    public PrestamoDAO() { load(); }

    @Override
    public boolean add(PrestamoDTO dto) {
        Prestamo e = DataMapper.prestamoDTOToPrestamo(dto);
        if (find(e.getId()) != null) return false;
        prestamos.add(e);
        return true;
    }

    @Override
    public boolean remove(String id) {
        Prestamo p = find(id);
        if (p == null) return false;
        prestamos.remove(p);
        return true;
    }

    @Override
    public boolean update(PrestamoDTO dto, String id) {
        Prestamo old = find(id);
        if (old == null) return false;

        Prestamo nuevo = DataMapper.prestamoDTOToPrestamo(dto);
        nuevo.setId(id);
        prestamos.remove(old);
        prestamos.add(nuevo);
        return true;
    }

    @Override
    public PrestamoDTO get(String id) {
        Prestamo p = find(id);
        return p == null ? null : DataMapper.prestamoToPrestamoDTO(p);
    }

    @Override
    public Prestamo find(String id) {
        for (Prestamo p : prestamos) if (p.getId().equals(id)) return p;
        return null;
    }

    @Override
    public List<PrestamoDTO> getAll() {
        List<PrestamoDTO> out = new ArrayList<>();
        for (Prestamo p : prestamos) out.add(DataMapper.prestamoToPrestamoDTO(p));
        return out;
    }

    // Extra: listar por usuario
    public List<Prestamo> findByUsuario(String idUsuario) {
        List<Prestamo> out = new ArrayList<>();
        for (Prestamo p : prestamos) if (p.getIdUsuario().equals(idUsuario)) out.add(p);
        return out;
    }

    public void load() {
        prestamos.clear();
        try {
            Path path = Paths.get("src", "archivos", FILE_NAME);
            Files.createDirectories(path.getParent());
            if (!Files.exists(path)) {
                Files.createFile(path);
                return;
            }

            List<String> lines = Files.readAllLines(path);
            if (lines.isEmpty()) return;

            int start = 0;
            // Detectar header (primera fila)
            if (lines.get(0).toLowerCase().contains("fechaprestamo") ||
                    lines.get(0).toLowerCase().contains("idusuario")) {
                start = 1;
            }

            for (int i = start; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line == null || line.trim().isEmpty()) continue;

                String[] c = line.split(SEP, -1);

                // Validación mínima para evitar ArrayIndexOutOfBounds
                if (c.length < 6) continue;

                // id;idUsuario;idLibro;fechaPrestamo;fechaVencimiento;estado
                Prestamo p = new Prestamo(
                        c[0].trim(), c[1].trim(), c[2].trim(),
                        LocalDate.parse(c[3].trim()),
                        LocalDate.parse(c[4].trim()),
                        co.edu.unbosque.model.EstadoPrestamo.valueOf(c[5].trim())
                );

                prestamos.add(p);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error cargando " + FILE_NAME, e);
        }
    }


    public void persist() {
        try {
            Path path = Paths.get("src", "archivos", FILE_NAME);
            Files.createDirectories(path.getParent());

            StringBuilder sb = new StringBuilder();
            sb.append("id;idUsuario;idLibro;fechaPrestamo;fechaVencimiento;estado\n");

            for (Prestamo p : prestamos) {
                sb.append(p.getId()).append(SEP)
                        .append(p.getIdUsuario()).append(SEP)
                        .append(p.getIdLibro()).append(SEP)
                        .append(p.getFechaPrestamo()).append(SEP)
                        .append(p.getFechaVencimiento()).append(SEP)
                        .append(p.getEstado().name())
                        .append("\n");
            }

            Files.writeString(path, sb.toString(),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        } catch (Exception e) {
            throw new RuntimeException("Error persistiendo " + FILE_NAME, e);
        }
    }

}
