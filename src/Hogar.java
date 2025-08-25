public class Hogar {
    private int id_hogar;
    private String nombre;
    private String direccion;

// Constructor de la clase hogar
public Hogar() {}


public Hogar(int id_hogar, String nombre, String direccion) {
    this.id_hogar = id_hogar;
    this.nombre = nombre;
    this.direccion = direccion;
}

//Funcion get y set 

public String getNombre() {return nombre;}
public void setNombre(String nombre) {this.nombre = nombre;}

public String getDireccion() {return direccion;}
public void setDireccion(String direccion) {this.direccion = direccion;}

public int getIdHogar() {return id_hogar;}
public void setIdHogar(int id_hogar) {this.id_hogar = id_hogar;}





}
