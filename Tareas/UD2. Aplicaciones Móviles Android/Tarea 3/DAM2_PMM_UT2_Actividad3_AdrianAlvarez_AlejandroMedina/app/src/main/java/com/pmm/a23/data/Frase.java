/**
 * Frase
 *
 * Esta clase representa el modelo de datos de una frase.
 * Se utiliza para transportar la información entre la base de datos,
 * la lógica de la aplicación y la interfaz de usuario.
 *
 * Una Frase contiene principalmente:
 *  - Un texto
 *  - Un autor
 *  - Un título (en este caso se usa como fecha)
 *  - Un identificador único (id)
 */
package com.pmm.a23.data;

public class Frase {

    // ==============================
    // ATRIBUTOS
    // ==============================

    // Atributos usados en pruebas o parseos antiguos de JSON
    private String autorDesdeJson;
    private String substring;

    // Identificador de la frase en la base de datos
    private int id;

    // Título de la frase (se usa como fecha)
    private String titulo;

    // Autor de la frase
    private String autor;

    // Texto principal de la frase
    private String texto;

    // ==============================
    // CONSTRUCTORES
    // ==============================

    /**
     * Constructor vacío.
     * Necesario para crear objetos Frase sin datos iniciales.
     */
    public Frase() {}

    /**
     * Constructor completo.
     * Se suele usar al recuperar datos desde la base de datos.
     *
     * @param texto texto de la frase
     * @param autor autor de la frase
     * @param titulo título o fecha
     * @param id identificador único
     */
    public Frase(String texto, String autor, String titulo, int id) {
        this.texto = texto;
        this.autor = autor;
        this.titulo = titulo;
        this.id = id;
    }

    /**
     * Constructor usado para pruebas con datos obtenidos de JSON.
     * Actualmente no se usa en la lógica principal.
     *
     * @param substring texto parcial de la frase
     * @param autorDesdeJson autor obtenido del JSON
     */
    public Frase(String substring, String autorDesdeJson) {
        this.substring = substring;
        this.autorDesdeJson = autorDesdeJson;
    }

    // ==============================
    // GETTERS Y SETTERS
    // ==============================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    // ==============================
    // MÉTODOS AUXILIARES
    // ==============================

    /**
     * Devuelve una representación en texto del objeto Frase.
     * Útil para depuración y logs.
     */
    @Override
    public String toString() {
        return "Frase{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", texto='" + texto + '\'' +
                '}';
    }
}
