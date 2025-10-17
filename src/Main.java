import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // Usar try-with-resources para asegurar que la conexión se cierre
        try (Connection conn = ConectorBD.getConnection()) {
            if (conn == null) {
                System.out.println("Error al conectar a la base de datos");
                return; // Salir si no hay conexión
            }
            System.out.println("Se pudo Conectar la base de datos a java");
        } catch (SQLException e) {
            // Manejar la excepción de obtención o cierre de la conexión
            e.printStackTrace();
            System.out.println("Fallo al usar o cerrar la conexión: " + e.getMessage());
        }
    }
}