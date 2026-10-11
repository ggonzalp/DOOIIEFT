package app;

import controlador.Controlador;
import modelo.Categoria;
import modelo.Libro;
import util.Eliminar;
import util.Modificar;
import util.Registrar;
import util.Visualizar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class VentanaGestionLibros extends JFrame implements Registrar, Modificar, Eliminar, Visualizar {
    private JPanel panelGestionLibros;
    private JTextField txtIsbn;
    private JTextField txtStock;
    private JTextField txtAutor;
    private JTextField txtEditorial;
    private JComboBox comboCategorias;
    private JTextField txtTitulo;
    private JButton btnVisualizar;
    private JButton btnModificar;
    private JButton btnRegistrar;
    private JButton btnEliminar;
    private JTable tablaLibros;

    private final Controlador controlador;
    private DefaultTableModel tableModel;
    private List<Libro> libros = new ArrayList<>();

    public VentanaGestionLibros(Controlador controlador) {
        this.controlador = controlador;

        configurarVentana();
        configurarTabla();
        configurarBotones();
        cargarCategorias();
        cargarTabla();
    }

    public void configurarVentana() {
        setContentPane(panelGestionLibros);
        setTitle("Biblioteca Escolar");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void configurarTabla() {
        tableModel = new DefaultTableModel(new Object[]{"ID", "Título", "Autor", "ISBN", "Editorial", "Stock", "Categoría"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tablaLibros.setModel(tableModel);
        tablaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaLibros.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaLibros.getSelectedRow();
            if (fila != -1) {
                mostrarEnFormulario(libros.get(fila));
            }
        });
    }

    public void configurarBotones() {
        btnRegistrar.addActionListener(e -> registrar());
        btnModificar.addActionListener(e -> modificar());
        btnEliminar.addActionListener(e -> eliminar());
        btnVisualizar.addActionListener(e -> visualizar());
    }

    private void cargarCategorias() {
        comboCategorias.removeAllItems();
        for (Categoria categoria : controlador.obtenerCategorias()) {
            comboCategorias.addItem(categoria);
        }
    }

    private void cargarTabla() {
        libros = controlador.obtenerLibro();
        tableModel.setRowCount(0);

        for (Libro libro : libros) {
            tableModel.addRow(new Object[]{
                    libro.getIdLibro(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getIsbn(),
                    libro.getEditorial(),
                    libro.getStock(),
                    libro.getCategoria().getNombre()
            });
        }
    }

    private void mostrarEnFormulario(Libro libro) {
        txtTitulo.setText(libro.getTitulo());
        txtAutor.setText(libro.getAutor());
        txtIsbn.setText(String.valueOf(libro.getIsbn()));
        txtEditorial.setText(libro.getEditorial());
        txtStock.setText(String.valueOf(libro.getStock()));

        // Busca en el combo la categoría del libro, comparando por id.
        for (int i = 0; i < comboCategorias.getItemCount(); i++) {
            if (((Categoria)comboCategorias.getItemAt(i)).getIdCategoria() == libro.getCategoria().getIdCategoria()) {
                comboCategorias.setSelectedIndex(i);
            }
        }
    }

    private boolean faltanDatos() {
        return txtTitulo.getText().isBlank() || txtAutor.getText().isBlank() || txtIsbn.getText().isBlank()
                || txtEditorial.getText().isBlank() || txtStock.getText().isBlank() || comboCategorias.getSelectedItem() == null;
    }
    @Override
    public void registrar() {
        if (faltanDatos()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.");
            return;
        }

        try {
            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            int isbn = Integer.parseInt(txtIsbn.getText().trim());
            String editorial = txtEditorial.getText().trim();
            int stock = Integer.parseInt(txtStock.getText().trim());
            Categoria categoria = (Categoria) comboCategorias.getSelectedItem();

            if (controlador.registrarLibro(titulo, autor, isbn, editorial, stock, categoria)) {
                JOptionPane.showMessageDialog(this, "Libro guardado.");
                limpiar();
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo guardar. Revise que no falten datos y que el ISBN no esté repetido.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ISBN y stock deben ser números enteros.");
        }
    }
    @Override
    public void modificar() {
        int fila = tablaLibros.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione en la tabla el libro que desea editar.");
            return;
        }

        if (faltanDatos()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        try {
            int idLibro = libros.get(fila).getIdLibro();
            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            int isbn = Integer.parseInt(txtIsbn.getText().trim());
            String editorial = txtEditorial.getText().trim();
            int stock = Integer.parseInt(txtStock.getText().trim());
            Categoria categoria = (Categoria) comboCategorias.getSelectedItem();

            if (controlador.editarLibro(idLibro, titulo, autor, isbn, editorial, stock, categoria)) {
                JOptionPane.showMessageDialog(this, "Libro actualizado.");
                limpiar();
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar. Revise que no falten datos y que el ISBN no esté repetido.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ISBN y stock deben ser números enteros.");
        }
    }

    @Override
    public void eliminar() {
        int fila = tablaLibros.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione en la tabla el libro que desea eliminar.");
            return;
        }

        Libro libro = libros.get(fila);

        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el libro \"" + libro.getTitulo() + "\"?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        if (controlador.eliminar(libro.getIdLibro())) {
            JOptionPane.showMessageDialog(this, "Libro eliminado.");
            limpiar();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar. Revise que no tenga préstamos registrados.");
        }
    }

    @Override
    public void visualizar() {
        limpiar();
        cargarCategorias();
        cargarTabla();
    }

    private void limpiar() {
        txtTitulo.setText("");
        txtAutor.setText("");
        txtIsbn.setText("");
        txtEditorial.setText("");
        txtStock.setText("");
        tablaLibros.clearSelection();
        txtTitulo.requestFocus();
    }
}

