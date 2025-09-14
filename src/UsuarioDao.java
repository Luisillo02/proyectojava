import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    // CREATE - agregar nuevo usuario

    public void agregarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario(nombre, apellido, email) VALUES (?, ?, ?)";
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getEmail());

            ps.executeUpdate(); // ← faltaba ;
            System.out.println("Usuario agregado correctamente");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // READ - lectura de usuarios

    public List<Usuario> listaRUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario";

        try (Connection conn = ConectorBD.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setid_Usuario(rs.getInt("id_usuario"));   
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));   
                u.setEmail(rs.getString("email"));         
                lista.add(u);
            }
        } catch (SQLException e){
            System.err.println("Error: " + e.getMessage());
        }
        return lista;
    }    


    // UPDATE - actualizar usuario

    public void actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuario SET nombre = ?, apellido = ?, email = ? WHERE id_usuario = ?";
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getEmail());
            ps.setInt(4, usuario.getid_Usuario());
            ps.executeUpdate();
            System.out.println("Usuario actualizado correctamente");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // DELETE - eliminar usuario por id

    public void eliminarUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Usuario eliminado correctamente");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
