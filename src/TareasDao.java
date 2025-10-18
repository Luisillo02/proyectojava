import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TareasDao{

    // CREATE - agregar tarea
    public void agregarTarea(Tareas tarea) {
        String sql = "INSERT INTO tarea(nombre, descripcion, frecuencia, ultima_fecha_realizada, estado) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tarea.getnombre());
            ps.setString(2, tarea.getdescripcion());
            ps.setString(3, tarea.getfrecuencia());
            ps.setDate(4, tarea.getUltima_fecha_realizada());
            ps.setBoolean(5, tarea.isestado());

            ps.executeUpdate();
            System.out.println("Tarea agregada");

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // READ - lista de tareas
    public List<Tareas> listaRTarea() {
        List<Tareas> lista = new ArrayList<>();
        String sql = "SELECT * FROM tarea";

        try (Connection conn = ConectorBD.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Tareas t = new Tareas();
                t.setid_tarea(rs.getInt("id_tarea"));
                t.setnombre(rs.getString("nombre"));
                t.setdescripcion(rs.getString("descripcion"));
                t.setfrecuencia(rs.getString("frecuencia"));
                t.setUltima_fecha_realizada(rs.getDate("Ultima_fecha_realizada"));
                t.setestado(rs.getBoolean("estado"));

                lista.add(t);
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

        return lista;
    }

    // UPDATE - actualizar tarea
    public void actualizarTareas(Tareas tareas){
        String sql = "UPDATE tarea SET nombre = ?, descripcion = ?, frecuencia = ?, Ultima_fecha_realizada = ?, estado = ? WHERE id_tarea = ?";
        try(Connection conn = ConectorBD.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tareas.getnombre());
            ps.setString(2, tareas.getdescripcion());
            ps.setString(3, tareas.getfrecuencia());
            ps.setDate(4, tareas.getUltima_fecha_realizada());
            ps.setBoolean(5, tareas.isestado());
            ps.setInt(6, tareas.getid_tarea());

            ps.executeUpdate();
            System.out.println("Tarea actualizada correctamente");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
         }
    }
    // DELETE - eliminar tarea por id
    public void eliminarTarea(int id){
        String sql = "DELETE FROM tarea WHERE id_tarea = ?";
        try(Connection conn = ConectorBD.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Tarea eliminada correctamente");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
         }
    }
}
