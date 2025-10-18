import javax.swing.*;

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
            // (Asegúrate de tener también un PanelUsuarios.java similar a los otros)
            // PanelUsuarios panelUsuarios = new PanelUsuarios(); 
            VentanaTareas panelTareas = new VentanaTareas();
            VentanaHogar panelHogar = new VentanaHogar();
            
            // 4. Añadir cada panel como una pestaña
            // pestanas.addTab("👥 Usuarios", panelUsuarios);
            pestanas.addTab("✔️ Tareas", panelTareas);
            pestanas.addTab("🏠 Hogar", panelHogar);
            
            // 5. Añadir el panel de pestañas a la ventana principal
            ventana.add(pestanas);
            
            // 6. Centrar y hacer visible la ventana
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
        });
    }
}
