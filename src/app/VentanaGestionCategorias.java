package app;

import controlador.Controlador;
import modelo.Categoria;
import util.Eliminar;
import util.Modificar;
import util.Registrar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class VentanaGestionCategorias extends JFrame implements Registrar, Modificar, Eliminar {
    private JPanel panelGestionCategorias;
    private JButton btnGuardar;
    private JButton btnEditar;
    private JTextField txtNombre;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JTable tablaCategorias;

    private final Controlador controlador;
    private DefaultTableModel tableModel;
    private List<Categoria> categorias = new ArrayList<>();

    public VentanaGestionCategorias(Controlador controlador) {
        this.controlador = controlador;

        configurarVentana();
        configurarBotones();
        configurarTabla();
        cargarTabla();
    }

    public void configurarVentana() {
        setContentPane(panelGestionCategorias);
        setTitle("Biblioteca Escolar");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void configurarTabla() {
        tableModel = new DefaultTableModel(new Object[]{"ID", "Categoría"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaCategorias.setModel(tableModel);
        tablaCategorias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Al hacer clic en la fila, su nombre pasa al campo de texto.
        tablaCategorias.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaCategorias.getSelectedRow();
            if (fila != -1) {
                txtNombre.setText(categorias.get(fila).getNombre());
            }
        });
    }


    public void configurarBotones() {
        btnGuardar.addActionListener(e -> registrar());
        btnEditar.addActionListener(e -> modificar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiar());
    }

    private void cargarTabla() {
        categorias = controlador.obtenerCategorias();
        tableModel.setRowCount(0);

        for (Categoria categoria : categorias) {
            tableModel.addRow(new Object[]{categoria.getIdCategoria(), categoria.getNombre()});
        }
    }

    @Override
    public void registrar() {
        if (txtNombre.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Escriba el nombre de la categoría.");
            return;
        }

        if (controlador.registrarCategoria(txtNombre.getText())) {
            JOptionPane.showMessageDialog(this, "Categoría guardada.");
            limpiar();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo guardar la categoría.");
        }
    }

    @Override
    public void modificar() {
        int fila = tablaCategorias.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione en la tabla la categoría que desea editar.");
            return;
        }

        if(txtNombre.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Escriba el nuevo nombre de la categoría.");
            return;
        }

        Categoria categoria = categorias.get(fila);

        if(controlador.editarCategoria(categoria.getIdCategoria(), txtNombre.getText())) {
            JOptionPane.showMessageDialog(this, "Categoría actualizada.");
            limpiar();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar la categoría.");
        }
    }

    @Override
    public void eliminar() {
        int fila = tablaCategorias.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione en la tabla la categoría a eliminar");
            return;
        }

        Categoria categoria = categorias.get(fila);

        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Desea eliminar la categoría \"" + categoria.getNombre() + "\"?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        if (controlador.eliminarCategoria(categoria.getIdCategoria())) {
            JOptionPane.showMessageDialog(this, "Categoría eliminada.");
            limpiar();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar. Revise que no tenga libros asociados.");
        }
    }

    private void limpiar() {
        txtNombre.setText("");
        tablaCategorias.clearSelection();
        txtNombre.requestFocus();
    }
}
