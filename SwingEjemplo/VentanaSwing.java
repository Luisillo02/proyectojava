import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VentanaSwing extends JFrame {

    public VentanaSwing() {
        // Título de la ventana
        setTitle("Ejemplo Swing en VS Code");

        // Tamaño de la ventana
        setSize(400, 200);

        // Cerrar la aplicación al cerrar la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear un panel
        JPanel panel = new JPanel();

        // Crear componentes
        JLabel etiqueta = new JLabel("¡Hola desde Swing!");
        JButton boton = new JButton("Click aquí");

        // Agregar componentes al panel
        panel.add(etiqueta);
        panel.add(boton);

        // Agregar panel a la ventana
        add(panel);
    }
}

