package ufps.arqui.python.poo.gui.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelado de clase de python en java.
 *
 * @author Omar Ramón Montes
 */
public class ClasePython {
    
    protected String pathModule;
    
    /**
     * Nombre de la clase Python.
     */
    protected String nombre;

    /**
     * Listado de clases de las cuales hereda la clase actual.
     */
    protected List<ClasePython> herencia = new ArrayList<>();
    
    /**
     * Posición en la cual la clase SERA o FUE dibujada en la vista 
     */
    protected Posicion posicion = new Posicion();

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<ClasePython> getHerencia() {
        return herencia;
    }

    public void setHerencia(List<ClasePython> herencia) {
        this.herencia = herencia;
    }

    public void addHerencia(ClasePython clase) {
        this.herencia.add(clase);
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public String getPathModule() {
        return pathModule;
    }

    public void setPathModule(String pathModule) {
        this.pathModule = pathModule;
    }

    @Override
    public String toString() {
        return "ClasePython{" + "nombre=" + nombre + '}';
    }
    
}
