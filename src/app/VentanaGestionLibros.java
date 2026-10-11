package app;

import controlador.Controlador;
import modelo.Usuario;

import javax.swing.*;

public class VentanaGestionLibros extends  JFrame{
    private JPanel panelGestionLibros;

    private final Controlador controlador;

    public VentanaGestionLibros(Controlador controlador) {
        this.controlador = controlador;

        configurarVentana();
        configurarBotones();
    }

    public void configurarVentana() {
        setContentPane(panelGestionLibros);
        setTitle("Biblioteca Escolar");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void configurarBotones() {

    }
}
