package Modelos;

import java.sql.Date;
public class Asignacion {
    private  int id_asignacion;
    private int id_usuario;
    private int id_tarea;
    private int id_hogar;
    private Date fecha_asignacion;
    private Date fecha_realizacion;
    private String estado;

// crea la clase vacia 

public Asignacion() {}

// ahora con los parametros

public Asignacion(int id_asignacion, int id_usuario, int id_tarea,int id_hogar, Date fecha_asignacion, Date fecha_realizacion, String estado) {
    this.id_asignacion = id_asignacion;
    this.id_usuario = id_usuario;
    this.id_tarea = id_tarea;
    this.id_hogar = id_hogar;
    this.fecha_asignacion = fecha_asignacion;
    this.fecha_realizacion = fecha_realizacion;
    this.estado = estado;
}

//Ahora los getter y setters

public int getid_asignacion(){return id_asignacion;}
public void setid_asignacion(int id_asignacion){this.id_asignacion = id_asignacion;}

public int getid_usuario(){return id_usuario;}
public void setid_usuario(int id_usuario){this.id_usuario = id_usuario;}
public int getid_tarea(){return id_tarea;}
public void setid_tarea(int id_tarea){this.id_tarea = id_tarea;}

public int getid_hogar(){return id_hogar;}
public void setid_hogar(int id_hogar){this.id_hogar = id_hogar;}

public Date getfecha_asignacion(){return fecha_asignacion;}

public void setfecha_asignacion(Date fecha_asignacion){this.fecha_asignacion = fecha_asignacion;}

public Date getfecha_realizacion(){return fecha_realizacion;}
public void setfecha_realizacion(Date fecha_realizacion){this.fecha_realizacion = fecha_realizacion;}

public String getestado(){return estado;}
public void setestado(String estado){this.estado = estado;}





}
