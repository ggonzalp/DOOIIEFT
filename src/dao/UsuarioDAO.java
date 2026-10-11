package dao;

import conexion.DatabaseConnection;
import modelo.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que permite el acceso a los datos de la tabla usuarios.
 */
public class UsuarioDAO {


    /**
     * Busca a un usuario por correo y contraseña. Se usa en el inicio de sesión.
     * @param correo Correo del usuario.
     * @param contrasena Contraseña del usuario.
     * @returnel usuario si los datos coinciden; null si no existe o si hay error.
     */

    public Usuario autenticar(String correo, String contrasena) {
        String sql = "SELECT idUsuario, nombreUsuario, rut, correo, contrasena, rol FROM usuarios WHERE correo =? AND BINARY contrasena =?";

        try (Connection conexion = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, correo);
            ps.setString(2, contrasena);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("idUsuario"),
                            rs.getString("nombreUsuario"),
                            new Rut(rs.getString("rut")),
                            rs.getString("correo"),
                            rs.getString("contrasena"),
                            rs.getString("rol"));
                }
            }
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error al autenticar usuario: " + e.getMessage());
        }
        return null;
    }

    /**
     * Constructor de la clase Usuario
     *
     * @param usuario Usuario que solicita el préstamo de un libro, puede ser personal administrativo
     * @return true si la fila se registró correctamente, de lo contrario false.
     */
    public boolean guardar(Usuario usuario) {

        //El id de usuario es generado en MySQL con AUTO_INCREMENT.

        String sql = "INSERT INTO usuarios (nombreUsuario, rut, correo, contrasena, rol) VALUES(?,?,?,?,?)";

        try (Connection conexion = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getRut().getNumero());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getContrasena());
            ps.setString(5, usuario.getRol());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene la lista de préstamos registrados.
     *
     * @return Lista de préstamos registrados; vacía si no hay préstamos o si ocurre un error.
     */
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT idUsuario, nombreUsuario, rut, correo, contrasena, rol FROM usuarios";

        try (Connection conexion = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idUsuario = rs.getInt("idUsuario");
                String nombreUsuario = rs.getString("nombreUsuario");
                String numeroRut = rs.getString("rut");
                String correo = rs.getString("correo");
                String contrasena = rs.getString("contrasena");
                String rol = rs.getString("rol");

                Rut rut = new Rut(numeroRut);
                Usuario usuario = new Usuario(idUsuario, nombreUsuario, rut, correo, contrasena, rol);

                usuarios.add(usuario);
            }

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }

        return usuarios;
    }

    /**
     * Actualiza los datos registrados en la lista
     *
     * @param usuario Usuario que administra y puede solicitar un préstamo.
     * @return true si fue posible la actualización, de lo contrario false.
     */
    public boolean actualizar(Usuario usuario) {

        String sql = "UPDATE usuarios SET nombreUsuario =?, rut =?, correo =?, contrasena =?, rol =? WHERE idUsuario =?";

        try (Connection conexion = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getRut().getNumero());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getContrasena());
            ps.setString(5, usuario.getRol());
            ps.setInt(6, usuario.getIdUsuario());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar el usuario: " + e.getMessage());
            return false;
        }
    }


    /**
     * Elimina elementos de la lista a partir de su id.
     *
     * @param idUsuario Número de identificación del libro.
     * @return true si se eliminó correctamente, de lo contrario false.
     */
    public boolean eliminar(int idUsuario) {

        String sql = "DELETE FROM usuarios WHERE idUsuario = ?";

        try (Connection conexion = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

}
