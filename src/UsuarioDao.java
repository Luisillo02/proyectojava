import Conexio.ConectorBD;
import java.sql.*;
import java.util.list;

public class UsuarioDao {
    //CREATE  metodo de creacion de usuario
    public void agregarUsuario(Usuario usuario){
        String sql ="INSERT INTO usuario(nombre,apellido,email) VALUES (?,?,?)";
        try (Connection conn = ConectorBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setString(1, usuario.getNombre());
                ps.setString(2, usuario.getApellido());
                ps.setString(3, usuario.getEmail());
                ps.executeUpdate();

                // Ejecuta la incercion a la base de datos
                ps.executeUpdate();
                System.out.println("Usuario agregado exitosamente");
            } catch (SQLException e) {
                System.out.println("Error al agregar usuario: " + e.getMessage());
            }
    }
    //Metodo READ en java 

    public List<Usuario> listaUsuarios(){
        
    }

}

