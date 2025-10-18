import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaHogar extends JPanel {

    private HogarDao hogarDao = new HogarDao();
    private JTextArea areaHogares;

    public VentanaHogar() {
        setLayout(new BorderLayout());

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

        // --- Área para mostrar la lista ---
        areaHogares = new JTextArea();
        areaHogares.setEditable(false);
        JScrollPane scrollLista = new JScrollPane(areaHogares);

        // --- Botón de actualización ---
        JButton botonActualizar = new JButton("Actualizar Lista");
        
        add(panelAgregar, BorderLayout.NORTH);
        add(scrollLista, BorderLayout.CENTER);
        add(botonActualizar, BorderLayout.SOUTH);

        // --- Lógica de los botones ---
        botonAgregar.addActionListener(e -> {
            String nombre = campoNombre.getText();
            String direccion = campoDireccion.getText();

            if (nombre.isEmpty() || direccion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ambos campos son obligatorios.");
                return;
            }

            Hogar nuevoHogar = new Hogar(0, nombre, direccion);
            hogarDao.agregarHogar(nuevoHogar);
            
            JOptionPane.showMessageDialog(this, "Hogar agregado correctamente.");
            campoNombre.setText("");
            campoDireccion.setText("");
            actualizarListaHogares();
        });

        botonActualizar.addActionListener(e -> actualizarListaHogares());
        actualizarListaHogares();
    }

    private void actualizarListaHogares() {
        areaHogares.setText("");
        List<Hogar> lista = hogarDao.listaRHogar();
        for (Hogar h : lista) {
            areaHogares.append("ID: " + h.getIdHogar() + " | Nombre: " + h.getNombre() + " | Dirección: " + h.getDireccion() + "\n");
        }
    }
}
