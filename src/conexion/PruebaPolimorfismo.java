package conexion;

import modelo.Estudiante;
import modelo.Persona;
import modelo.Rut;
import modelo.Usuario;

import java.util.List;

/**
 * Demostración de polimorfismo para el video:
 * el mismo llamado (getDescripcion) da un resultado distinto según el objeto real.
 */
public class PruebaPolimorfismo {
    public static void main(String[] args) {

        Persona estudiante = new Estudiante(1, "Ana Pérez", new Rut("12345678-9"), "3°A", "ana@colegio.cl");
        Persona usuario = new Usuario(1, "Luis Soto", new Rut("11111111-1"), "luis@biblioteca.cl", "1234", "bibliotecario");

        List<Persona> personas = List.of(estudiante, usuario);

        for (Persona persona : personas) {
            System.out.println(persona.getDescripcion());
        }
    }
}