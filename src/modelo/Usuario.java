package modelo;

/**
 * Clase que representa a un usuario registrado.
 */

public class Usuario  implements Runnable {

    private int idUsuario;
    private String nombreUsuario;
    private Rut rut;
    private String correo;
    private String contrasena;
    private String rol;

    /**
     * Constructor clase Usuario.
     * @param idUsuario Número de identificación del usuario.
     * @param nombreUsuario Nombre del usuario.
     * @param rut Rol único nacional del usuario.
     * @param correo Correo electrónico del usuario.
     * @param contrasena Contraseña del usuario para ingresar al sistema.
     * @param rol Número de rol del usuario.
     */

    public Usuario(int idUsuario, String nombreUsuario, Rut rut, String correo, String contrasena, String rol) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.rut = rut;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    //Métodos getter.

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public Rut getRut() {
        return rut;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getRol() {
        return rol;
    }

    //Métodos setter.

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setRut(Rut rut) {
        this.rut = rut;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public void run() {

    }
}
