package conexion;

import controlador.Controlador;
import modelo.Categoria;
import modelo.Estudiante;
import modelo.Libro;
import modelo.Prestamo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Demuestra que synchronized impide prestar más ejemplares de los que hay:
 * 20 hilos piden al mismo tiempo un libro que tiene 5 ejemplares.
 */
public class PruebaConcurrencia {

    private static final int ISBN_PRUEBA = 999999;

    public static void main(String[] args) throws InterruptedException {
        Controlador controlador = new Controlador();

        // 1. Datos de prueba: un libro con 5 ejemplares y un estudiante que ya existe.
        Categoria categoria = controlador.obtenerCategorias().get(0);
        Estudiante estudiante = controlador.obtenerEstudiante().get(0);
        controlador.registrarLibro("Libro de prueba", "Prueba", ISBN_PRUEBA, "Prueba", 5, categoria);
        Libro libro = buscarLibro(controlador);

        // 2. 20 hilos piden el mismo libro al mismo tiempo.
        AtomicInteger aprobados = new AtomicInteger();
        AtomicInteger rechazados = new AtomicInteger();
        Thread[] hilos = new Thread[20];

        for (int i = 0; i < hilos.length; i++) {
            hilos[i] = new Thread(() -> {
                if (controlador.registrarPrestamo(estudiante, libro)) {
                    aprobados.incrementAndGet();
                } else {
                    rechazados.incrementAndGet();
                }
            });
        }

        for (Thread hilo : hilos) {
            hilo.start();
        }
        for (Thread hilo : hilos) {
            hilo.join(); // espera a que termine cada hilo
        }

        // 3. Resultado.
        System.out.println("Préstamos aprobados: " + aprobados.get());
        System.out.println("Préstamos rechazados: " + rechazados.get());
        System.out.println("Stock final: " + buscarLibro(controlador).getStock());

        // 4. Limpieza: borra los préstamos y el libro de prueba.
        for (Prestamo prestamo : controlador.obtenerPrestamos()) {
            if (prestamo.getLibro().getIdLibro() == libro.getIdLibro()) {
                controlador.eliminarPrestamo(prestamo.getIdPrestamo());
            }
        }
        controlador.eliminar(libro.getIdLibro());
    }

    /**
     * Busca el libro de prueba por su ISBN.
     */
    private static Libro buscarLibro(Controlador controlador) {
        for (Libro libro : controlador.obtenerLibro()) {
            if (libro.getIsbn() == ISBN_PRUEBA) {
                return libro;
            }
        }
        return null;
    }
}