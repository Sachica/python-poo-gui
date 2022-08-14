package ufps.arqui.python.poo.gui.models;

import java.util.Map;

/**
 * Clase que encapsula una instancia de una clase creada por el usuario.
 *
 * @author Omar Ramón Montes
 */
public class MundoInstancia {
    /**
     * Id de referencia a la instancia.
     */
    private String id;
    
    /**
     * Nombre de la instancia.
     */
    private String name;

    /**
     * Nombre de la clase a la que pertenece la instalcia.
     */
    private String name_class;

    /**
     * Listado de atributos que contiene la instancia.
     */
    private Map<String, AttrInstancia[]> attrs;

    /**
     * Listado de metodos que contiene la instancia.
     */
    private Map<String, MethodInstancia[]> methods;
    
    /**
     * Valor puro de la instancia, puede ser un valor primitivo o coleccion
     */
    private String rawValue;
    
    /**
     * Indica si la instancia esta declarada
     */
    private boolean isDeclared;
    
    /**
     * Indica si la instancia es una colleción de python
     */
    private boolean isCollection;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_class() {
        return name_class;
    }

    public void setName_class(String name_class) {
        this.name_class = name_class;
    }

    public Map<String, AttrInstancia[]> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, AttrInstancia[]> attrs) {
        this.attrs = attrs;
    }

    public Map<String, MethodInstancia[]> getMethods() {
        return methods;
    }

    public void setMethods(Map<String, MethodInstancia[]> methods) {
        this.methods = methods;
    }

    public String getRawValue() {
        return rawValue;
    }

    public void setRawValue(String rawValue) {
        this.rawValue = rawValue;
    }

    public Boolean getIsDeclared() {
        return isDeclared;
    }

    public void setIsDeclared(Boolean isDeclared) {
        this.isDeclared = isDeclared;
    }

    public Boolean getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(Boolean isCollection) {
        this.isCollection = isCollection;
    }

    @Override
    public String toString() {
        return "MundoInstancia{" + "id=" + id + ", name=" + name + ", name_class=" + name_class + ", attrs=" + attrs + ", methods=" + methods + ", rawValue=" + rawValue + ", isDeclared=" + isDeclared + ", isCollection=" + isCollection + '}';
    }
}
