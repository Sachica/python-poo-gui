package ufps.arqui.python.poo.gui.models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para representar el paquete de los ficheros de python.
 *
 * @author Omar Ramón Montes
 */
public class Directorio extends Fichero{

    /**
     * Listado de directorios dentro del directorio actual.
     */
    private List<Directorio> directorios = new ArrayList<>();

    /**
     * Listado de archivos python dentro del directorio actual.
     */
    private List<ArchivoPython> archivos = new ArrayList<>();

    public Directorio() {
    }

    public Directorio(File fichero) {
        super(fichero);
    }

    /**
     * Crea un archivo .py tomando como ruta el directorio en el que se
     * encuentra.
     *
     * @param nombreArchivo
     */
    public void crearArchivo(String nombreArchivo) {
        File file = new File(super.fichero.getAbsolutePath() + "/" + nombreArchivo + ".py");
        try {
            if (!file.exists()) {
                file.createNewFile();
                ArchivoPython ap = new ArchivoPython();
                ap.setFichero(file);
                addArchivo(ap);
            } else {
                //..
            }
        } catch (Exception e) {
            //..
        }
    }

    public List<Directorio> getDirectorios() {
        return directorios;
    }

    public void setDirectorios(List<Directorio> directorios) {
        this.directorios = directorios;
    }

    public void addDirectorio(Directorio directorio) {
        this.directorios.add(directorio);
    }
    
    public ArchivoPython getArchivoPorNombre(String nombre){
        return this.getArchivos().stream()
                .filter(archivo -> nombre.equals(archivo.getFichero().getName().split("\\.")[0]))
                .findAny()
                .orElse(null);
    }

    public List<ArchivoPython> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<ArchivoPython> archivos) {
        this.archivos = archivos;
    }

    public void addArchivo(ArchivoPython archivo) {
        this.archivos.add(archivo);
    }
    
    /**
     * Busca un <code>ArchivoPython</code> que corresponda con la ruta absoluta
     * @param absolutePath Ruta absoluta del archivo a buscar
     * @return <code>ArchivoPython</code>
     */
    public ArchivoPython getArchivo(String absolutePath){
        ArchivoPython res = null;
        for(ArchivoPython archivoPython: this.archivos){
            if(archivoPython.getFichero().getAbsolutePath().equals(absolutePath)){
                res = archivoPython;
                break;
            }
        }
        
        if(res == null){
            for(Directorio subDirectorio: this.directorios){
                res = subDirectorio.getArchivo(absolutePath);
                if (res != null) break;
            }
        }
        
        return res;
    }
}
