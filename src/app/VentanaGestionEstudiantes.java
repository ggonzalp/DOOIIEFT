package app;

import controlador.Controlador;

import javax.swing.*;

public class VentanaGestionEstudiantes extends JFrame {
    private JPanel panelGestionEstudiantes;

    private final Controlador controlador;

    public VentanaGestionEstudiantes(Controlador controlador) {
        this.controlador = controlador;

        configurarVentana();
        configurarBotones();
    }

    public void configurarVentana() {
        setContentPane(panelGestionEstudiantes);
        setTitle("Biblioteca Escolar");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void configurarBotones() {

    }
}
