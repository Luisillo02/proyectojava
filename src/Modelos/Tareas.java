package Modelos;
import java.sql.Date;

public class Tareas {
    private int id_tarea;
    private String nombre;
    private String descripcion;
    private String frecuencia;
    private Date Ultima_fecha_realizada;
    private boolean estado;

// Constructor de la clase tareas
public Tareas() {
    this.estado = false;
}


// Constructor con Parametros

public Tareas(int id_tarea,String nombre,String descripcion,String frecuencia,Date Ultima_fecha_realizada,boolean estado) {
    this.id_tarea = id_tarea;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.frecuencia = frecuencia;
    this.Ultima_fecha_realizada = Ultima_fecha_realizada;
    this.estado = estado;
}

public int getid_tarea() {return id_tarea;}
public void setid_tarea(int id_tarea) {this.id_tarea = id_tarea;}

public String getnombre() {return nombre;}
public void setnombre(String nombre) {this.nombre = nombre;}

public String getdescripcion() {return descripcion;}
public void setdescripcion(String descripcion) {this.descripcion = descripcion;}

public String getfrecuencia() {return frecuencia;}
public void setfrecuencia(String frecuencia) {this.frecuencia = frecuencia;}

public Date getUltima_fecha_realizada() {return Ultima_fecha_realizada;}
public void setUltima_fecha_realizada(Date Ultima_fecha_realizada) {this.Ultima_fecha_realizada = Ultima_fecha_realizada;}

public boolean isestado() {return estado; }
public void setestado(boolean estado) { this.estado = estado; }
}
