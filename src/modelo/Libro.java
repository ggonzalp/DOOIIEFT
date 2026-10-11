package modelo;

/**
 * Representa un libro de la biblioteca
 */


public class Libro {

    private int idLibro;
    private String titulo;
    private String autor;
    private int isbn;
    private String editorial;
    private int stock;
    private Categoria categoria;

    /**
     * Contructor de la clase Libro
     * @param idLibro Número de identificación de un libro.
     * @param titulo Título de un libro.
     * @param autor Autor de un libro.
     * @param isbn Número de registro isbn de un libro.
     * @param editorial Editorial que publicó de un libro.
     * @param stock stock de un libro.
     * @param categoria Categoría a la que pertenece un libro.
     */

    public Libro (int idLibro, String titulo, String autor, int isbn, String editorial, int stock, Categoria categoria) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.editorial = editorial;
        this.stock = stock;
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return titulo + " (stock: " + stock + ")";
    }

    //Metodos getter.
    public int getIdLibro() {
        return idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getIsbn() {
        return isbn;
    }

    public String getEditorial() {
        return editorial;
    }

    public int getStock() {
        return stock;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    //Metodos setter.
    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

}
