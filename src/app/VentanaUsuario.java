package app;

import controlador.Controlador;
import modelo.Usuario;

import javax.swing.*;

public class VentanaUsuario extends JFrame{

    private JPanel panelGestion;
    private JButton btnEstudiantes;
    private JButton btnLibros;
    private JButton btnReportes;
    private JButton btnCerrarSesion;
    private JButton btnCategorias;

    private final Controlador controlador;

    public VentanaUsuario(Controlador controlador, Usuario usuario) {
        this.controlador = controlador;

        configurarVentana();
        configurarBotones();
    }

    public void configurarVentana() {
        setContentPane(panelGestion);
        setTitle("Biblioteca Escolar");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void configurarBotones() {
        btnEstudiantes.addActionListener(e -> new VentanaGestionEstudiantes(controlador).setVisible(true));
        btnLibros.addActionListener(e -> new VentanaGestionLibros(controlador).setVisible(true));
        btnCategorias.addActionListener(e -> new VentanaGestionCategorias(controlador).setVisible(true));
        btnReportes.addActionListener(e -> new VentanaReportes(controlador).setVisible(true));
        btnCerrarSesion.addActionListener(e -> dispose());

    }
}
