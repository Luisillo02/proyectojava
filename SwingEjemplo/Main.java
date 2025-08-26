public class Main {
    public static void main(String[] args) {
        // Ejecutar la GUI en el hilo de eventos de Swing
        javax.swing.SwingUtilities.invokeLater(() -> {
            VentanaSwing ventana = new VentanaSwing();
            ventana.setVisible(true);
        });
    }
}



