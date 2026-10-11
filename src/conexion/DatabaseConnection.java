package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Establece conexión entre la aplicación y MySQL.
 */

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/dooiieft";

    private static final String USER = "GGP";
    private static final String PASSWORD = "BaseDatos2026";

    /**
     * Metodo conectar
     * @return una conexión activa.
     * @throws SQLException
     */
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
