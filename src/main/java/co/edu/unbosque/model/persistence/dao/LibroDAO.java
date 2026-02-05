package co.edu.unbosque.model.persistence.dao;

import co.edu.unbosque.model.Libro;
import co.edu.unbosque.model.dto.LibroDTO;
import co.edu.unbosque.model.persistence.DataMapper;

import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class LibroDAO implements OperationDAO<LibroDTO, Libro> {

    private final List<Libro> libros = new ArrayList<>();
    private static final String FILE_NAME = "Libros.csv";
    private static final String SEP = ";";

    public LibroDAO() { load(); }

    @Override
    public boolean add(LibroDTO dto) {
        Libro e = DataMapper.libroDTOToLibro(dto);
        if (find(e.getId()) != null) return false;
        libros.add(e);
        return true;
    }

    @Override
    public boolean remove(String id) {
        Libro l = find(id);
        if (l == null) return false;
        // baja lógica (recomendado)
        l.setActivo(false);
        return true;
    }

    @Override
    public boolean update(LibroDTO dto, String id) {
        Libro old = find(id);
        if (old == null) return false;

        Libro nuevo = DataMapper.libroDTOToLibro(dto);
        nuevo.setId(id);
        libros.remove(old);
        libros.add(nuevo);
        return true;
    }

    @Override
    public LibroDTO get(String id) {
        Libro l = find(id);
        return l == null ? null : DataMapper.libroToLibroDTO(l);
    }

    @Override
    public Libro find(String id) {
        for (Libro l : libros) {
            if (l.getId().equals(id)) return l;
        }
        return null;
    }

    @Override
    public List<LibroDTO> getAll() {
        List<LibroDTO> out = new ArrayList<>();
        for (Libro l : libros) out.add(DataMapper.libroToLibroDTO(l));
        return out;
    }

    // Extra: buscar por título (contains, case-insensitive)
    public List<LibroDTO> buscarPorTitulo(String titulo) {
        String q = titulo == null ? "" : titulo.toLowerCase();
        List<LibroDTO> out = new ArrayList<>();
        for (Libro l : libros) {
            if (l.getTitulo() != null && l.getTitulo().toLowerCase().contains(q)) {
                out.add(DataMapper.libroToLibroDTO(l));
            }
        }
        return out;
    }

    // Extra: ajustar disponibles (prestar/devolver)
    public boolean ajustarDisponibles(String idLibro, int delta) {
        Libro l = find(idLibro);
        if (l == null || !l.isActivo()) return false;

        int nuevos = l.getEjemplaresDisponibles() + delta;
        if (nuevos < 0) return false;
        if (nuevos > l.getEjemplaresTotales()) nuevos = l.getEjemplaresTotales();

        l.setEjemplaresDisponibles(nuevos);
        return true;
    }

    public void load() {
        libros.clear();
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
            // Si la primera línea parece header, saltarla
            if (lines.get(0).toLowerCase().contains("ejemplarestotales")) {
                start = 1;
            }

            for (int i = start; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line == null || line.trim().isEmpty()) continue;

                String[] c = line.split(SEP, -1);

                // id;titulo;autor;isbn;ejemplaresTotales;ejemplaresDisponibles;activo;fechaRegistro
                Libro l = new Libro(
                        c[0], c[1], c[2], c[3],
                        Integer.parseInt(c[4].trim()),
                        Integer.parseInt(c[5].trim()),
                        Boolean.parseBoolean(c[6].trim()),
                        LocalDate.parse(c[7].trim())
                );

                libros.add(l);
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
            for (Libro l : libros) {
                sb.append(l.getId()).append(SEP)
                        .append(l.getTitulo()).append(SEP)
                        .append(l.getAutor()).append(SEP)
                        .append(l.getIsbn()).append(SEP)
                        .append(l.getEjemplaresTotales()).append(SEP)
                        .append(l.getEjemplaresDisponibles()).append(SEP)
                        .append(l.isActivo()).append(SEP)
                        .append(l.getFechaRegistro())
                        .append("\n");
            }
            Files.writeString(path, sb.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException("Error persistiendo " + FILE_NAME, e);
        }
    }
}
