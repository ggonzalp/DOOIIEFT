package app;

import controlador.Controlador;
import modelo.Estudiante;
import modelo.Prestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class VentanaReportes extends JFrame {
    private JPanel panelReportes;
    private JButton btnActivos;
    private JButton btnAtrasados;
    private JButton btnMasPrestados;
    private JTable tablaReportes;
    private JLabel lblReporte;
    private JButton btnHistorial;

    private final Controlador controlador;
    private DefaultTableModel tableModel;

    public VentanaReportes(Controlador controlador) {
        this.controlador = controlador;

        configurarVentana();
        configurarTabla();
        configurarBotones();
        mostrarActivos();
    }

    public void configurarVentana() {
        setContentPane(panelReportes);
        setTitle("Biblioteca Escolar");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void configurarTabla() {
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tablaReportes.setModel(tableModel);
    }

    public void configurarBotones() {
        btnActivos.addActionListener(e -> mostrarActivos());
        btnAtrasados.addActionListener(e -> mostrarAtrasados());
        btnMasPrestados.addActionListener(e -> mostrarMasPrestados());
        btnHistorial.addActionListener(e -> mostrarHistorial());
    }

    private void mostrarActivos() {
        mostrarReporte("Préstamos activos",
                new String[]{"Estudiante", "Curso", "Libro", "Fecha préstamo", "Fecha devolución"},
                controlador.reportePrestamosActivos());
    }

    private void mostrarAtrasados() {
        mostrarReporte("Préstamos atrasados",
                new String[]{"Estudiante", "Curso", "Libro", "Debía devolver", "Días de atraso"},
                controlador.reportePrestamosAtrasados());
    }

    private void mostrarHistorial() {
        String rut = JOptionPane.showInputDialog(this, "RUT del estudiante (ej: 12345678-9):");
        if (rut == null) {
            return; // Apretó Cancelar
        }

        Estudiante estudiante;
        try {
            estudiante = controlador.buscarEstudiantePorRut(rut);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "RUT inválido.");
            return;
        }

        if (estudiante == null) {
            JOptionPane.showMessageDialog(this, "No hay un estudiante registrado con ese RUT.");
            return;
        }

        List<Object[]> filas = new ArrayList<>();
        for (Prestamo prestamo : controlador.obtenerHistorial(estudiante)) {
            String estado;
            if (prestamo.isDevuelto()) {
                estado = "Devuelto";
            } else {
                estado = "Pendiente";
            }

            filas.add(new Object[]{
                    prestamo.getLibro().getTitulo(),
                    prestamo.getFechaPrestamo(),
                    prestamo.getFechaDevolucion(),
                    estado
            });
        }

        mostrarReporte("Historial de " + estudiante.getNombre(),
                new String[]{"Libro", "Fecha préstamo", "Fecha devolución", "Estado"},
                filas);
    }
    private void mostrarMasPrestados() {
        mostrarReporte("Libros más prestados",
                new String[]{"Libro", "Categoría", "Veces prestado"},
                controlador.reporteLibrosMasPrestador());
    }

    private void mostrarReporte(String titulo, String[] columnas, List<Object[]> filas) {
        tableModel.setColumnIdentifiers(columnas);
        tableModel.setRowCount(0);

        for(Object[] fila : filas) {
            tableModel.addRow(fila);
        }
        lblReporte.setText(titulo + ": " + filas.size() + " resultado(s)");
    }
}

