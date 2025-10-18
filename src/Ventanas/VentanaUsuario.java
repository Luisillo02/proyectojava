package Ventanas;
import DAOs.UsuarioDao;
import Modelos.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaUsuario extends JPanel {

    private UsuarioDao usuarioDao = new UsuarioDao();
    private JTextArea areaUsuarios;
    private JTextField campoIdEliminar; // Campo para ID a eliminar

    public VentanaUsuario() {
        setLayout(new BorderLayout(5, 5)); // Añadir espacio entre componentes
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen exterior

        // --- Panel Superior (Agregar y Eliminar) ---
        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));

        // --- Panel para agregar ---
        JPanel panelAgregar = new JPanel(new GridLayout(4, 2, 5, 5));
        panelAgregar.setBorder(BorderFactory.createTitledBorder("Agregar Nuevo Usuario"));

        JTextField campoNombre = new JTextField();
        JTextField campoApellido = new JTextField();
        JTextField campoEmail = new JTextField();
        JButton botonAgregar = new JButton("Agregar Usuario");

        panelAgregar.add(new JLabel("Nombre:"));
        panelAgregar.add(campoNombre);
        panelAgregar.add(new JLabel("Apellido:"));
        panelAgregar.add(campoApellido);
        panelAgregar.add(new JLabel("Email:"));
        panelAgregar.add(campoEmail);
        panelAgregar.add(new JLabel(""));
        panelAgregar.add(botonAgregar);

        // --- Panel para eliminar ---
        JPanel panelEliminar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEliminar.setBorder(BorderFactory.createTitledBorder("Eliminar Usuario por ID"));
        campoIdEliminar = new JTextField(5); // Campo más pequeño para ID
        JButton botonEliminar = new JButton("Eliminar");
        panelEliminar.add(new JLabel("ID Usuario:"));
        panelEliminar.add(campoIdEliminar);
        panelEliminar.add(botonEliminar);


        panelSuperior.add(panelAgregar, BorderLayout.CENTER);
        panelSuperior.add(panelEliminar, BorderLayout.SOUTH);


        // --- Área para mostrar la lista ---
        areaUsuarios = new JTextArea();
        areaUsuarios.setEditable(false);
        areaUsuarios.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollLista = new JScrollPane(areaUsuarios);

        // --- Botón de actualización ---
        JButton botonActualizar = new JButton("Actualizar Lista");

        add(panelSuperior, BorderLayout.NORTH); // Panel combinado arriba
        add(scrollLista, BorderLayout.CENTER);
        add(botonActualizar, BorderLayout.SOUTH);

        // --- Lógica de los botones ---
        botonAgregar.addActionListener(e -> {
            String nombre = campoNombre.getText();
            String apellido = campoApellido.getText();
            String email = campoEmail.getText();

            if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!email.contains("@") || !email.contains(".")) {
                 JOptionPane.showMessageDialog(this, "Introduce un email válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario nuevoUsuario = new Usuario(0, nombre, apellido, email);
            usuarioDao.agregarUsuario(nuevoUsuario);

            JOptionPane.showMessageDialog(this, "Usuario agregado correctamente.");
            campoNombre.setText("");
            campoApellido.setText("");
            campoEmail.setText("");
            actualizarListaUsuarios();
        });

        botonEliminar.addActionListener(e -> {
            String idStr = campoIdEliminar.getText();
            if (idStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Introduce el ID del usuario a eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                int id = Integer.parseInt(idStr);
                int confirmacion = JOptionPane.showConfirmDialog(this,
                        "¿Estás seguro de que deseas eliminar al usuario con ID " + id + "?",
                        "Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    usuarioDao.eliminarUsuario(id);
                    JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.");
                    campoIdEliminar.setText(""); // Limpiar campo
                    actualizarListaUsuarios();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número entero.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });


        botonActualizar.addActionListener(e -> actualizarListaUsuarios());
        actualizarListaUsuarios();
    }

    private void actualizarListaUsuarios() {
        areaUsuarios.setText("");
        List<Usuario> lista = usuarioDao.listaRUsuarios();
        if (lista.isEmpty()) {
            areaUsuarios.setText("No hay usuarios registrados.");
        } else {
            for (Usuario u : lista) {
                areaUsuarios.append("ID: " + u.getid_Usuario() + " | Nombre: " + u.getNombre() + " " + u.getApellido() + " | Email: " + u.getEmail() + "\n");
            }
        }
    }
}
