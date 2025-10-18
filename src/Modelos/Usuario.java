package Modelos;
public class Usuario {
    private int id_Usuario;
    private String nombre;
    private String apellido;
    private String email;
// ahora un constructor vacio
public Usuario() {}


// Constructor con sus parametros

public Usuario(int id_Usuario, String nombre, String apellido, String email ) {
    this.id_Usuario = id_Usuario;
    this.nombre = nombre;
    this.apellido = apellido;
    this.email = email;
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
}
