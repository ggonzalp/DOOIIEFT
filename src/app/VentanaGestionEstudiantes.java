package app;

import controlador.Controlador;
import modelo.Estudiante;
import util.Eliminar;
import util.Modificar;
import util.Registrar;
import util.Visualizar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class VentanaGestionEstudiantes extends JFrame implements Registrar, Modificar, Eliminar, Visualizar {
    private JPanel panelGestionEstudiantes;
    private JTextField txtNombre;
    private JTextField txtCurso;
    private JTextField txtRut;
    private JTextField txtCorreo;
    private JButton btnEliminar;
    private JButton btnModificar;
    private JButton btnVisualizar;
    private JButton btnRegistrar;
    private JTable tablaEstudiantes;

    private final Controlador controlador;
    private DefaultTableModel tableModel;
    private List<Estudiante> estudiantes = new ArrayList<>();

    public VentanaGestionEstudiantes(Controlador controlador) {
        this.controlador = controlador;

        configurarVentana();
        configurarTabla();
        configurarBotones();
        cargarTabla();
    }

    public void configurarVentana() {
        setContentPane(panelGestionEstudiantes);
        setTitle("Biblioteca Escolar");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void configurarTabla() {
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "RUT", "Curso", "Correo"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaEstudiantes.setModel(tableModel);
        tablaEstudiantes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaEstudiantes.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaEstudiantes.getSelectedRow();
            if (fila != -1) {
                mostrarEnFormulario(estudiantes.get(fila));
            }
        });
    }

    public void configurarBotones() {
        btnRegistrar.addActionListener(e -> registrar());
        btnModificar.addActionListener(e -> modificar());
        btnEliminar.addActionListener(e -> eliminar());
        btnVisualizar.addActionListener(e -> visualizar());
    }

    private void cargarTabla() {
        estudiantes = controlador.obtenerEstudiante();
        tableModel.setRowCount(0);

        for (Estudiante estudiante : estudiantes) {
            tableModel.addRow(new Object[]{
                    estudiante.getIdEstudiante(),
                    estudiante.getNombre(),
                    estudiante.getRut().getNumero(),
                    estudiante.getCurso(),
                    estudiante.getCorreo()
            });
        }
    }

    private void mostrarEnFormulario(Estudiante estudiante) {
        txtNombre.setText(estudiante.getNombre());
        txtRut.setText(estudiante.getRut().getNumero());
        txtCurso.setText(estudiante.getCurso());
        txtCorreo.setText(estudiante.getCorreo());
    }

    private boolean faltanDatos() {
        return txtNombre.getText().isBlank() || txtRut.getText().isBlank()
                || txtCurso.getText().isBlank() || txtCorreo.getText().isBlank();
    }
    @Override
    public void registrar() {
        if (faltanDatos()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        if (controlador.registrarEstudiante(txtNombre.getText(), txtRut.getText(), txtCurso.getText(), txtCorreo.getText())) {
            JOptionPane.showMessageDialog(this, "Estudiante correctamente registrado.");
            limpiar();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar. Revise que el Rut tenga el formato 12345678-9 o K.");
        }
    }


    @Override
    public void modificar() {
        int fila =  tablaEstudiantes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione en la tabla el estudiante que desea modificar.");
            return;
        }

        if (faltanDatos()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.");
            return;
        }

        int idEstudiante = estudiantes.get(fila).getIdEstudiante();

        if (controlador.editarEstudiante(idEstudiante, txtNombre.getText(), txtRut.getText(), txtCurso.getText(), txtCorreo.getText())) {
            JOptionPane.showMessageDialog(this, "Estudiante actualizado.");
            limpiar();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar. Revise que el RUT sea del tipo 12345678-9 y que no esté repetido.");
        }
    }

    @Override
    public void eliminar() {
        int fila = tablaEstudiantes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione en la tabla el estudiante que desea eliminar.");
            return;
        }

        Estudiante estudiante = estudiantes.get(fila);

        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Eliminar a " + estudiante.getNombre() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        if (controlador.eliminarEstudiante(estudiante.getIdEstudiante())) {
            JOptionPane.showMessageDialog(this, "Estudiante eliminado.");
            limpiar();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar. Revise que no tenga préstamos registrados.");
        }
    }

    private void limpiar() {
        txtNombre.setText("");
        txtRut.setText("");
        txtCurso.setText("");
        txtCorreo.setText("");
        tablaEstudiantes.clearSelection();
        txtNombre.requestFocus();
    }

    @Override
    public void visualizar() {
        limpiar();
        cargarTabla();
    }
}


