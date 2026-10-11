package dao;

import conexion.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que genera reportes con la información almacenada en la base de datos.
 */
public class ReporteDao {

    public List<Object[]> prestamosActivos() {
        String sql = "SELECT estudiante.nombre, estudiante.curso, libro.titulo, prestamo.fechaPrestamo, prestamo.fechaDevolucion " +
                "FROM prestamos prestamo " +
                "JOIN estudiantes estudiante ON prestamo.idEstudiante = estudiante.idEstudiante " +
                "JOIN libros libro ON prestamo.idLibro = libro.idLibro " +
                "WHERE prestamo.devuelto = FALSE " +
                "ORDER BY prestamo.fechaDevolucion";
        return consultar(sql);
    }

    public List<Object[]> prestamosAtrasados() {
        String sql = "SELECT estudiante.nombre, estudiante.curso, libro.titulo, prestamo.fechaDevolucion, DATEDIFF(CURDATE(), prestamo.fechaDevolucion) AS diasAtraso " +
                "FROM prestamos prestamo " +
                "JOIN estudiantes estudiante ON prestamo.idEstudiante = estudiante.idEstudiante " +
                "JOIN libros libro ON prestamo.idLibro = libro.idLibro " +
                "WHERE prestamo.devuelto = FALSE AND prestamo.fechaDevolucion < CURDATE() " +
                "ORDER BY diasAtraso DESC";
        return consultar(sql);
    }

    public List<Object[]> librosMasPrestados() {
        String sql = "SELECT libro.titulo, categoria.nombre, COUNT(prestamo.idPrestamo) AS vecesPrestado " +
                "FROM libros libro " +
                "JOIN categorias categoria ON libro.idCategoria = categoria.idCategoria " +
                "LEFT JOIN prestamos prestamo ON prestamo.idLibro = libro.idLibro " +
                "GROUP BY libro.idLibro, libro.titulo, categoria.nombre " +
                "ORDER BY vecesPrestado DESC, libro.titulo";
        return consultar(sql);
    }

    private List<Object[]> consultar(String sql) {
        List<Object[]> filas = new ArrayList<>();

        try (Connection conexion = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int columnas = rs.getMetaData().getColumnCount();

            while (rs.next()) {
                Object[] fila = new Object[columnas];
                for(int i = 0; i < columnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                filas.add(fila);
            }

        } catch (SQLException e) {
            System.out.println("Error en el reporte: " + e.getMessage());
        }

        return filas;
    }

}
