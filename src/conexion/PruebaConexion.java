package conexion;

import java.sql.Connection;
import java.sql.SQLException;

public class PruebaConexion {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();

            System.out.println("Válida: " + conn.isValid(2));

            System.out.println("BD: " + conn.getCatalog());


            } catch (SQLException e) {
            System.out.println("Falló: " + e.getMessage());


        }
    }
}
