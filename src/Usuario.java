import java.sql.Date;

public class Usuario {
    private int id_Usuario;
    private String nombre;
    private String apellido;
    private String email;
    private Date fecha_alta;
    private boolean  activo;

// ahora un constructor vacio
public Usuario() {}


// Constructor con sus parametros

public Usuario(int id_Usuario, String nombre, String apellido, String email, Date fecha_alta, boolean  activo) {
    this.id_Usuario = id_Usuario;
    this.nombre = nombre;
    this.apellido = apellido;
    this.email = email;
    this.fecha_alta = fecha_alta;
    this.activo = activo;
}
// Funciones de acceso (getters y setters)

public int getid_Usuario(){return id_Usuario;}
public void setid_Usuario(int id_Usuario){this.id_Usuario = id_Usuario;}

public String getNombre(){return nombre;}
public void setNombre(String nombre){this.nombre = nombre;}

public String getApellido(){return apellido;}
public void setApellido(String apellido){this.apellido = apellido;}

public String getEmail(){return email;}
public void setEmail(String email){this.email = email;}

public Date getfecha_alta(){return fecha_alta;}
public void setfecha_alta(Date fecha_alta){this.fecha_alta = fecha_alta;}

public boolean isActivo(){return activo;}
public void setActivo(boolean  activo){this.activo = activo;}




}
