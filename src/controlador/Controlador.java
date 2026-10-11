package controlador;

import dao.*;
import modelo.*;

import java.time.LocalDate;
import java.util.List;

public class Controlador {

    private static final int DIAS_PRESTAMO = 7;

    private LibroDAO libroDAO = new LibroDAO();
    private EstudianteDAO estudianteDAO = new EstudianteDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private PrestamoDAO prestamoDAO = new PrestamoDAO();
    private CategoriaDAO categoriaDAO = new CategoriaDAO();

    //=================
    //      LOGIN
    //=================

    /**
     * Valida las credenciales ingresadas por el usuario.
     *
     * @param correo     Correo electrónico del usuario.
     * @param contrasena Contraseña del usuario.
     * @return el usuario autenticado; null si los datos están vacíos o no coinciden.
     */
    public Usuario iniciarSesion(String correo, String contrasena) {
        if (correo == null || correo.isBlank() || contrasena == null || contrasena.isBlank()) {
            return null;
        }
        return usuarioDAO.autenticar(correo.trim(), contrasena);
    }

    public boolean esBibliotecario(Usuario usuario) {
        return usuario != null && "bibliotecario".equalsIgnoreCase(usuario.getRol());
    }

    //=================
    //    CATEGORIA
    //=================


    /**
     * Registra una categoría nueva
     *
     * @param nombre Nombre de la categoría.
     * @return true si se registró, false si no.
     */
    public boolean registrarCategoria(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return false;
        }
        return categoriaDAO.guardar(new Categoria(0, nombre.trim()));
    }

    /**
     * Obtiene las categorías.
     *
     * @return lista de categorías registradas.
     */
    public List<Categoria> obtenerCategorias() {
        return categoriaDAO.listarTodos();
    }

    /**
     * Modifica el nombre de una categoría
     *
     * @param idCategoria Número identificador de la categoría.
     * @param nombre      Nombre de la categoría.
     * @return true si se actualizó, false si no.
     */

    public boolean editarCategoria(int idCategoria, String nombre) {
        if (idCategoria <= 0 || nombre == null || nombre.isBlank()) {
            return false;
        }
        return categoriaDAO.actualizar(new Categoria(idCategoria, nombre.trim()));
    }

    /**
     * Elimina una categoría
     *
     * @param idCategoria Número identificador de la categoría.
     * @return true si se eliminó, false si no.
     */
    public boolean eliminarCategoria(int idCategoria) {
        if (idCategoria <= 0) {
            return false;
        }
        return categoriaDAO.eliminar(idCategoria);
    }

    //=================
    //      LIBRO
    //=================

    /**
     * Metodo que registra un libro nuevo.
     *
     * @param titulo    Título de un libro.
     * @param autor     Autor de un libro.
     * @param isbn      Número de publicación isbn de un libro.
     * @param editorial Editorial que publica el libro.
     * @param stock     Cantidad de ejemplares disponibles.
     * @param categoria Identificación de la categoría de un libro.
     * @return true si se registró, false si no lo hizo.
     */
    public boolean registrarLibro(String titulo, String autor, int isbn, String editorial, int stock, Categoria categoria) {

        if (titulo.isBlank() || autor.isBlank() || isbn <= 0 || editorial.isBlank() || stock <= 0 || categoria == null) {

            return false;
        }

        Libro libro = new Libro(0, titulo, autor, isbn, editorial, stock, categoria);
        return libroDAO.guardar(libro);
    }

    /**
     * Metodo obtener libros.
     *
     * @return La lista de libros registrada.
     */
    public List<Libro> obtenerLibro() {
        return libroDAO.listarTodos();
    }

    public boolean editarLibro(int idLibro, String titulo, String autor, int isbn, String editorial, int stock, Categoria categoria) {

        if (idLibro <= 0 || titulo.isBlank() || autor.isBlank() || isbn <= 0 || editorial.isBlank() || stock <= 0 || categoria == null) {
            return false;
        }

        return libroDAO.actualizar(new Libro(idLibro, titulo, autor, isbn, editorial, stock, categoria));
    }

    public boolean eliminar(int idLibro) {
        if (idLibro <= 0) {
            return false;
        }

        return libroDAO.eliminar(idLibro);
    }

    //=================
    //   ESTUDIANTE
    //=================

    public boolean registrarEstudiante(String nombre, String rutTexto, String curso, String correo) {

        if (nombre.isBlank() || rutTexto.isBlank() || curso.isBlank() || correo.isBlank()) {
            return false;
        }

        try {
            Rut rut = new Rut(rutTexto.trim());
            return estudianteDAO.guardar(new Estudiante(0, nombre.trim(), rut, curso.trim(), correo.trim()));
        } catch (IllegalArgumentException e) {
            System.out.println("RUT inválido: " + rutTexto);
            return false;
        }
    }

    /**
     * Metodo obtener estudiantes.
     *
     * @return La lista de estudiantes registrada.
     */
    public List<Estudiante> obtenerEstudiante() {
        return estudianteDAO.listarTodos();
    }

    /**
     * Modifica los datos de un estudiante existente.
     *
     * @param idEstudiante Número identificador de un estudiante.
     * @param nombre       Nombre de un estudiante.
     * @param rutTexto     Rut de un estudiante.
     * @param curso        Curso de un estudiante.
     * @param correo       Correo electrónico de un estudiante.
     * @return true si se actualizó, false si no.
     */
    public boolean editarEstudiante(int idEstudiante, String nombre, String rutTexto, String curso, String correo) {
        if (idEstudiante <= 0 || nombre.isBlank() || curso.isBlank() || correo.isBlank()) {
            return false;
        }
        try {
            Rut rut = new Rut(rutTexto.trim());
            return estudianteDAO.actualizar(new Estudiante(idEstudiante, nombre.trim(), rut, curso.trim(), correo.trim()));
        } catch (IllegalArgumentException e) {
            System.out.println("RUT inválido: " + rutTexto);
            return false;
        }
    }

    public boolean eliminarEstudiante(int idEstudiante) {
        if (idEstudiante <= 0) {
            return false;
        }

        return estudianteDAO.eliminar(idEstudiante);
    }

    //=================
    //   PRÉSTAMO
    //=================

    /**
     * Registra un préstamo.
     *
     * @param estudiante Estudiante que solicita un préstamo.
     * @param libro      Libro que puede ser registrado y prestado.
     * @return true si se registró, false si no.
     */
    public synchronized boolean registrarPrestamo(Estudiante estudiante, Libro libro) {
        if (estudiante == null || libro == null || libro.getStock() <= 0) {
            return false;
        }

        LocalDate hoy = LocalDate.now();
        LocalDate vencimiento = hoy.plusDays(DIAS_PRESTAMO);

        Prestamo prestamo = new Prestamo(0, estudiante, libro, hoy.toString(), vencimiento.toString(), false);

        if (!prestamoDAO.guardar(prestamo)) {
            return false;
        }
        return libroDAO.cambiarStock(libro.getIdLibro(), -1);
    }

    /**
     * Obtiene los préstamos.
     *
     * @return lista de préstamos.
     */
    public List<Prestamo> obtenerPrestamos() {
        return prestamoDAO.listarTodos();
    }

    /**
     * Registra una devolución
     *
     * @param prestamo Préstamo que se devuelve.
     * @return true si se registró, false si no.
     */
    public synchronized boolean registrarDevolucion(Prestamo prestamo) {
        if (prestamo == null || prestamo.isDevuelto()) {
            return false;
        }

        prestamo.setDevuelto(true);

        if (!prestamoDAO.actualizar(prestamo)) {
            prestamo.setDevuelto(false);
            return false;
        }
        return libroDAO.cambiarStock(prestamo.getLibro().getIdLibro(), 1);
    }

    /**
     * Elimina un préstamo.
     *
     * @param idPrestamo Número de identificación del préstamo.
     * @return true si se eliminó, false si no.
     */
    public boolean eliminarPrestamo(int idPrestamo) {
        if (idPrestamo <= 0) {
            return false;
        }
        return prestamoDAO.eliminar(idPrestamo);
    }

    /**
     * Busca a un estudiante por su rut.
     * @param rutTexto Rut ingresado en formato chileno (12345678-9 o K)
     * @return el estudiante o null si no hay registro.
     * @throws IllegalArgumentException si el RUT no tiene un formato válido.
     */

    public Estudiante buscarEstudiantePorRut(String rutTexto) {
        Rut rut = new Rut(rutTexto.trim());

        for (Estudiante estudiante : estudianteDAO.listarTodos()) {
            if (estudiante.getRut().getNumero().equalsIgnoreCase(rut.getNumero())) {
                return estudiante;
            }
        }
        return null;
    }

    /**
     * Obtiene el historial de préstamos de un estudiante.
     * @param estudiante Estudiante que consulta.
     * @return lista de préstamos del estudiante.
     */
    public List<Prestamo> obtenerHistorial(Estudiante estudiante) {
        if (estudiante == null) {
            return List.of();
        }
        return prestamoDAO.listarPorEstudiante(estudiante.getIdEstudiante());
    }
}
