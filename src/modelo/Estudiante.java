package modelo;

/**
 * Clase que representa un estudiante.
 */

public class Estudiante {

    private int idEstudiante;
    private String nombre;
    private Rut rut;
    private String curso;
    private String correo;

    /**
     * Constructor clase Estudiante
     * @param idEstudiante Número de identificación.
     * @param nombre Nombre del estudiante.
     * @param rut Rol único nacional del estudiante.
     * @param curso Curso o nivel en el que se encuentra el estudiante.
     * @param correo Correo electrónico del estudiante.
     */
    public Estudiante (int idEstudiante, String nombre, Rut rut, String curso, String correo) {

        this.idEstudiante = idEstudiante;
        this.nombre = nombre;
        this.rut = rut;
        this.curso = curso;
        this.correo = correo;
    }

    //Metodos getter
    public String getCorreo() {
        return correo;
    }

    public String getCurso() {
        return curso;
    }

    public Rut getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    //Métodos setter

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
