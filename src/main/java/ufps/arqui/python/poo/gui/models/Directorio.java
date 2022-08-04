package ufps.arqui.python.poo.gui.models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import ufps.arqui.python.poo.gui.models.ArchivoPython;

/**
 * Clase para representar el paquete de los ficheros de python.
 *
 * @author Omar Ramón Montes
 */
public class Directorio extends Fichero{

    /**
     * Listado de directorios dentro del directorio actual.
     */
    private List<String> directorys;

    /**
     * Listado de archivos python dentro del directorio actual.
     */
    private List<String> files;
    
    private Map<String, ArchivoPython> allFiles;
    
    private Map<String, Directorio> allDirectorys;
    
    public Directorio() {
    }

    public Directorio(File fichero) {
        super(fichero);
    }
    
    public boolean isMyChild(String filename){
        for(String file: this.files){
            if(filename.equals(file)) return true;
        }
        
        for(String file: this.directorys){
            if(filename.equals(file)) return true;
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.directorys);
        hash = 97 * hash + Objects.hashCode(this.files);
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
        final Directorio other = (Directorio) obj;
        if (!Objects.equals(this.directorys, other.directorys)) {
            return false;
        }
        if (!Objects.equals(this.files, other.files)) {
            return false;
        }
        return true;
    }
    
    public List<Directorio> getDirectorios() {
        List<Directorio> res = new ArrayList<>();
        for(String key: this.directorys){
            res.add(this.allDirectorys.get(key));
        }
        return res;
    }

    public List<ArchivoPython> getArchivos() {
        List<ArchivoPython> res = new ArrayList<>();
        for(String key: this.files){
            res.add(this.allFiles.get(key));
        }
        return res;
    }

    public void setAllFiles(Map<String, ArchivoPython> allFiles) {
        this.allFiles = allFiles;
    }

    public void setAllDirectorys(Map<String, Directorio> allDirectorys) {
        this.allDirectorys = allDirectorys;
    }
}
