import javax.swing.*;
import Ventanas.VentanaUsuario;
import Ventanas.VentanaTareas;
import Ventanas.VentanaHogar;
public class AppPrincipal {

    public static void main(String[] args) {
        // Asegura que la interfaz gráfica se ejecute en el hilo correcto
        SwingUtilities.invokeLater(() -> {

            // 1. Crear la ventana principal
            JFrame ventana = new JFrame("Gestor de Convivencia para Roommates");
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setSize(800, 600);

            // 2. Crear el contenedor de pestañas
            JTabbedPane pestanas = new JTabbedPane();

            // 3. Crear una instancia de cada panel que hemos diseñado
            // Se asume que ahora existe VentanaUsuario.java
            VentanaUsuario panelUsuarios = new VentanaUsuario();
            VentanaTareas panelTareas = new VentanaTareas();
            VentanaHogar panelHogar = new VentanaHogar();
           //VentanaAsignacion panelAsignacion = new VentanaAsignacion();
           // VentanaInformes panelInformes = new VentanaInformes();


            // 4. Añadir cada panel como una pestaña
            pestanas.addTab("👥 Usuarios", panelUsuarios); // Pestaña añadida
            pestanas.addTab("✔️ Tareas", panelTareas);
            pestanas.addTab("🏠 Hogar", panelHogar);
           // pestanas.addTab("📝 Asignaciones", panelAsignacion); // Pestaña añadida
          //  pestanas.addTab("📊 Informes", panelInformes); // Pestaña añadida


            // 5. Añadir el panel de pestañas a la ventana principal
            ventana.add(pestanas);

            // 6. Centrar y hacer visible la ventana
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
        });
    }
}