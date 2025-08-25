import java.sql.Connection;

public class Main {
    public static void main(String[] args){
        Connection conn = ConectorBD.getConnection();
        if (conn != null) {
            // Realizar operaciones con la base de datos
            System.out.println("Se pudo Conectar la base de datos a java ");
        } else {
            System.out.println("Error al conectar a la base de datos");
        }
    }

}
