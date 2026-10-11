package app;

import controlador.Controlador;
import modelo.Usuario;

import javax.swing.*;

public class VentanaReportes extends JFrame {
    private JPanel panelReportes;

    private final Controlador controlador;

    public VentanaReportes(Controlador controlador) {
        this.controlador = controlador;

        configurarVentana();
        configurarBotones();
    }

    public void configurarVentana() {
        setContentPane(panelReportes);
        setTitle("Biblioteca Escolar");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void configurarBotones() {

    }
}
