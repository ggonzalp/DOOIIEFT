package modelo;

/**
 * Clase que representa un estudiante.
 */

public class Estudiante extends Persona {

    private int idEstudiante;
    private String curso;

    /**
     * Constructor clase Estudiante
     * @param idEstudiante Número de identificación.
     * @param curso        Curso o nivel en el que se encuentra el estudiante.
     */
    public Estudiante(int idEstudiante, String nombre, Rut rut, String curso, String correo) {
        super(nombre, rut, correo);
        this.idEstudiante = idEstudiante;
        this.curso = curso;
    }

    @Override
    public String getDescripcion() {
        return "Estudiante " + getNombre() + " (" + curso + ")";
    }

    //Metodos getter
    public String getCurso() {
        return curso;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    //Métodos setter
    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}
