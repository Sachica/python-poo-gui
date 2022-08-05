package ufps.arqui.python.poo.gui.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Modelado de clase de python en java.
 *
 * @author Omar Ramón Montes
 */
public class ClasePython {
    
    /**
     * Modulo al cual pertenece esta clase
     */
    private String pathModule;
    
    /**
     * Nombre de la clase Python.
     */
    private String name;

    /**
     * Listado de clases de las cuales hereda la clase actual.
     */
    private final List<String> bases = new ArrayList<>();
    
    private Map<String, ClasePython> allClass;
    
    /**
     * Posición en la cual la clase SERA o FUE dibujada en la vista 
     */
    protected Posicion posicion = new Posicion();

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.pathModule);
        hash = 83 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ClasePython other = (ClasePython) obj;
        if (!Objects.equals(this.pathModule, other.pathModule)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.bases, other.bases)) {
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ClasePython> getBases() {
        List<ClasePython> classes = new ArrayList<>();
        for(String key: this.bases){
            classes.add(this.allClass.get(key));
        }
        return classes;
    }
    
    public void addBase(String base){
        this.bases.add(base);
    }

    public void setAllClass(Map<String, ClasePython> allClass) {
        this.allClass = allClass;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public String getPathModule() {
        return pathModule;
    }

    public void setPathModule(String pathModule) {
        this.pathModule = pathModule;
    }
    
    @Override
    public String toString() {
        return "ClasePython{" + "nombre=" + name + '}';
    }

}
