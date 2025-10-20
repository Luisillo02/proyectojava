package Ventanas;
import DAOs.TareasDao;
import Modelos.Tareas;
import java.awt.*;
import java.sql.Date;
import java.util.List;
import javax.swing.*;

public class VentanaTareas extends JPanel {

    private TareasDao tareasDao = new TareasDao();
    private JTextArea areaTareas;
    private JTextField campoIdEliminar; // Campo para ID a eliminar

    public VentanaTareas() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Panel Superior (Agregar y Eliminar) ---
        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));

        // --- Panel para agregar nuevas tareas ---
        JPanel panelAgregar = new JPanel(new GridLayout(5, 2, 5, 5)); // Ajustado a 5 filas
        panelAgregar.setBorder(BorderFactory.createTitledBorder("Agregar Nueva Tarea"));

        JTextField campoNombre = new JTextField();
        JTextField campoDescripcion = new JTextField();

        // <-- CAMBIO 1: Creamos las opciones y el JComboBox en lugar del JTextField.
        String[] opcionesFrecuencia = {"Diaria", "Semanal", "Mensual"};
        JComboBox<String> comboFrecuencia = new JComboBox<>(opcionesFrecuencia);


        JTextField campoFrecuencia = new JTextField();
        JCheckBox checkEstado = new JCheckBox("Activa");
        checkEstado.setSelected(true);
        JButton botonAgregar = new JButton("Agregar Tarea");

        panelAgregar.add(new JLabel("Nombre Tarea:"));
        panelAgregar.add(campoNombre);
        panelAgregar.add(new JLabel("Descripción:"));
        panelAgregar.add(campoDescripcion);
        panelAgregar.add(new JLabel("Frecuencia:"));
        panelAgregar.add(comboFrecuencia);
        
        panelAgregar.add(new JLabel(""));
        panelAgregar.add(botonAgregar);

         // --- Panel para eliminar ---
        JPanel panelEliminar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEliminar.setBorder(BorderFactory.createTitledBorder("Eliminar Tarea por ID"));
        campoIdEliminar = new JTextField(5);
        JButton botonEliminar = new JButton("Eliminar");
        panelEliminar.add(new JLabel("ID Tarea:"));
        panelEliminar.add(campoIdEliminar);
        panelEliminar.add(botonEliminar);

        panelSuperior.add(panelAgregar, BorderLayout.CENTER);
        panelSuperior.add(panelEliminar, BorderLayout.SOUTH);

        // --- Área de texto para mostrar la lista de tareas ---
        areaTareas = new JTextArea();
        areaTareas.setEditable(false);
        areaTareas.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollLista = new JScrollPane(areaTareas);

        // --- Botón para actualizar ---
        JButton botonActualizar = new JButton("Actualizar Lista de Tareas");

        add(panelSuperior, BorderLayout.NORTH); // Panel combinado arriba
        add(scrollLista, BorderLayout.CENTER);
        add(botonActualizar, BorderLayout.SOUTH);

        // --- Lógica de los Botones ---
        botonAgregar.addActionListener(e -> {
            String nombre = campoNombre.getText();
            String descripcion = campoDescripcion.getText();
            String frecuencia = (String) comboFrecuencia.getSelectedItem();
            boolean estado = false;

            if (nombre.isEmpty() || descripcion.isEmpty() || frecuencia.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, completa Nombre, Descripción y Frecuencia.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Tareas nuevaTarea = new Tareas(0, nombre, descripcion, frecuencia, new Date(System.currentTimeMillis()), estado);
            tareasDao.agregarTarea(nuevaTarea);

            JOptionPane.showMessageDialog(this, "Tarea agregada con éxito.");
            campoNombre.setText("");
            campoDescripcion.setText("");
            campoFrecuencia.setText("");
            
            
            actualizarTareas();
        });

         botonEliminar.addActionListener(e -> {
            String idStr = campoIdEliminar.getText();
            if (idStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Introduce el ID de la tarea a eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                int id = Integer.parseInt(idStr);
                int confirmacion = JOptionPane.showConfirmDialog(this,
                        "¿Estás seguro de que deseas eliminar la tarea con ID " + id + "?",
                        "Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    tareasDao.eliminarTarea(id); // Asegúrate que TareasDao tenga este método implementado correctamente
                    JOptionPane.showMessageDialog(this, "Tarea eliminada correctamente.");
                    campoIdEliminar.setText("");
                    actualizarTareas();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número entero.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });


        botonActualizar.addActionListener(e -> actualizarTareas());
        actualizarTareas();
    }

    public void actualizarTareas() {
        areaTareas.setText("");
        List<Tareas> lista = tareasDao.listaRTarea();
         if (lista.isEmpty()) {
             areaTareas.setText("No hay tareas registradas.");
         } else {
            for (Tareas t : lista) {
                //String estadoStr = t.isestado() ? "Activa" : "Inactiva";
                areaTareas.append("ID: " + t.getid_tarea() + " - " + t.getnombre() + " (" + t.getfrecuencia() + ") "  + "\n");
                areaTareas.append("  Descripción: " + t.getdescripcion() + "\n\n");
            }
         }
    }
}