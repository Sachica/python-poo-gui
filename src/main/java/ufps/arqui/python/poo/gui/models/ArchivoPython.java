package ufps.arqui.python.poo.gui.models;

import ufps.arqui.python.poo.gui.exceptions.Exceptions;
import ufps.arqui.python.poo.gui.utils.AdministrarArchivo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Modelado de archivo de python.
 *
 * @author Omar Ramón Montes
 */
public class ArchivoPython {
    
    private ExcepcionCompilar excepcionCompilar;
    
    /**
     * Representación del archivo python en String. Es necesario para parsear el
     * json
     */
    private String archivoStr;

    /**
     * Representación del archivo python.
     */
    private File archivo;
    
    /**
     * Contenido del archivo
     */
    private final StringBuilder contenido = new StringBuilder();

    /**
     * Listado de clases que contiene el archivo.
     */
    private List<ClasePython> clases = new ArrayList<>();

    /**
     * Crear una clase en el atchivo actual.
     * 
     * @param nombre 
     */
    public void crearClase(String modulo, String nombre) throws Exceptions{
        boolean existe = false;
        for (int i = 0; i < this.clases.size(); i++) {
            if (this.clases.get(i).getNombre().equals(nombre)) {
                existe = true;
                break;
            }
        }
        if (!existe) {
            ClasePython clasePython = new ClasePython();
            clasePython.setNombre(nombre);
            clasePython.setPathModule(modulo);
            addClase(clasePython);
                
            String contenidoClase = "\nclass " + nombre + "(object):\n\tpass\n";
                
            AdministrarArchivo.escribirArchivo(this.getArchivo(), contenidoClase, true);
        }else{
            throw new Exceptions("Ya existe una clase con el mismo nombre en el archivo");
        }
    }
    
    /**
     * Lee el contenido del archivo y lo almacena en el atributo <code>contenido</code>
     * @throws Exceptions 
     */
    public void leerContenido() throws Exceptions{
        this.contenido.setLength(0);
        AdministrarArchivo.abrirArchivo(this.archivo, this.contenido);
    }
    
    @Override
    public boolean equals(Object o){
        if(o == null || !(o instanceof ArchivoPython)){
            return false;
        }
        
        ArchivoPython other = (ArchivoPython)o;
        return this.archivo.getAbsolutePath().equals(other.getArchivo().getAbsolutePath());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.archivo);
        return hash;
    }
    
    public File getArchivo() {
        if (this.archivo == null && this.archivoStr != null) {
            this.archivo = new File(this.archivoStr);
        }
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    public List<ClasePython> getClases() {
        return clases;
    }

    public void setClases(List<ClasePython> clases) {
        this.clases = clases;
    }

    public void addClase(ClasePython clase) {
        this.clases.add(clase);
    }

    public String getContenido() throws Exceptions {
        this.contenido.setLength(0);
        AdministrarArchivo.abrirArchivo(this.archivo, this.contenido);
        return this.contenido.toString();
    }

    public ExcepcionCompilar getExcepcionCompilar() {
        return excepcionCompilar;
    }

    public void setExcepcionCompilar(ExcepcionCompilar excepcionCompilar) {
        this.excepcionCompilar = excepcionCompilar;
    }
}
