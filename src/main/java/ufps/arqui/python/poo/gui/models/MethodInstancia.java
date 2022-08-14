package ufps.arqui.python.poo.gui.models;

/**
 * Clase para encapsula el metodo que contiene una clase instanciada.
 *
 * @author Omar Ramón Montes
 */
public class MethodInstancia {

    /**
     * Nombre del metodo.
     */
    private String name;

    /**
     * Listado de argumentos que requiere el método.
     */
    private String[] args;
    
    /**
     * Documentación del metodo
     */
    private String docs;
    
    /**
     * Clase propietaria del metodo.
     */
    private String owner;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String getDocs() {
        return docs;
    }

    public void setDocs(String docs) {
        this.docs = docs;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
