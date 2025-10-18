import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaHogar extends JPanel {

    private HogarDao hogarDao = new HogarDao();
    private JTextArea areaHogares;
    private JTextField campoIdEliminar; // Campo para ID a eliminar

    public VentanaHogar() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Panel Superior (Agregar y Eliminar) ---
        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));

        // --- Panel para agregar ---
        JPanel panelAgregar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAgregar.setBorder(BorderFactory.createTitledBorder("Agregar Nuevo Hogar"));

        JTextField campoNombre = new JTextField(15);
        JTextField campoDireccion = new JTextField(20);
        JButton botonAgregar = new JButton("Agregar Hogar");

        panelAgregar.add(new JLabel("Nombre:"));
        panelAgregar.add(campoNombre);
        panelAgregar.add(new JLabel("Dirección:"));
        panelAgregar.add(campoDireccion);
        panelAgregar.add(botonAgregar);

        // --- Panel para eliminar ---
        JPanel panelEliminar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEliminar.setBorder(BorderFactory.createTitledBorder("Eliminar Hogar por ID"));
        campoIdEliminar = new JTextField(5);
        JButton botonEliminar = new JButton("Eliminar");
        panelEliminar.add(new JLabel("ID Hogar:"));
        panelEliminar.add(campoIdEliminar);
        panelEliminar.add(botonEliminar);

        panelSuperior.add(panelAgregar, BorderLayout.CENTER);
        panelSuperior.add(panelEliminar, BorderLayout.SOUTH);

        // --- Área para mostrar la lista ---
        areaHogares = new JTextArea();
        areaHogares.setEditable(false);
        areaHogares.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollLista = new JScrollPane(areaHogares);

        // --- Botón de actualización ---
        JButton botonActualizar = new JButton("Actualizar Lista");

        add(panelSuperior, BorderLayout.NORTH); // Panel combinado arriba
        add(scrollLista, BorderLayout.CENTER);
        add(botonActualizar, BorderLayout.SOUTH);

        // --- Lógica de los botones ---
        botonAgregar.addActionListener(e -> {
            String nombre = campoNombre.getText();
            String direccion = campoDireccion.getText();

            if (nombre.isEmpty() || direccion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ambos campos (Nombre y Dirección) son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Hogar nuevoHogar = new Hogar(0, nombre, direccion);
            hogarDao.agregarHogar(nuevoHogar);

            JOptionPane.showMessageDialog(this, "Hogar agregado correctamente.");
            campoNombre.setText("");
            campoDireccion.setText("");
            actualizarListaHogares();
        });

        botonEliminar.addActionListener(e -> {
            String idStr = campoIdEliminar.getText();
            if (idStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Introduce el ID del hogar a eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                int id = Integer.parseInt(idStr);
                int confirmacion = JOptionPane.showConfirmDialog(this,
                        "¿Estás seguro de que deseas eliminar el hogar con ID " + id + "?",
                        "Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    hogarDao.eliminarHogar(id); // Asegúrate que HogarDao tenga este método implementado correctamente
                    JOptionPane.showMessageDialog(this, "Hogar eliminado correctamente.");
                    campoIdEliminar.setText("");
                    actualizarListaHogares();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número entero.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });


        botonActualizar.addActionListener(e -> actualizarListaHogares());
        actualizarListaHogares();
    }

    private void actualizarListaHogares() {
        areaHogares.setText("");
        List<Hogar> lista = hogarDao.listaRHogar();
         if (lista.isEmpty()) {
             areaHogares.setText("No hay hogares registrados.");
         } else {
            for (Hogar h : lista) {
                areaHogares.append("ID: " + h.getIdHogar() + " | Nombre: " + h.getNombre() + " | Dirección: " + h.getDireccion() + "\n");
            }
         }
    }
}