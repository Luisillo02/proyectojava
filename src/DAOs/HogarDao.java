package DAOs;
 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Modelos.Hogar;



public class HogarDao {
    // funcion añadir hogar
    public void agregarHogar(Hogar hogar){
        String sql = "INSERT INTO hogar(nombre, direccion) VALUES (?, ?)";
        try( Connection conn = ConectorBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, hogar.getNombre());
            ps.setString(2, hogar.getDireccion());

            ps.executeUpdate();
            System.out.println("Hogar agragado");
            } catch (SQLException e){
                System.err.println("Error: " + e.getMessage());
            }

        }
    // Read - lectura de Hogar
    public List<Hogar> listaRHogar(){
        List<Hogar> lista = new ArrayList<>();
        String sql = "SELECT * FROM hogar";

        try(Connection conn = ConectorBD.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                Hogar h = new Hogar();
                h.setIdHogar(rs.getInt("id_hogar"));
                h.setNombre(rs.getString("nombre"));
                h.setDireccion(rs.getString("direccion"));
                lista.add(h);
            }
        } catch (SQLException e){
            System.err.println("Error: " + e.getMessage());

        }
        return lista;
    }


// UPDATE - actualizar Hogar
    public void actualizarHogar(Hogar hogar){
        String sql = "UPDATE hogar SET nombre=?, direccion=?  WHERE id_hogar= ?";
        try(Connection conn = ConectorBD.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1,hogar.getNombre());
            ps.setString(2,hogar.getDireccion());
            ps.setInt(3,hogar.getIdHogar());
            ps.executeUpdate();
            System.out.println("Hogar a sido actualizado")
            ;
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

// opcion DELETE- eliminar Hogar por id 

public void eliminarHogar(int id){
    String sql = "DELETE FROM hogar WHERE id_hogar = ?";
    try (Connection conn = ConectorBD.getConnection();
    PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("Hogar eliminado");

    } catch (SQLException e) {
        System.err.println("Error: " + e.getMessage());
    }
    
}

    }

