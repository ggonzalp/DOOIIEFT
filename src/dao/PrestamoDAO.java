package dao;

import conexion.DatabaseConnection;
import modelo.Estudiante;
import modelo.Libro;
import modelo.Prestamo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa el préstamo de un libro.
 */
public class PrestamoDAO {

    /**
     * Constructor de la clase PrestamoDAO
     *
     * @param prestamo Representa el préstamo de un libro.
     * @return true si la fila se registró correctamente, de lo contrario false.
     */
    public boolean guardar(Prestamo prestamo) {
        String sql = "INSERT INTO prestamos (idEstudiante, idLibro," +
                "fechaPrestamo, fechaDevolucion, devuelto) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, prestamo.getEstudiante().getIdEstudiante());
            ps.setInt(2, prestamo.getLibro().getIdLibro());
            ps.setString(3, prestamo.getFechaPrestamo());
            ps.setString(4, prestamo.getFechaDevolucion());
            ps.setBoolean(5, prestamo.isDevuelto());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar préstamo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene la lista de préstamos registrados.
     *
     * @return Lista de préstamos registrados; vacía si no hay préstamos o si ocurre un error.
     */
    public List<Prestamo> listarTodos() {
        List<Prestamo> prestamos = new ArrayList<>();

        String sql = "SELECT idPrestamo, idEstudiante, idLibro, fechaPrestamo, fechaDevolucion, devuelto FROM prestamos";

        try (Connection conexion = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idPrestamo = rs.getInt("idPrestamo");
                int idEstudiante = rs.getInt("idEstudiante");
                int idLibro = rs.getInt("idLibro");
                String fechaPrestamo = rs.getString("fechaPrestamo");
                String fechaDevolucion = rs.getString("fechaDevolucion");
                boolean devuelto = rs.getBoolean("Devuelto");

                Estudiante estudiante = new Estudiante(idEstudiante, "", null, "", "");
                Libro libro = new Libro(idLibro, "", "", 0, "", 0, null);
                Prestamo prestamo = new Prestamo(idPrestamo, estudiante, libro, fechaPrestamo, fechaDevolucion, devuelto);
                prestamos.add(prestamo);

            }
        } catch (SQLException e) {
            System.out.println("Error al listar préstamos: " + e.getMessage());
        }

        return prestamos;
    }

    /**
     * Actualiza los datos registrados en la lista
     *
     * @param prestamo Préstamo de un libro.
     * @return true si fue posible la actualización, de lo contrario false.
     */
    public boolean actualizar(Prestamo prestamo) {

        String sql = "UPDATE prestamos SET idEstudiante =?, idLibro =?, fechaPrestamo =?, fechaDevolucion =?, devuelto =? WHERE idPrestamo =?";

        try (Connection conexion = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, prestamo.getEstudiante().getIdEstudiante());
            ps.setInt(2, prestamo.getLibro().getIdLibro());
            ps.setString(3, prestamo.getFechaPrestamo());
            ps.setString(4, prestamo.getFechaDevolucion());
            ps.setBoolean(5, prestamo.isDevuelto());
            ps.setInt(6, prestamo.getIdPrestamo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar el libro: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina elementos de la lista a partir de su id.
     *
     * @param idPrestamo Número de identificación del libro.
     * @return true si se eliminó correctamente, de lo contrario false.
     */
    public boolean eliminar(int idPrestamo) {

        String sql = "DELETE FROM prestamos WHERE idPrestamo = ?";

        try (Connection conexion = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idPrestamo);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar préstamo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene los préstamos de un estudiante.
     * @param idEstudiante Número de identificación de un estudiante
     * @return Lista de préstamos del estudiante.
     */

    public List<Prestamo> listarPorEstudiante(int idEstudiante) {
        List<Prestamo> prestamos = new ArrayList<>();

        String sql = "SELECT prestamos.idPrestamo, prestamos.idEstudiante, prestamos.idLibro, libros.titulo, prestamos.fechaPrestamo, prestamos.fechaDevolucion, prestamos.devuelto " +
                "FROM prestamos " +
                "JOIN libros ON prestamos.idLibro = libros.idLibro " +
                "WHERE prestamos.idEstudiante = ? " +
                "ORDER BY prestamos.fechaPrestamo DESC, prestamos.idPrestamo DESC";

        try (Connection conexion = DatabaseConnection.getInstance().getConnection();
        PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idEstudiante);

            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Estudiante estudiante = new Estudiante(rs.getInt("idEstudiante"), "", null, "", "");
                    Libro libro = new Libro(rs.getInt("idLibro"), rs.getString("titulo"), "", 0, "", 0, null);
                    Prestamo prestamo = new Prestamo(rs.getInt("idPrestamo"), estudiante, libro, rs.getString("fechaPrestamo"), rs.getString("fechaDevolucion"), rs.getBoolean("devuelto"));

                    prestamos.add(prestamo);

                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar historial: " + e.getMessage());
        }

        return prestamos;
    }
}
