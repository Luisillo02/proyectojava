package Ventanas;
import DAOs.AsignacionDao;
import DAOs.HogarDao;
import DAOs.TareasDao;
import DAOs.UsuarioDao;
import Modelos.Asignacion;
import Modelos.Hogar;
import Modelos.Tareas;
import Modelos.Usuario;
import java.awt.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;

public class VentanaAsignacion extends JPanel {

    private AsignacionDao asignacionDao = new AsignacionDao();
    private UsuarioDao usuarioDao = new UsuarioDao();
    private TareasDao tareasDao = new TareasDao();
    private HogarDao hogarDao = new HogarDao(); // DAO de Hogar
    private JTextArea areaAsignaciones;
    private JComboBox<String> comboUsuarios; 
    private JComboBox<String> comboTareas;   
    private JComboBox<String> comboHogar;    // Combo de Hogar
    private JTextField campoFechaAsignacion; 
    private JTextField campoFechaRealizacion; 
    private JComboBox<String> comboEstado; // <-- CAMBIO: Re-habilitado (es necesario)
    private JTextField campoIdEliminar;
    
    public VentanaAsignacion() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Panel para agregar ---
        JPanel panelAgregar = new JPanel(new GridLayout(7, 2, 5, 5)); 
        panelAgregar.setBorder(BorderFactory.createTitledBorder("Agregar Asignación"));
        
        comboUsuarios = new JComboBox<>();
        comboTareas = new JComboBox<>();
        comboHogar = new JComboBox<>();
        campoFechaAsignacion = new JTextField("YYYY-MM-DD"); 
        campoFechaRealizacion = new JTextField("YYYY-MM-DD (opcional)"); 

        // --- CAMBIO: Lógica de Estado (re-habilitada) ---
        // El estado de una ASIGNACIÓN es "Pendiente" o "Completada"
        String [] opcionesEstado = {"Pendiente","Completada"};
        comboEstado = new JComboBox<>(opcionesEstado);

        JButton botonAgregar = new JButton("Agregar Asignación");

        panelAgregar.add(new JLabel("Usuario:"));
        panelAgregar.add(comboUsuarios);
        panelAgregar.add(new JLabel("Tarea (Solo Inactivas):"));
        panelAgregar.add(comboTareas);
        panelAgregar.add(new JLabel("Hogar:"));
        panelAgregar.add(comboHogar);
        panelAgregar.add(new JLabel("Fecha Asignación (YYYY-MM-DD):"));
        panelAgregar.add(campoFechaAsignacion);
        panelAgregar.add(new JLabel("Fecha Realización (YYYY-MM-DD):"));
        panelAgregar.add(campoFechaRealizacion);
        panelAgregar.add(new JLabel("Estado Asignación:")); // <-- Re-habilitado
        panelAgregar.add(comboEstado); // <-- Re-habilitado
        panelAgregar.add(new JLabel("")); 
        panelAgregar.add(botonAgregar);

        cargarCombos(); // Carga Usuarios, Tareas Y HOGARES

        // --- Área para mostrar la lista ---
        areaAsignaciones = new JTextArea();
        areaAsignaciones.setEditable(false);
        areaAsignaciones.setMargin(new Insets(10, 10, 10, 10)); 
        JScrollPane scrollLista = new JScrollPane(areaAsignaciones);

        JButton botonActualizar = new JButton("Actualizar Lista");

        JPanel panelAcciones = new JPanel (new BorderLayout(10,10));
        JPanel panelEliminar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEliminar.setBorder(BorderFactory.createTitledBorder("Eliminar Asignacion por ID:"));
        campoIdEliminar = new JTextField(5);
        JButton botonEliminar = new JButton("Eliminar");
        panelEliminar.add(new JLabel("ID Asignacion"));
        panelEliminar.add(campoIdEliminar);
        panelEliminar.add(botonEliminar);
        panelAcciones.add(panelEliminar,BorderLayout.CENTER);
        panelAcciones.add(botonActualizar,BorderLayout.EAST);

        add(panelAgregar, BorderLayout.NORTH);
        add(scrollLista, BorderLayout.CENTER);
        add(panelAcciones, BorderLayout.SOUTH);

