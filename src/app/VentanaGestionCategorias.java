package app;

import controlador.Controlador;
import modelo.Usuario;

import javax.swing.*;

public class VentanaGestionCategorias extends JFrame{
    private JPanel panelGestionCategorias;

    private final Controlador controlador;

    public VentanaGestionCategorias(Controlador controlador) {
        this.controlador = controlador;

        configurarVentana();
        configurarBotones();
    }

    public void configurarVentana() {
        setContentPane(panelGestionCategorias);
        setTitle("Biblioteca Escolar");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void configurarBotones() {

    }
}
