import java.sql.Connection;
import java.sql.Date;
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
        Usuario nuevo = new Usuario(0,"Javier","Milei","Elleon@gmail.com");
        UsuarioDao.agregarUsuario(nuevo);

        // Lista de usuario 
        for (Usuario u : UsuarioDao.listaRUsuarios()){
            System.out.println(u.getid_Usuario()+" - " + u.getNombre()+ " - " + u.getApellido());
        }
        // Insertar Tarea
        TareasDao TareasDao = new TareasDao();
        Tareas nuevaTareas = new Tareas(1, "Tarea 01", "Sacar la basura", "Semanal  ", new Date ( System.currentTimeMillis()) , true);
        TareasDao.agregarTarea(nuevaTareas);

        for (Tareas t : TareasDao.listaRTarea()){
            System.out.println(t.getid_tarea()+ "-" + t.getnombre()+ "-" +t.getdescripcion()+ "-"+t.getfrecuencia()+ "-" + t.getUltima_fecha_realizada()+ "-" + t.isestado());
        }

        HogarDao HogarDao = new HogarDao();
        // insertamos hogar
        Hogar nuevoHogar = new Hogar(0, "La casona", "Av El libertador 234");
        HogarDao.agregarHogar(nuevoHogar);
        for ( Hogar h : HogarDao.listaRHogar()){
            System.out.println(h.getIdHogar()+ " - " + h.getNombre()+ "-" + h.getDireccion());
        }
        // Insertar Asignacion
        AsignacionDao AsignacionDao = new AsignacionDao();
        Asignacion nuevagAsignacion = new Asignacion(0, 0, 0, new Date( System.currentTimeMillis()), new Date (2025,12,12),"Activo");
        AsignacionDao.agregarAsignacion(nuevagAsignacion);
        for (Asignacion a : AsignacionDao.listaRAsignacion()){ 
            System.out.println(a.getid_asignacion()+ " - " + a.getid_usuario()+ "-" + a.getid_tarea()+ "-" + a.getfecha_asignacion()+ "-" + a.getfecha_realizacion());
        }

    }
}