        // --- Lógica de los botones ---
        botonAgregar.addActionListener(e -> {
            String usuarioSeleccionado = (String) comboUsuarios.getSelectedItem();
            String tareaSeleccionada = (String) comboTareas.getSelectedItem();
            String hogarSeleccionado = (String) comboHogar.getSelectedItem();
            String fechaAsignacionStr = campoFechaAsignacion.getText();
            String fechaRealizacionStr = campoFechaRealizacion.getText();
            String estado = (String) comboEstado.getSelectedItem(); // <-- Re-habilitado

            // Validación corregida
            if (usuarioSeleccionado == null || tareaSeleccionada == null || hogarSeleccionado == null || fechaAsignacionStr.isEmpty()  || usuarioSeleccionado.equals("Seleccionar...") || tareaSeleccionada.equals("Seleccionar...") || hogarSeleccionado.equals("Seleccionar...")) {
                JOptionPane.showMessageDialog(this, "Usuario, Tarea, Hogar y Fecha Asignación son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int idUsuario = Integer.parseInt(usuarioSeleccionado.split(" - ")[0]);
                int idTarea = Integer.parseInt(tareaSeleccionada.split(" - ")[0]);    
                int idHogar = Integer.parseInt(hogarSeleccionado.split(" - ")[0]); // <-- CAMBIO: Corregido el split
                
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false); 
                Date fechaAsignacionSQL = new Date(sdf.parse(fechaAsignacionStr).getTime());
                Date fechaRealizacionSQL = null;
                if (fechaRealizacionStr != null && !fechaRealizacionStr.isEmpty() && !fechaRealizacionStr.contains("opcional")) {
                    fechaRealizacionSQL = new Date(sdf.parse(fechaRealizacionStr).getTime());
                }
                
                // Asumo que tu Asignacion.java y AsignacionDao.java ya están correctos
                Asignacion nuevaAsignacion = new Asignacion(0, idUsuario, idTarea, idHogar, fechaAsignacionSQL, fechaRealizacionSQL, estado);
                asignacionDao.agregarAsignacion(nuevaAsignacion); 

                // (Mantengo el nombre de tu método)
                tareasDao.actualizarEstadoTarea(idTarea, true); // Pone la TAREA como "Activa"

                JOptionPane.showMessageDialog(this, "Asignación agregada y Tarea marcada como 'Activa'.");
                
                campoFechaAsignacion.setText("YYYY-MM-DD");
                campoFechaRealizacion.setText("YYYY-MM-DD (opcional)");
                comboEstado.setSelectedItem("Pendiente"); // <-- Re-habilitado
                
                cargarCombos(); 
                actualizarListaAsignaciones();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error al obtener ID de usuario, tarea u hogar.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Usa YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) { 
                 JOptionPane.showMessageDialog(this, "Error al agregar la asignación: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 ex.printStackTrace(); 
            }
        });

        // --- Lógica del botón Eliminar (con reactivación de tarea) ---
        botonEliminar.addActionListener(e -> {
            String idStr = campoIdEliminar.getText();
            if (idStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Introduce el ID de la asignación a eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                int idAsignacion = Integer.parseInt(idStr);
                Asignacion asignacion = asignacionDao.buscarAsignacionPorId(idAsignacion);

                if (asignacion == null) {
                    JOptionPane.showMessageDialog(this, "No se encontró ninguna asignación con el ID " + idAsignacion, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                int idTareaAReactivar = asignacion.getid_tarea();
                int confirmacion = JOptionPane.showConfirmDialog(this,
                        "¿Estás seguro de que deseas eliminar la asignación con ID " + idAsignacion + "?\n" +
                        "La Tarea asociada (ID: " + idTareaAReactivar + ") volverá a estar 'Inactiva'.",
                        "Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    asignacionDao.eliminarAsignacion(idAsignacion);
                    
                    // (Mantengo el nombre de tu método)
                    tareasDao.actualizarEstadoTarea(idTareaAReactivar, false); 

                    JOptionPane.showMessageDialog(this, "Asignación eliminada. Tarea marcada como 'Inactiva'.");
                    campoIdEliminar.setText("");
                    
                    actualizarListaAsignaciones();
                    cargarCombos(); 
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número entero.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });

        botonActualizar.addActionListener(e -> {
             cargarCombos(); 
             actualizarListaAsignaciones();
        });

        actualizarListaAsignaciones(); // Carga inicial
     }

     private void cargarCombos() {
        comboUsuarios.removeAllItems(); 
        comboTareas.removeAllItems();
        comboHogar.removeAllItems(); // <-- CAMBIO: Limpiar comboHogar

        comboUsuarios.addItem("Seleccionar...");
        List<Usuario> usuarios = usuarioDao.listaRUsuarios();
        for (Usuario u : usuarios) {
            comboUsuarios.addItem(u.getid_Usuario() + " - " + u.getNombre() + " " + u.getApellido());
        }

        comboTareas.addItem("Seleccionar...");
        List<Tareas> tareas = tareasDao.listaRTarea();
        for (Tareas t : tareas) {
             if (!t.isestado()) { // Solo mostrar tareas INACTIVAS (estado == false)
                comboTareas.addItem(t.getid_tarea() + " - " + t.getnombre());
             }
        }
        
        // --- CAMBIO: Lógica para cargar hogares (AÑADIDA) ---
        comboHogar.addItem("Seleccionar...");
        List<Hogar> hogares = hogarDao.listaRHogar();
        for (Hogar h : hogares) {
            comboHogar.addItem(h.getIdHogar() + " - " + h.getNombre());
        }
     }


    private void actualizarListaAsignaciones() {
        areaAsignaciones.setText("");
        List<Asignacion> lista = asignacionDao.listaRAsignacion();
        if (lista.isEmpty()) {
             areaAsignaciones.setText("No hay asignaciones registradas.");
        } else {
             SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
             for (Asignacion a : lista) {
                 String fechaAsigStr = (a.getfecha_asignacion() != null) ? sdf.format(a.getfecha_asignacion()) : "N/A";
                 String fechaRealStr = (a.getfecha_realizacion() != null) ? sdf.format(a.getfecha_realizacion()) : "Pendiente";

                 // --- CAMBIO: Añadido 'getid_hogar()' ---
                 areaAsignaciones.append("ID: " + a.getid_asignacion() +
                                       " | Usuario ID: " + a.getid_usuario() +
                                       " | Tarea ID: " + a.getid_tarea() +
                                       " | Hogar ID: " + a.getid_hogar() + "\n");
                 areaAsignaciones.append("  Asignada: " + fechaAsigStr +
                                       " | Realización: " + fechaRealStr +
                                       " | Estado: " + a.getestado() + "\n\n");
             }
        }
    }
}
