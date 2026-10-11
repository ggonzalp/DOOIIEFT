package modelo;

public abstract class Persona {

    private String nombre;
    private Rut rut;
    private String correo;

    protected Persona(String nombre, Rut rut, String correo) {
        this.nombre = nombre;
        this.rut = rut;
        this.correo = correo;
    }

    public abstract String getDescripcion();

    //Métodos getter.
    public String getCorreo() {
        return correo;
    }

    public Rut getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    //Métodos setter.
    //El rut es un dato sensible que no debería ser modificado.

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
