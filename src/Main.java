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
        UsuarioDao UsuarioDao = new  UsuarioDao();
        // Insertar usurio
        Usuario nuevo = new Usuario(0,"Pedro","Sanchez","Pedro@gmail.com");
        UsuarioDao.agregarUsuario(nuevo);

        // Lista de usuario 
        for (Usuario u : UsuarioDao.listaRUsuarios()){
            System.out.println(u.getid_Usuario()+" - " + u.getNombre()+ " " + u.getApellido());
        }
    }

}
