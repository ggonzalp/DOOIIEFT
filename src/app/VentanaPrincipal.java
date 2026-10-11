package app;

import controlador.Controlador;

import javax.swing.*;

public class VentanaPrincipal extends JFrame {
    private JPanel panelPrincipal;
    private JButton btnEstudiante;
    private JButton btnUsuario;

    private final Controlador controlador;

    public VentanaPrincipal() {
        controlador = new Controlador();

        configurarVentana();
        configurarBotones();
    }

    public void configurarVentana() {
        setContentPane(panelPrincipal);
        setTitle("Biblioteca Escolar");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void configurarBotones(){
        btnUsuario.addActionListener(e -> {
            VentanaAutentificador ventanaAutentificador = new VentanaAutentificador(controlador);
            ventanaAutentificador.setVisible(true);
        });

        btnEstudiante.addActionListener(e -> {
            VentanaEstudiante ventanaEstudiante = new VentanaEstudiante(controlador);
            ventanaEstudiante.setVisible(true);
        });
    }
}
