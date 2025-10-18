import java.awt.*;
import java.sql.Date;
import java.util.List;
import javax.swing.*;

public class VentanaTareas extends JPanel {

    private TareasDao tareasDao = new TareasDao();
    private JTextArea areaTareas;

    public VentanaTareas() {
        setLayout(new BorderLayout());

        // --- Panel para agregar nuevas tareas ---
        JPanel panelAgregar = new JPanel(new GridLayout(6, 2, 5, 5));
        panelAgregar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField campoNombre = new JTextField();
        JTextField campoDescripcion = new JTextField();
        JTextField campoFrecuencia = new JTextField();
        JCheckBox checkEstado = new JCheckBox("Activa");
        checkEstado.setSelected(true);
        JButton botonAgregar = new JButton("Agregar Tarea");

        panelAgregar.add(new JLabel("Nombre Tarea:"));
        panelAgregar.add(campoNombre);
        panelAgregar.add(new JLabel("Descripción:"));
        panelAgregar.add(campoDescripcion);
        panelAgregar.add(new JLabel("Frecuencia (Ej: Diaria):"));
        panelAgregar.add(campoFrecuencia);
        panelAgregar.add(new JLabel("Estado:"));
        panelAgregar.add(checkEstado);
        panelAgregar.add(new JLabel(""));
        panelAgregar.add(botonAgregar);

        // --- Área de texto para mostrar la lista de tareas ---
        areaTareas = new JTextArea();
        areaTareas.setEditable(false);
        areaTareas.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollLista = new JScrollPane(areaTareas);

        // --- Botón para actualizar ---
        JButton botonActualizar = new JButton("Actualizar Lista de Tareas");

        add(panelAgregar, BorderLayout.NORTH);
        add(scrollLista, BorderLayout.CENTER);
        add(botonActualizar, BorderLayout.SOUTH);

        // --- Lógica de los Botones ---
        botonAgregar.addActionListener(e -> {
            String nombre = campoNombre.getText();
            String descripcion = campoDescripcion.getText();
            String frecuencia = campoFrecuencia.getText();
            boolean estado = checkEstado.isSelected();

            if (nombre.isEmpty() || descripcion.isEmpty() || frecuencia.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
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

        botonActualizar.addActionListener(e -> actualizarTareas());
        actualizarTareas();
    }

    public void actualizarTareas() {
        areaTareas.setText("");
        List<Tareas> lista = tareasDao.listaRTarea();
        for (Tareas t : lista) {
            String estado = t.isestado() ? "Activa" : "Inactiva";
            areaTareas.append("ID: " + t.getid_tarea() + " - " + t.getnombre() + " (" + t.getfrecuencia() + ") - Estado: " + estado + "\n");
            areaTareas.append("  Descripción: " + t.getdescripcion() + "\n\n");
        }
    }

}