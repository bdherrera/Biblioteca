package co.edu.unbosque.model.persistence.dao;

import java.util.List;
import co.edu.unbosque.model.Devolucion;
import co.edu.unbosque.model.dto.DevolucionDTO;
import co.edu.unbosque.model.persistence.DataMapper;

import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class DevolucionDAO implements OperationDAO<DevolucionDTO, Devolucion> {

    private final List<Devolucion> devoluciones = new ArrayList<>();
    private static final String FILE_NAME = "Devoluciones.csv";
    private static final String SEP = ";";

    public DevolucionDAO() { load(); }

    @Override
    public boolean add(DevolucionDTO dto) {
        Devolucion e = DataMapper.devolucionDTOToDevolucion(dto);
        if (find(e.getId()) != null) return false;
        devoluciones.add(e);
        return true;
    }

    @Override
    public boolean remove(String id) {
        Devolucion d = find(id);
        if (d == null) return false;
        devoluciones.remove(d);
        return true;
    }

    @Override
    public boolean update(DevolucionDTO dto, String id) {
        Devolucion old = find(id);
        if (old == null) return false;

        Devolucion nuevo = DataMapper.devolucionDTOToDevolucion(dto);
        nuevo.setId(id);
        devoluciones.remove(old);
        devoluciones.add(nuevo);
        return true;
    }

    @Override
    public DevolucionDTO get(String id) {
        Devolucion d = find(id);
        return d == null ? null : DataMapper.devolucionToDevolucionDTO(d);
    }

    @Override
    public Devolucion find(String id) {
        for (Devolucion d : devoluciones) if (d.getId().equals(id)) return d;
        return null;
    }

    @Override
    public List<DevolucionDTO> getAll() {
        List<DevolucionDTO> out = new ArrayList<>();
        for (Devolucion d : devoluciones) out.add(DataMapper.devolucionToDevolucionDTO(d));
        return out;
    }

    // Extra: buscar devolución por préstamo (1 a 1)
    public Devolucion findByPrestamo(String idPrestamo) {
        for (Devolucion d : devoluciones) {
            if (d.getIdPrestamo().equals(idPrestamo)) return d;
        }
        return null;
    }

    public void load() {
        devoluciones.clear();
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
            // Detectar header
            if (lines.get(0).toLowerCase().contains("fechadevolucion") ||
                    lines.get(0).toLowerCase().contains("idprestamo")) {
                start = 1;
            }

            for (int i = start; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line == null || line.trim().isEmpty()) continue;

                String[] c = line.split(SEP, -1);

                // Validación mínima
                if (c.length < 6) continue;

                // id;idPrestamo;fechaDevolucion;multa;estadoLibro;comentarios
                Devolucion d = new Devolucion(
                        c[0].trim(),
                        c[1].trim(),
                        LocalDate.parse(c[2].trim()),
                        Float.parseFloat(c[3].trim()),
                        co.edu.unbosque.model.EstadoLibro.valueOf(c[4].trim()),
                        c[5].trim()
                );

                devoluciones.add(d);
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
            sb.append("id;idPrestamo;fechaDevolucion;multa;estadoLibro;comentarios\n");

            for (Devolucion d : devoluciones) {
                sb.append(d.getId()).append(SEP)
                        .append(d.getIdPrestamo()).append(SEP)
                        .append(d.getFechaDevolucion()).append(SEP)
                        .append(d.getMulta()).append(SEP)
                        .append(d.getEstadoLibro().name()).append(SEP)
                        .append(d.getComentarios() == null ? "" : d.getComentarios())
                        .append("\n");
            }

            Files.writeString(path, sb.toString(),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        } catch (Exception e) {
            throw new RuntimeException("Error persistiendo " + FILE_NAME, e);
        }
    }

}
