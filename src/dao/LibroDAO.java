package dao;

import conexion.DatabaseConnection;

import modelo.Categoria;
import modelo.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    /**
     * Inserta un libro en la base de datos
     *
     * @param libro Corresponde a un libro, objeto que puede ser prestado.
     * @return true si la fila se insertó correctamente, de lo contrario devuelve false.
     */

    public boolean guardar(Libro libro) {
        String sql = "INSERT INTO libros (titulo, autor, isbn, editorial, stock, idCategoria) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conexion = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setInt(3, libro.getIsbn());
            ps.setString(4, libro.getEditorial());
            ps.setInt(5, libro.getStock());
            ps.setInt(6, libro.getCategoria().getIdCategoria());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al guardar libro: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene la lista de todos los libros registrados.
     * @return Lista de libros registrados; vacía si no hay libros o si ocurre un error.
     */
    public List<Libro> listarTodos() {
        List<Libro> libros = new ArrayList<>();

        String sql = "SELECT libro.idLibro, libro.titulo, libro.autor, libro.isbn, libro.editorial, libro.stock, libro.idCategoria, categoria.nombre FROM libros libro JOIN categorias categoria ON libro.idCategoria = categoria.idCategoria";

        try (Connection conexion = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idLibro = rs.getInt("idLibro");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                int isbn = rs.getInt("isbn");
                String editorial = rs.getString("editorial");
                int stock = rs.getInt("stock");
                int idCategoria = rs.getInt("idCategoria");

                Categoria categoria = new Categoria(idCategoria, rs.getString("nombre"));
                Libro libro = new Libro(idLibro, titulo, autor, isbn, editorial, stock, categoria);
                libros.add(libro);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar libros: " + e.getMessage());
        }

        return libros;
    }

    /**
     * Actualiza los datos registrados en la lista
     * @param libro Libro que puede ser prestado.
     * @return true si fue posible la actualización, de lo contrario false.
     */
    public boolean actualizar(Libro libro) {

        String sql = "UPDATE libros SET titulo =?, autor =?, isbn =?, editorial =?, stock =?, idCategoria =? WHERE idLibro =?";

        try (Connection conexion = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setInt(3, libro.getIsbn());
            ps.setString(4, libro.getEditorial());
            ps.setInt(5, libro.getStock());
            ps.setInt(6, libro.getCategoria().getIdCategoria());
            ps.setInt(7, libro.getIdLibro());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar el libro: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina elementos de la lista a partir de su id.
     * @param idLibro Número de identificación del libro.
     * @return true si se eliminó correctamente, de lo contrario false.
     */
    public boolean eliminar(int idLibro) {

        String sql = "DELETE FROM libros WHERE idLibro= ?";

        try (Connection conexion = DatabaseConnection.getInstance().getConnection();
            PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idLibro);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar libro: " + e.getMessage());
            return false;
        }
    }

    public boolean cambiarStock(int idLibro, int cantidad) {
        String sql = "UPDATE libros SET stock = stock + ? WHERE idLibro =?";

        try (Connection conexion = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, cantidad);
            ps.setInt(2, idLibro);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al cambiar el stock: " + e.getMessage());
            return false;
        }
    }

    public int obtenerStock(int idLibro) {
        String sql = "SELECT stock FROM libros WHERE idLibro = ?";

        try (Connection conexion = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idLibro);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("stock");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener el stock: " + e.getMessage());
        }

        return 0;
    }
}
