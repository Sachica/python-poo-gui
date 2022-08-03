package ufps.arqui.python.poo.gui.models;

import ufps.arqui.python.poo.gui.exceptions.Exceptions;
import ufps.arqui.python.poo.gui.utils.AdministrarArchivo;

import java.util.HashSet;
import java.util.Observable;
import java.util.Set;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Clase del modelo encargada de gestionar los archivos abiertos en el editor de
 * texto por el usuario, y realizar las correspondientes acciones sobre los mismos
 * archivos
 * @author Sachikia
 */
public class Editor extends Observable{
    private final Set<ArchivoPython> archivosAbiertos = new HashSet<>();
    private SimpleObjectProperty<ArchivoPython> ultimoArchivoAbierto = new SimpleObjectProperty<>();

    public Editor() {
    }
    
    /**
     * Abre un <code>ArchivoPython</code>
     * Si actualmente ya estaba abierto, no lo vuelve a leer
     * @param archivoPython
     * @throws Exceptions 
     */
    public void abrirArchivo(ArchivoPython archivoPython) throws Exceptions{
        archivoPython.leerContenido();
        this.setUltimoArchivoAbierto(archivoPython);
        this.archivosAbiertos.add(this.ultimoArchivoAbierto.get());
    }
    
    /**
     * Verifica si el archivo python pasado como parametro se encuentra abierto
     * @param archivoPython
     * @return 
     */
    public boolean estaAbierto(ArchivoPython archivoPython){
        return this.archivosAbiertos.contains(archivoPython);
    }

    public Set<ArchivoPython> getArchivosAbiertos() {
        return archivosAbiertos;
    }

    public void setUltimoArchivoAbierto(ArchivoPython ultimoArchivoAbierto) {
        this.ultimoArchivoAbierto.set(ultimoArchivoAbierto);
    }
    
    public ArchivoPython getUltimoArchivoAbierto() {
        return this.ultimoArchivoAbierto.get();
    }
    
    public SimpleObjectProperty<ArchivoPython> getUltimoArchivoAbiertoProperty() {
        return this.ultimoArchivoAbierto;
    }
    
    /**
     * Cierra el archivo python
     * @param archivoPython 
     */
    public void cerrarArchivo(ArchivoPython archivoPython) {
        this.archivosAbiertos.remove(archivoPython);
        if(this.ultimoArchivoAbierto != null && this.ultimoArchivoAbierto.equals(archivoPython)){
            this.ultimoArchivoAbierto = null;
        }
    }

    public void guardarArchivo(ArchivoPython archivoPython, String contenido) throws Exceptions {
        AdministrarArchivo.escribirArchivo(archivoPython.getFichero(), contenido, false);
        this.setUltimoArchivoAbierto(archivoPython);
    }
    
    public void crearClase(ArchivoPython archivoPython, String modulo, String nombre) throws Exceptions{
        archivoPython.crearClase(modulo, nombre);
        this.setUltimoArchivoAbierto(archivoPython);
    }
}
