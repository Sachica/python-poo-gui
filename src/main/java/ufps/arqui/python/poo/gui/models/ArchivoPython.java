package ufps.arqui.python.poo.gui.models;

import ufps.arqui.python.poo.gui.exceptions.Exceptions;
import ufps.arqui.python.poo.gui.utils.AdministrarArchivo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Modelado de archivo de python.
 *
 * @author Omar Ramón Montes
 */
public class ArchivoPython extends Fichero{
    
    /**
     * Almacena cualquiera excepción producida durante el escaneo del proyecto
     * referente a este archivo
     */
    private ExcepcionCompilar excepcionCompilar;
    
    /**
     * Contenido del archivo
     */
    protected String contenido;
    
    /**
     * Representación de la ruta relativa de este archivo con formato de modulo python
     */
    protected String module;

    /**
     * Listado de clases que contiene el archivo.
     */
    private final List<String> classes = new ArrayList<>();
    
    private Map<String, ClasePython> allClass;

    public ArchivoPython() {
    }

    public ArchivoPython(File fichero) {
        super(fichero);
    }
    
    /**
     * Crear una clase en el atchivo actual.
     * 
     * @param nombre 
     */
    public void crearClase(String modulo, String nombre) throws Exceptions{
//        boolean existe = false;
//        for (int i = 0; i < this.clases.size(); i++) {
//            if (this.clases.get(i).getNombre().equals(nombre)) {
//                existe = true;
//                break;
//            }
//        }
//        if (!existe) {
//            ClasePython clasePython = new ClasePython();
//            clasePython.setNombre(nombre);
//            clasePython.setPathModule(modulo);
//            addClase(clasePython);
//                
//            String contenidoClase = "\nclass " + nombre + "(object):\n\tpass\n";
//                
//            AdministrarArchivo.escribirArchivo(super.getFichero(), contenidoClase, true);
//        }else{
//            throw new Exceptions("Ya existe una clase con el mismo nombre en el archivo");
//        }
    }
    
    /**
     * Lee el contenido del archivo y lo almacena en el atributo <code>contenido</code>
     * @throws Exceptions 
     */
    public void leerContenido() throws Exceptions{
        this.contenido = AdministrarArchivo.abrirArchivo(this.fichero);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.classes);
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
        
        if(!super.equals(obj)){
            return false;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ArchivoPython other = (ArchivoPython) obj;
        if (!Objects.equals(this.classes, other.classes)) {
            return false;
        }
        return true;
    }
    
    public void addClass(String clazz){
        this.classes.add(clazz);
    }
    
    public List<ClasePython> getClases() {
        List<ClasePython> res = new ArrayList<>();
        for(String key: this.classes){
            res.add(this.allClass.get(key));
        }
        return res;
    }

    public void setAllClass(Map<String, ClasePython> allClass) {
        this.allClass = allClass;
    }
    
    public String getContenido() throws Exceptions {
        this.contenido = AdministrarArchivo.abrirArchivo(super.fichero);
        return this.contenido;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public ExcepcionCompilar getExcepcionCompilar() {
        return excepcionCompilar;
    }

    public void setExcepcionCompilar(ExcepcionCompilar excepcionCompilar) {
        this.excepcionCompilar = excepcionCompilar;
    }
}
