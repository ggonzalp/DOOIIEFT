package app;

import javax.swing.*;

public class VentanaPrincipal extends JFrame {
    private JPanel ventanaPrincipal;
    private JButton button1;
    private JButton button2;
    private JButton usuarioButton;

    public VentanaPrincipal() {
        setContentPane(ventanaPrincipal);
        setTitle("Biblioteca Escolar");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
