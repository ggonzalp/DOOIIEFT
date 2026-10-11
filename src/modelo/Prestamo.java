package modelo;

/**
 * Clase que representa el préstamo de un libro.
 */
public class Prestamo implements Runnable{

    private int idPrestamo;
    private Estudiante estudiante;
    private Libro libro;
    private String fechaPrestamo;
    private String fechaDevolucion;
    private boolean devuelto;

    /**
     * Constructor clase Prestamo.
     * @param idPrestamo Número de identificación de un préstamo.
     * @param estudiante Número de identificación del estudiante.
     * @param libro Número de identificación de un libro.
     * @param fechaPrestamo Fecha de préstamo del libro.
     * @param fechaDevolucion Fecha de devolución del libro.
     * @param devuelto Validación de la devolución del libro.
     */
    public Prestamo(int idPrestamo, Estudiante estudiante, Libro libro, String fechaPrestamo, String fechaDevolucion, boolean devuelto) {
        this.idPrestamo = idPrestamo;
        this.estudiante = estudiante;
        this.libro = libro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.devuelto = devuelto;
    }

    //Metodos getter
    public int getIdPrestamo() {
        return idPrestamo;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public Libro getLibro() {
        return libro;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    //Métodos setter.
    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public void setFechaPrestamo(String fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }

    @Override
    public void run() {

    }
}
