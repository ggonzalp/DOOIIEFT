package app;

import controlador.Controlador;
import modelo.Estudiante;
import modelo.Libro;
import modelo.Prestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Ventana con interfaz diseñada para el estudiante.
 */

public class VentanaEstudiante extends JFrame {

    private final Controlador controlador;

    private DefaultTableModel tableModel;

    private JButton btnPrestamo;
    private JButton btnDevolucion;
    private JButton btnConsulta;
    private JTable tablaEstudiante;
    private JPanel panelEstudiante;

    private Estudiante estudianteActual;
    private List<Libro> catalogo = new ArrayList<>();
    private List<Prestamo> historial = new ArrayList<>();
    private boolean mostrandoCatalogo;

    public VentanaEstudiante(Controlador controlador) {
        this.controlador = controlador;

        configurarVentana();
        configurarTabla();
        configurarBotones();
        cargarCatalogo();
    }

    private void configurarVentana() {
        setContentPane(panelEstudiante);
        setTitle("Portal Estudiante");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void configurarTabla() {
        String[] columnas = {"ID", "Título", "Autor", "ISBN", "Editorial", "Stock", "Categoría", "Estado"};

        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaEstudiante.setModel(tableModel);
        tablaEstudiante.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void configurarBotones() {
        btnPrestamo.addActionListener(e -> prestarLibro());
        btnDevolucion.addActionListener(e -> devolverLibro());
        btnConsulta.addActionListener(e -> mostrarHistorial());
    }

    /**
     * Pide el rut una sola vez y lo guarda.
     *
     * @return true si el estudiante fue identificado.
     */
    private boolean identificarEstudiante() {
        if (estudianteActual != null) {
            return true;
        }

        String rut = JOptionPane.showInputDialog(this, "Ingresa tu rut ej: 12345678-9 o K");
        if (rut == null) {
            return false; //Botón salir
        }

        try {
            estudianteActual = controlador.buscarEstudiantePorRut(rut);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Rut inválido.");
            return false;
        }

        if (estudianteActual == null) {
            JOptionPane.showMessageDialog(this, "No hay un estudiante registrado con ese RUT.");
            return false;
        }

        setTitle("Portal Estudiante - " + estudianteActual.getNombre());
        return true;
    }

    /**
     * Muestra el stock de un título.
     */
    private void cargarCatalogo() {
        catalogo = controlador.obtenerLibro();
        mostrandoCatalogo = true;

        tableModel.setColumnIdentifiers(new Object[]{"ID", "Título", "Autor", "ISBN", "Editorial", "Stock", "Categoría", "Estado"});
        tableModel.setRowCount(0);

        for (Libro libro : catalogo) {
            String estado;
            if (libro.getStock() > 0) {
                estado = "Disponible";
            } else {
                estado = "Sin stock";
            }

            tableModel.addRow(new Object[]{
                    libro.getIdLibro(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getIsbn(),
                    libro.getEditorial(),
                    libro.getStock(),
                    libro.getCategoria().getNombre(),
                    estado
            });
        }
    }

    /**
     * Muestra historial.
     */
    private void mostrarHistorial() {
        if (!identificarEstudiante()) {
            return;
        }

        historial = controlador.obtenerHistorial((estudianteActual));
        mostrandoCatalogo = false;

        tableModel.setColumnIdentifiers(new Object[]{"ID", "Libro", "Fecha préstamo", "Fecha devolución", "Estado"});
        for (Prestamo prestamo : historial) {
            String estado;
            if (prestamo.isDevuelto()) {
                estado = "Devuelto";
            } else {
                estado = "Pendiente";
            }

            tableModel.addRow(new Object[]{
                    prestamo.getIdPrestamo(),
                    prestamo.getLibro().getTitulo(),
                    prestamo.getFechaPrestamo(),
                    prestamo.getFechaDevolucion(),
                    estado
            });
        }

        if (historial.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todavía no tienes préstamos asociados");
        }
    }


    /**
     * Presta libro seleccionado en el catálogo.
     */
    private void prestarLibro() {
        if (!mostrandoCatalogo) {
            cargarCatalogo();
            JOptionPane.showMessageDialog(this, "Elige un libro del catálogo.");
            return;
        }

        int fila = tablaEstudiante.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Primero selecciona un libro del catálogo.");
            return;
        }

        if (!identificarEstudiante()) {
            return;
        }

        Libro libro = catalogo.get(fila);
        btnPrestamo.setEnabled(false);

        new Thread(() -> {
            boolean registrado = controlador.registrarPrestamo(estudianteActual, libro);

            SwingUtilities.invokeLater(() -> {
                btnPrestamo.setEnabled(true);
                cargarCatalogo();

                if (registrado) {
                    JOptionPane.showMessageDialog(this, "Préstamo registrado: " + libro.getTitulo());
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo registrar el préstamo. Revisa que haya stock.");
                }
            });
        }).start();
    }

        /**
         * Devuelve el préstamo seleccionado en el historial. El registro corre en un hilo aparte.
         */
        private void devolverLibro() {
            if (mostrandoCatalogo) {
                mostrarHistorial();
                if (!mostrandoCatalogo && !historial.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Elige el préstamo pendiente y presiona Devolución.");
                }
                return;
            }

            int fila = tablaEstudiante.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Primero selecciona un préstamo de la tabla.");
                return;
            }


        Prestamo prestamo = historial.get(fila);
        if (prestamo.isDevuelto()) {
            JOptionPane.showMessageDialog(this, "Ese libro ya fue devuelto.");
            return;
        }

        btnDevolucion.setEnabled(false);

        new Thread(() -> {
            boolean registrada = controlador.registrarDevolucion(prestamo);

            SwingUtilities.invokeLater(() -> {
                btnDevolucion.setEnabled(true);
                mostrarHistorial();

                if (registrada) {
                    JOptionPane.showMessageDialog(this, "Devolución registrada: " + prestamo.getLibro().getTitulo());
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo registrar la devolución.");
                }
            });
        }).start();
    }
}
