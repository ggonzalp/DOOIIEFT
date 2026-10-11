package modelo;

/**
 * Clase que representa a un usuario registrado.
 */

public class Usuario extends Persona{

    private int idUsuario;
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
        super(nombreUsuario, rut, correo);
        this.idUsuario = idUsuario;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    @Override
    public String getDescripcion() {
        return "Usuario " + getNombre() + " [" + rol + "]";
    }

    //Métodos getter.

    public int getIdUsuario() {
        return idUsuario;
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

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

}
