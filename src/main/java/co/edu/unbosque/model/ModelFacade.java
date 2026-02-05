package co.edu.unbosque.model;

import co.edu.unbosque.model.persistence.dao.DevolucionDAO;
import co.edu.unbosque.model.persistence.dao.LibroDAO;
import co.edu.unbosque.model.persistence.dao.PrestamoDAO;
import co.edu.unbosque.model.persistence.dao.UsuarioDAO;
import co.edu.unbosque.model.services.PrestamoService;

/**
 * Facade: punto único de acceso al modelo (servicios + persistencia).
 */
public class ModelFacade {

    private final UsuarioDAO usuarioDAO;
    private final LibroDAO libroDAO;
    private final PrestamoDAO prestamoDAO;
    private final DevolucionDAO devolucionDAO;

    private final PrestamoService prestamoService;

    public ModelFacade() {
        usuarioDAO = new UsuarioDAO();
        libroDAO = new LibroDAO();
        prestamoDAO = new PrestamoDAO();
        devolucionDAO = new DevolucionDAO();

        prestamoService = new PrestamoService(usuarioDAO, libroDAO, prestamoDAO, devolucionDAO);
    }

    public UsuarioDAO getUsuarioDAO() { return usuarioDAO; }
    public LibroDAO getLibroDAO() { return libroDAO; }
    public PrestamoDAO getPrestamoDAO() { return prestamoDAO; }
    public DevolucionDAO getDevolucionDAO() { return devolucionDAO; }

    public PrestamoService getPrestamoService() { return prestamoService; }

    public void load() {
        usuarioDAO.load();
        libroDAO.load();
        prestamoDAO.load();
        devolucionDAO.load();
    }

    public void persist() {
        usuarioDAO.persist();
        libroDAO.persist();
        prestamoDAO.persist();
        devolucionDAO.persist();
    }
}
