package conexion;

import controlador.Controlador;

public class PruebaDatos {
    public static void main(String[] args) {
        Controlador c = new Controlador();

        System.out.println("Categorías: " + c.obtenerCategorias().size());
        System.out.println("Libros: " + c.obtenerLibro().size());
        System.out.println("Estudiantes: " + c.obtenerEstudiante().size());
        System.out.println("Préstamos: " + c.obtenerPrestamos().size());
    }
}