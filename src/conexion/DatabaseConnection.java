package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/dooiieft";
    private static final String USER = "GGP";
    private static final String PASSWORD = "BaseDatos2026";

    // La única instancia de la clase. Se crea la primera vez que alguien la pide.
    private static DatabaseConnection instancia;


    private DatabaseConnection() {
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instancia == null) {
            instancia = new DatabaseConnection();
        }
        return instancia;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}