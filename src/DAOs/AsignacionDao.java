package DAOs;
import Modelos.Asignacion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsignacionDao {
    // CREATE - agregar asignacion
    public void agregarAsignacion(Asignacion asignacion) {
        String sql = "INSERT INTO asignacion(id_usuario, id_tarea, fecha_asignacion, fecha_realizacion,estado ) VALUES(?,?,?,?,?)";
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, asignacion.getid_usuario());
            ps.setInt(2, asignacion.getid_tarea());
            ps.setDate(3, asignacion.getfecha_asignacion());
            ps.setDate(4, asignacion.getfecha_realizacion());
            ps.setString(5, asignacion.getestado());
            


            ps.executeUpdate();
            System.out.println("Asignacion agregada");

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // READ - lista de asignaciones
    public List<Asignacion> listaRAsignacion() {
        List<Asignacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM asignacion";

        try (Connection conn = ConectorBD.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Asignacion a = new Asignacion();
                a.setid_asignacion(rs.getInt("id_asignacion"));
                a.setid_usuario(rs.getInt("id_usuario"));
                a.setid_tarea(rs.getInt("id_tarea"));
                a.setfecha_asignacion(rs.getDate("fecha_asignacion"));
                a.setfecha_realizacion(rs.getDate("fecha_realizacion"));
                a.setestado(rs.getString("estado"));

                lista.add(a);
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

        return lista;
    }

    // UPDATE - actualizar asignacion
    public void actualizarAsignacion(Asignacion asignacion){
        String sql = "UPDATE asignacion SET id_usuario = ?, id_tarea = ?, fecha_asignacion = ?, fecha_realizacion = ?, estado = ? WHERE id_asignacion = ?";
        try(Connection conn = ConectorBD.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, asignacion.getid_usuario());
            ps.setInt(2, asignacion.getid_tarea());
            ps.setDate(3, asignacion.getfecha_asignacion());
            ps.setDate(4, asignacion.getfecha_realizacion());
            ps.setString(5, asignacion.getestado());
            ps.setInt(6, asignacion.getid_asignacion());    

            ps.executeUpdate();
            System.out.println("Asignacion actualizada");

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    // DELETE - eliminar asignacion
    } public void eliminarAsignacion(int id_asignacion) {
        String sql = "DELETE FROM asignacion WHERE id_asignacion = ?";
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id_asignacion);
            ps.executeUpdate();
            System.out.println("Asignacion eliminada");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}