package app;

import controlador.Controlador;
import modelo.Estudiante;
import modelo.Libro;
import modelo.Prestamo;
import util.Visualizar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class VentanaPrestamos extends JFrame implements Visualizar {
    private JButton btnPrestar;
    private JButton btnDevolver;
    private JButton btnVisualizar;
    private JTable tablaLibros;
    private JComboBox<Estudiante> comboEstudiante;
    private JPanel panelPrestamos;
    private JComboBox<Libro> comboLibros;

    private final Controlador controlador;
    private DefaultTableModel modeloPrestamos;
    private List<Prestamo> pendientes = new ArrayList<>();

    public VentanaPrestamos(Controlador controlador) {
        this.controlador = controlador;

        configurarVentana();
        configurarTabla();
        cargarEstudiantes();
        cargarLibros();
        configurarBotones();
        cargarPrestamos();
    }

    private void configurarVentana() {
        setContentPane(panelPrestamos);
        setTitle("Préstamos y Devoluciones");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void configurarTabla() {
        modeloPrestamos = new DefaultTableModel(new Object[]{"ID", "Libro", "Fecha préstamo", "Vence"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tablaLibros.setModel(modeloPrestamos);
        tablaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void configurarBotones() {
        //Al cambiar de estudiante, se cargan sus préstamos pendientes.
        comboEstudiante.addActionListener(e -> cargarPrestamos());
        btnPrestar.addActionListener(e -> prestar());
        btnDevolver.addActionListener(e -> devolver());
        btnVisualizar.addActionListener(e -> visualizar());
    }

    private void cargarEstudiantes() {
        comboEstudiante.removeAllItems();
        for (Estudiante estudiante : controlador.obtenerEstudiante()) {
            comboEstudiante.addItem(estudiante);
        }
    }


    private void cargarLibros() {
        int elegido = comboLibros.getSelectedIndex();

        comboLibros.removeAllItems();
        for (Libro libro : controlador.obtenerLibro()) {
            comboLibros.addItem(libro);
        }

        if (elegido >= 0 && elegido < comboLibros.getItemCount()) {
            comboLibros.setSelectedIndex(elegido);
        }
    }

    private void cargarPrestamos() {
        pendientes = new ArrayList<>();
        modeloPrestamos.setRowCount(0);

        Estudiante estudiante = (Estudiante) comboEstudiante.getSelectedItem();
        if (estudiante == null) {
            return;
        }

        for (Prestamo prestamo : controlador.obtenerHistorial(estudiante)) {
            if (!prestamo.isDevuelto()) {
                pendientes.add(prestamo);
                modeloPrestamos.addRow(new Object[]{
                        prestamo.getIdPrestamo(),
                        prestamo.getLibro().getTitulo(),
                        prestamo.getFechaPrestamo(),
                        prestamo.getFechaDevolucion()
                });
            }
        }
    }


    private void prestar() {
        Estudiante estudiante = (Estudiante) comboEstudiante.getSelectedItem();
        Libro libro = (Libro) comboLibros.getSelectedItem();

        if (estudiante == null || libro == null) {
            JOptionPane.showMessageDialog(this, "Elija un estudiante y un libro.");
            return;
        }

        btnPrestar.setEnabled(false);

        new Thread(() -> {
            boolean registrado = controlador.registrarPrestamo(estudiante, libro);

            SwingUtilities.invokeLater(() -> {
                btnPrestar.setEnabled(true);
                cargarLibros();
                cargarPrestamos();

                if (registrado) {
                    JOptionPane.showMessageDialog(this, "Préstamo registrado: " + libro.getTitulo() + " para " + estudiante.getNombre());
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo registrar el préstamo. Revise que haya stock.");
                }
            });
        }).start();
    }


    private void devolver() {
        int fila = tablaLibros.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione en la tabla el préstamo que se devuelve.");
            return;
        }

        Prestamo prestamo = pendientes.get(fila);
        btnDevolver.setEnabled(false);

        new Thread(() -> {
            boolean registrada = controlador.registrarDevolucion(prestamo);

            SwingUtilities.invokeLater(() -> {
                btnDevolver.setEnabled(true);
                cargarLibros();
                cargarPrestamos();

                if (registrada) {
                    JOptionPane.showMessageDialog(this, "Devolución registrada: " + prestamo.getLibro().getTitulo());
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo registrar la devolución.");
                }
            });
        }).start();
    }


    @Override
    public void visualizar() {
        cargarLibros();
        cargarPrestamos();
    }
}