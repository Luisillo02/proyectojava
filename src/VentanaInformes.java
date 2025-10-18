import javax.swing.*;
import java.awt.*;

public class VentanaInformes extends JPanel {

    public VentanaInformes() {
        setLayout(new BorderLayout());

        JLabel etiquetaTitulo = new JLabel("Ventana de Informes (En construcción)");
        etiquetaTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 16));

        JTextArea areaInformes = new JTextArea();
        areaInformes.setEditable(false);
        areaInformes.setText("\nAquí se podrían mostrar resúmenes:\n\n" +
                             "- Tareas completadas por usuario.\n" +
                             "- Tareas pendientes.\n" +
                             "- Frecuencia de realización de tareas.\n" +
                             "- Etc.");
        areaInformes.setMargin(new Insets(10, 10, 10, 10));

        add(etiquetaTitulo, BorderLayout.NORTH);
        add(new JScrollPane(areaInformes), BorderLayout.CENTER);
    }

    // Aquí podrías agregar métodos para generar y mostrar informes específicos
    // consultando los DAOs necesarios.
}