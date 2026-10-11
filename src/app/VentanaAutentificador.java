package app;

import controlador.Controlador;
import modelo.Usuario;

import javax.swing.*;

public class VentanaAutentificador extends JFrame {
    private JPanel panelAutentificador;
    private JTextField correo;
    private JPasswordField contrasena;
    private JButton btnLimpiar;
    private JButton btnIngresar;
    private JButton btnSalir;

    private final Controlador controlador;

    public VentanaAutentificador(Controlador controlador) {
        this.controlador = controlador;

        setContentPane(panelAutentificador);
        setTitle("Autentificación del Usuario");
        setSize(320, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        getRootPane().setDefaultButton(btnIngresar);

        btnLimpiar.addActionListener(e -> limpiar());
        btnIngresar.addActionListener(e -> ingresar());
        btnSalir.addActionListener(e -> salir());
    }

    private void ingresar() {
        String textoCorreo = correo.getText().trim();
        String textoContrasena = new String(contrasena.getPassword());

        if (textoCorreo.isEmpty() || textoContrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete correo y contraseña");
            return;
        }

        Usuario usuario = controlador.iniciarSesion(textoCorreo, textoContrasena);

        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Correo o contraseña incorrectos");
            contrasena.setText("");
            return;
        }

        if (!controlador.esBibliotecario(usuario)) {
            JOptionPane.showMessageDialog(this, "Acceso solo para personal de la biblioteca");
            return;
        }
        Object[] opciones = {"Ingresar al Sistema"};
        JOptionPane.showOptionDialog(this, "Bienvenid@ " + usuario.getNombreUsuario(), "Inicio de sesión",
        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
        dispose();
        new VentanaUsuario(controlador, usuario).setVisible(true);
    }

    private void limpiar() {
        correo.setText("");
        contrasena.setText("");
        correo.requestFocus();
    }

    private void salir() {
        dispose();
    }

}