package modelo;

/**
 * Clase que representa la categoría a la que pertenece un libro.
 */

public class Categoria {

    private int idCategoria;
    private String nombre;

    /**
     * Constructor clase Categoria.
     * @param idCategoria Número identificador de la categoría.
     * @param nombreCategoria Nombre de la categoría a la que pertenece un libro.
     */
    public Categoria(int idCategoria, String nombreCategoria) {
        this.idCategoria = idCategoria;
        this.nombre = nombreCategoria;
    }

    //Métodos getter.

    public String getNombre() {
        return nombre;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    //Métodos setter.

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
