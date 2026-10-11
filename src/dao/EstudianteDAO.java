package dao;

import conexion.DatabaseConnection;
import modelo.Estudiante;
import modelo.Rut;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa a un estudiante que puede solicitar un préstamo.
 */

public class EstudianteDAO {

    /**
     * Constructor de la clase EstudianteDAO
     *
     * @param estudiante Representa a un estudiante que uede solicitar el préstamo de un libro.
     * @return true si la fila se registró correctamente, de lo contrario false.
     */
    public boolean guardar(Estudiante estudiante) {
        String sql = "INSERT INTO estudiantes (nombre, rut, curso, correo) VALUES (?, ?, ?, ?)";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getRut().getNumero());
            ps.setString(3, estudiante.getCurso());
            ps.setString(4, estudiante.getCorreo());


            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar estudiante: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene la lista de estudiantes registrados.
     *
     * @return Lista de estudiantes registrados; vacía si no hay préstamos o si ocurre un error.
     */
    public List<Estudiante> listarTodos() {
        List<Estudiante> estudiantes = new ArrayList<>();

        String sql = "SELECT idEstudiante, nombre, rut, curso, correo FROM estudiantes";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idEstudiante = rs.getInt("idEstudiante");
                String nombreEstudiante = rs.getString("nombre");
                String numeroRut = rs.getString("rut");
                String curso = rs.getString("curso");
                String correo = rs.getString("correo");

                Rut rut = new Rut(numeroRut);
                Estudiante estudiante = new Estudiante(idEstudiante, nombreEstudiante, rut, curso, correo);
                estudiantes.add(estudiante);
            }
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error al listar estudiantes: " + e.getMessage());
        }

        return estudiantes;
    }

    /**
     * Actualiza los datos registrados en la lista
     *
     * @param estudiante Estudiante que puede solicitar un préstamo.
     * @return true si fue posible la actualización, de lo contrario false.
     */
    public boolean actualizar(Estudiante estudiante) {

        String sql = "UPDATE estudiantes SET nombre =?, rut =?, curso =?, correo =? WHERE idEstudiante =?";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getRut().getNumero());
            ps.setString(3, estudiante.getCurso());
            ps.setString(4, estudiante.getCorreo());
            ps.setInt(5, estudiante.getIdEstudiante());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar el estudiante: " + e.getMessage());
            return false;
        }
    }


    /**
     * Elimina elementos de la lista a partir de su id.
     * @param idEstudiante Número de identificación del libro.
     * @return true si se eliminó correctamente, de lo contrario false.
     */
    public boolean eliminar(int idEstudiante) {

        String sql = "DELETE FROM estudiantes WHERE idEstudiante = ?";

        try (Connection conexion = DatabaseConnection.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idEstudiante);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar estudiante: " + e.getMessage());
            return false;
        }
    }
}
