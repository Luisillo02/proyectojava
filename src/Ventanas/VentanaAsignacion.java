package Ventanas;
import DAOs.AsignacionDao;
import DAOs.TareasDao;
import DAOs.UsuarioDao;
import Modelos.Asignacion;
import Modelos.Tareas;
import DAOs.HogarDao;
import Modelos.Hogar;
import Modelos.Usuario;
import java.awt.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;

public class VentanaAsignacion extends JPanel {

    private AsignacionDao asignacionDao = new AsignacionDao();
    // Suponemos que tienes DAOs para Usuarios y Tareas para obtener listas
    private UsuarioDao usuarioDao = new UsuarioDao();
    private TareasDao tareasDao = new TareasDao();
    private HogarDao hogarDao = new HogarDao();
    private JTextArea areaAsignaciones;
    private JComboBox<String> comboUsuarios; // Para seleccionar usuario
    private JComboBox<String> comboTareas;   // Para seleccionar tarea
    private JComboBox<String> comboHogar;   // Para seleccionar Hogar
    private JTextField campoFechaAsignacion; // Podría ser un JDatePicker si usas librerías externas
    private JTextField campoFechaRealizacion; // Podría ser un JDatePicker
   // private JComboBox<String> comboEstado; // Para seleccionar estado
    private JTextField campoIdEliminar;
    public VentanaAsignacion() {
        setLayout(new BorderLayout());

        // --- Panel para agregar ---
        JPanel panelAgregar = new JPanel(new GridLayout(7, 2, 5, 5)); // Ajustado a 6 filas
        panelAgregar.setBorder(BorderFactory.createTitledBorder("Agregar/Actualizar Asignación"));
        panelAgregar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen

        comboUsuarios = new JComboBox<>();
        comboTareas = new JComboBox<>();
        comboHogar = new JComboBox<>();
        
        campoFechaAsignacion = new JTextField("YYYY-MM-DD"); // Placeholder
        campoFechaRealizacion = new JTextField("YYYY-MM-DD (opcional)"); // Placeholder
         // Agrego el Seleccionador de opciones 
        //String [] opcionesEstado = {"Inactiva","Activa"};
        //comboEstado = new JComboBox<>(opcionesEstado);

        
        
        JButton botonAgregar = new JButton("Agregar Asignación");
        // Nota: Para actualizar/eliminar necesitarías seleccionar una asignación existente (más complejo)

        panelAgregar.add(new JLabel("Usuario:"));
        panelAgregar.add(comboUsuarios);
        panelAgregar.add(new JLabel("Tarea:"));
        panelAgregar.add(comboTareas);
        panelAgregar.add(new JLabel("Hogar"));
        panelAgregar.add(comboHogar);
        panelAgregar.add(new JLabel("Fecha Asignación (YYYY-MM-DD):"));
        panelAgregar.add(campoFechaAsignacion);
        panelAgregar.add(new JLabel("Fecha Realización (YYYY-MM-DD):"));
        panelAgregar.add(campoFechaRealizacion);
        //panelAgregar.add(new JLabel("Estado:"));
        //panelAgregar.add(comboEstado);
        panelAgregar.add(new JLabel("")); // Espacio
        panelAgregar.add(botonAgregar);

        cargarCombos(); // Cargar usuarios y tareas en los ComboBox

        // --- Área para mostrar la lista ---
        areaAsignaciones = new JTextArea();
        areaAsignaciones.setEditable(false);
        areaAsignaciones.setMargin(new Insets(10, 10, 10, 10)); // Margen
        JScrollPane scrollLista = new JScrollPane(areaAsignaciones);

        // --- Botón de actualización ---
        JButton botonActualizar = new JButton("Actualizar Lista");

        add(panelAgregar, BorderLayout.NORTH);
        add(scrollLista, BorderLayout.CENTER);
        JPanel panelAcciones = new JPanel (new BorderLayout(10,10));
        
        //Panel para eliminar 

        JPanel panelEliminar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEliminar.setBorder(BorderFactory.createTitledBorder("Eliminar Asignacion por ID:"));
        campoIdEliminar = new JTextField(5);
        JButton botonEliminar = new JButton("Eliminar");
        panelEliminar.add(new JLabel("ID Asignacion"));
        panelEliminar.add(campoIdEliminar);
        panelEliminar.add(botonEliminar);

        // se añade el panel eliminar y de actualizar al de acciones
        panelAcciones.add(panelEliminar,BorderLayout.CENTER);
        panelAcciones.add(botonActualizar,BorderLayout.EAST);

        // Añadir el panel al sur
        add(panelAcciones, BorderLayout.SOUTH);
// --- Lógica de los botones ---
        botonAgregar.addActionListener(e -> {
            String usuarioSeleccionado = (String) comboUsuarios.getSelectedItem();
            String tareaSeleccionada = (String) comboTareas.getSelectedItem();
            String hogarSeleccionado = (String) comboHogar.getSelectedItem();
            String fechaAsignacionStr = campoFechaAsignacion.getText();
            String fechaRealizacionStr = campoFechaRealizacion.getText();
            
            // --- CAMBIO 1: La validación estaba incompleta ---
            //String estado = (String) comboEstado.getSelectedItem(); // Obtener el estado
            if (usuarioSeleccionado == null || tareaSeleccionada == null || hogarSeleccionado == null ||fechaAsignacionStr.isEmpty()  || usuarioSeleccionado.equals("Seleccionar...") || tareaSeleccionada.equals("Seleccionar...")) {
                JOptionPane.showMessageDialog(this, "Usuario, Tarea,hogar; Fecha Asignación y Estado son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int idUsuario = Integer.parseInt(usuarioSeleccionado.split(" - ")[0]); // Extraer ID
                int idTarea = Integer.parseInt(tareaSeleccionada.split(" - ")[0]);     // Extraer ID
                int idHogar = Integer.parseInt(hogarSeleccionado.split("-")[0]);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false); // No permitir fechas inválidas
                // String estado = (String) comboEstado.getSelectedItem(); // (Movido arriba para validación)
                Date fechaAsignacionSQL = new Date(sdf.parse(fechaAsignacionStr).getTime());
                Date fechaRealizacionSQL = null;
                if (fechaRealizacionStr != null && !fechaRealizacionStr.isEmpty() && !fechaRealizacionStr.contains("opcional")) {
                    fechaRealizacionSQL = new Date(sdf.parse(fechaRealizacionStr).getTime());
                }
                
                Asignacion nuevaAsignacion = new Asignacion(0, idUsuario, idTarea,idHogar,fechaAsignacionSQL, fechaRealizacionSQL, "Activa");
                asignacionDao.agregarAsignacion(nuevaAsignacion); // Asumo que el DAO ya está corregido

                // --- CAMBIO 2: Corregido el nombre del método que crasheaba ---
                // (De 'actualizarEstadoTarea' a 'actualizarEstado')
                tareasDao.actualizarEstadoTarea(idTarea, true); // Pone la TAREA como "Activa"

                JOptionPane.showMessageDialog(this, "Asignación agregada y Tarea marcada como 'Activa'.");
                
                campoFechaAsignacion.setText("YYYY-MM-DD");
                campoFechaRealizacion.setText("YYYY-MM-DD (opcional)");
                
                // --- CAMBIO 3: Lógica de reseteo de comboEstado ---
                // (Asumiendo que las opciones son "Pendiente"/"Completada", no "Activa")
                //comboEstado.setSelectedItem("Pendiente"); 
                
                cargarCombos(); 
                actualizarListaAsignaciones();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error al obtener ID de usuario o tarea.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Usa YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) { 
                 JOptionPane.showMessageDialog(this, "Error al agregar la asignación: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 ex.printStackTrace(); 
            }
        });

        // --- CAMBIO 4: LÓGICA PARA EL BOTÓN ELIMINAR (AÑADIDA) ---
        // (Esto asume que declaraste 'botonEliminar' y 'campoIdEliminar' en el constructor)
        // --- LÓGICA DEL BOTÓN ELIMINAR (Actualizada) ---
        botonEliminar.addActionListener(e -> {
            String idStr = campoIdEliminar.getText();
            if (idStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Introduce el ID de la asignación a eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                int idAsignacion = Integer.parseInt(idStr);

                // --- LÓGICA AÑADIDA ---
                // 1. Buscar la asignación ANTES de borrarla (usando el método del Paso 1)
                Asignacion asignacion = asignacionDao.buscarAsignacionPorId(idAsignacion);

                // Verificar si se encontró la asignación
                if (asignacion == null) {
                    JOptionPane.showMessageDialog(this, "No se encontró ninguna asignación con el ID " + idAsignacion, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // 2. Guardamos el ID de la tarea que vamos a reactivar
                int idTareaAReactivar = asignacion.getid_tarea();
                // --- FIN LÓGICA AÑADIDA ---

                // 3. Pedir confirmación (ahora mostrando qué tarea se reactivará)
                int confirmacion = JOptionPane.showConfirmDialog(this,
                        "¿Estás seguro de que deseas eliminar la asignación con ID " + idAsignacion + "?\n" +
                        "La Tarea asociada (ID: " + idTareaAReactivar + ") volverá a estar 'Inactiva'.",
                        "Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    
                    // 4. Eliminar la asignación
                    asignacionDao.eliminarAsignacion(idAsignacion);
                    
                    tareasDao.actualizarEstadoTarea(idTareaAReactivar, false);

                    JOptionPane.showMessageDialog(this, "Asignación eliminada. Tarea marcada como 'Inactiva'.");
                    campoIdEliminar.setText("");
                    
                    // 6. Recargamos ambas listas
                    actualizarListaAsignaciones();
                    cargarCombos(); // Para que la tarea aparezca de nuevo en la lista
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número entero.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });
        // --- FIN DEL CAMBIO 4 ---

        botonActualizar.addActionListener(e -> {
             cargarCombos(); 
             actualizarListaAsignaciones();
        });

        actualizarListaAsignaciones(); // Carga inicial
     }

     private void cargarCombos() {
        comboUsuarios.removeAllItems(); 
        comboTareas.removeAllItems();

        comboUsuarios.addItem("Seleccionar...");
        List<Usuario> usuarios = usuarioDao.listaRUsuarios();
        for (Usuario u : usuarios) {
            comboUsuarios.addItem(u.getid_Usuario() + " - " + u.getNombre() + " " + u.getApellido());
        }

        comboTareas.addItem("Seleccionar...");
        List<Tareas> tareas = tareasDao.listaRTarea();
        for (Tareas t : tareas) {
             // --- CAMBIO 5: Lógica corregida para evitar duplicados ---
             // (De 't.isestado()' a '!t.isestado()')
             if (!t.isestado()) { // Solo mostrar tareas INACTIVAS (estado == false)
                comboTareas.addItem(t.getid_tarea() + " - " + t.getnombre());
             }
        }
     }


    private void actualizarListaAsignaciones() {
        areaAsignaciones.setText("");
        List<Asignacion> lista = asignacionDao.listaRAsignacion();
        if (lista.isEmpty()) {
             areaAsignaciones.setText("No hay asignaciones registradas.");
        } else {
             SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Formato más legible
             for (Asignacion a : lista) {
                 String fechaAsigStr = (a.getfecha_asignacion() != null) ? sdf.format(a.getfecha_asignacion()) : "N/A";
                 String fechaRealStr = (a.getfecha_realizacion() != null) ? sdf.format(a.getfecha_realizacion()) : "Pendiente";

                 // Sería ideal obtener nombres de usuario/tarea en lugar de IDs (requiere JOINS en el DAO o búsquedas adicionales)
                 areaAsignaciones.append("ID: " + a.getid_asignacion() +
                                       " | Usuario ID: " + a.getid_usuario() +
                                       " | Tarea ID: " + a.getid_tarea() + "\n");
                 areaAsignaciones.append("  Asignada: " + fechaAsigStr +
                                       " | Realizada: " + fechaRealStr +
                                       " | Estado: " + a.getestado() + "\n\n");
             }
        }
    }
}