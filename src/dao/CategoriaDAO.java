package dao;

import conexion.DatabaseConnection;
import modelo.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa la categoría a la que pertenece el libro.
 */

public class CategoriaDAO {

    public boolean guardar(Categoria categoria) {

        String sql = "INSERT INTO categorias (nombre) VALUES (?)";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, categoria.getNombre());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al guardar categoría: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene la lista de categorías registradas.
     * @return lista de categorías registradas.
     */
    public List<Categoria> listarTodos() {
        List<Categoria> categorias = new ArrayList<>();

        String sql = "SELECT idCategoria, nombre FROM categorias";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idCategoria = rs.getInt("idCategoria");
                String nombre = rs.getString("nombre");


                Categoria categoria = new Categoria(idCategoria, nombre);
                categorias.add(categoria);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar las categorías: " + e.getMessage());
        }

        return categorias;
    }
    /**
     * Actualiza los datos registrados en la lista
     * @param categoria Categoría a la que puede pertenecer un libro.
     * @return true si fue posible la actualización, de lo contrario false.
     */

    public boolean actualizar(Categoria categoria) {

        String sql = "UPDATE categorias SET nombre =? WHERE idCategoria =?";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, categoria.getNombre());
            ps.setInt(2, categoria.getIdCategoria());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar la categorías: " + e.getMessage());
            return false;
        }
    }
    /**
     * Elimina elementos de la lista a partir de su id.
     * @param idCategoria Número de identificación del libro.
     * @return true si se eliminó correctamente, de lo contrario false.
     */
    public boolean eliminar(int idCategoria) {
        String sql = "DELETE FROM categorias WHERE idCategoria =?";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idCategoria);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar la categoría: " + e.getMessage());
            return false;
        }
    }
}

