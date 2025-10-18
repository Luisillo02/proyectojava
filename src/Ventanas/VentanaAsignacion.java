package Ventanas;
import DAOs.AsignacionDao;
import DAOs.UsuarioDao;
import DAOs.TareasDao;
import Modelos.Asignacion;
import Modelos.Usuario;
import Modelos.Tareas;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class VentanaAsignacion extends JPanel {

    private AsignacionDao asignacionDao = new AsignacionDao();
    // Suponemos que tienes DAOs para Usuarios y Tareas para obtener listas
    private UsuarioDao usuarioDao = new UsuarioDao();
    private TareasDao tareasDao = new TareasDao();

    private JTextArea areaAsignaciones;
    private JComboBox<String> comboUsuarios; // Para seleccionar usuario
    private JComboBox<String> comboTareas;   // Para seleccionar tarea
    private JTextField campoFechaAsignacion; // Podría ser un JDatePicker si usas librerías externas
    private JTextField campoFechaRealizacion; // Podría ser un JDatePicker
    private JTextField campoEstado;

    public VentanaAsignacion() {
        setLayout(new BorderLayout());

        // --- Panel para agregar ---
        JPanel panelAgregar = new JPanel(new GridLayout(6, 2, 5, 5)); // Ajustado a 6 filas
        panelAgregar.setBorder(BorderFactory.createTitledBorder("Agregar/Actualizar Asignación"));
        panelAgregar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen

        comboUsuarios = new JComboBox<>();
        comboTareas = new JComboBox<>();
        campoFechaAsignacion = new JTextField("YYYY-MM-DD"); // Placeholder
        campoFechaRealizacion = new JTextField("YYYY-MM-DD (opcional)"); // Placeholder
        campoEstado = new JTextField("Pendiente"); // Valor por defecto
        JButton botonAgregar = new JButton("Agregar Asignación");
        // Nota: Para actualizar/eliminar necesitarías seleccionar una asignación existente (más complejo)

        panelAgregar.add(new JLabel("Usuario:"));
        panelAgregar.add(comboUsuarios);
        panelAgregar.add(new JLabel("Tarea:"));
        panelAgregar.add(comboTareas);
        panelAgregar.add(new JLabel("Fecha Asignación (YYYY-MM-DD):"));
        panelAgregar.add(campoFechaAsignacion);
        panelAgregar.add(new JLabel("Fecha Realización (YYYY-MM-DD):"));
        panelAgregar.add(campoFechaRealizacion);
        panelAgregar.add(new JLabel("Estado:"));
        panelAgregar.add(campoEstado);
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
        add(botonActualizar, BorderLayout.SOUTH);

        // --- Lógica de los botones ---
        botonAgregar.addActionListener(e -> {
            String usuarioSeleccionado = (String) comboUsuarios.getSelectedItem();
            String tareaSeleccionada = (String) comboTareas.getSelectedItem();
            String fechaAsignacionStr = campoFechaAsignacion.getText();
            String fechaRealizacionStr = campoFechaRealizacion.getText();
            String estado = campoEstado.getText();

            if (usuarioSeleccionado == null || tareaSeleccionada == null || fechaAsignacionStr.isEmpty() || estado.isEmpty() || usuarioSeleccionado.equals("Seleccionar...") || tareaSeleccionada.equals("Seleccionar...")) {
                JOptionPane.showMessageDialog(this, "Usuario, Tarea, Fecha Asignación y Estado son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int idUsuario = Integer.parseInt(usuarioSeleccionado.split(" - ")[0]); // Extraer ID
                int idTarea = Integer.parseInt(tareaSeleccionada.split(" - ")[0]);     // Extraer ID

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false); // No permitir fechas inválidas

                Date fechaAsignacionSQL = new Date(sdf.parse(fechaAsignacionStr).getTime());
                Date fechaRealizacionSQL = null;
                if (fechaRealizacionStr != null && !fechaRealizacionStr.isEmpty() && !fechaRealizacionStr.contains("opcional")) {
                    fechaRealizacionSQL = new Date(sdf.parse(fechaRealizacionStr).getTime());
                }

                Asignacion nuevaAsignacion = new Asignacion(0, idUsuario, idTarea, fechaAsignacionSQL, fechaRealizacionSQL, estado);
                asignacionDao.agregarAsignacion(nuevaAsignacion); // Cuidado: El SQL en AsignacionDao tiene un WHERE incorrecto

                JOptionPane.showMessageDialog(this, "Asignación agregada correctamente.");
                // Limpiar campos (opcionalmente resetear combos a "Seleccionar...")
                campoFechaAsignacion.setText("YYYY-MM-DD");
                campoFechaRealizacion.setText("YYYY-MM-DD (opcional)");
                campoEstado.setText("Pendiente");
                actualizarListaAsignaciones();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error al obtener ID de usuario o tarea.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Usa YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) { // Captura genérica por si algo más falla
                 JOptionPane.showMessageDialog(this, "Error al agregar la asignación: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 ex.printStackTrace(); // Imprime detalles del error en consola
            }
        });

        botonActualizar.addActionListener(e -> {
             cargarCombos(); // Recargar por si hay nuevos usuarios/tareas
             actualizarListaAsignaciones();
        });

        actualizarListaAsignaciones(); // Carga inicial
    }

     private void cargarCombos() {
        comboUsuarios.removeAllItems(); // Limpiar antes de llenar
        comboTareas.removeAllItems();

        comboUsuarios.addItem("Seleccionar...");
        List<Usuario> usuarios = usuarioDao.listaRUsuarios();
        for (Usuario u : usuarios) {
            comboUsuarios.addItem(u.getid_Usuario() + " - " + u.getNombre() + " " + u.getApellido());
        }

        comboTareas.addItem("Seleccionar...");
        List<Tareas> tareas = tareasDao.listaRTarea();
        for (Tareas t : tareas) {
             if (t.isestado()) { // Opcional: Solo mostrar tareas activas
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