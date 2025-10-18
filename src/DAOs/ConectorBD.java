package DAOs;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectorBD {
    // De forma privada usando el pilar de encapsulamiento
    private static final String URL = "jdbc:mysql://localhost:3306/proyectojava";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Metodo de obtencion de conexion
    public static Connection getConnection() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexion Exitosa");
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return conexion;
    }
}