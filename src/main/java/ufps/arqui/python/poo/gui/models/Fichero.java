package ufps.arqui.python.poo.gui.models;

import java.io.File;
import java.util.Objects;

/**
 *
 * @author Sachikia
 */
public class Fichero {
    /**
     * Representación de un directorio en String. Es necesario para parsear el
     * json
     */
    protected String ficheroStr;

    /**
     * Representación de un directorio.
     */
    protected File fichero;

    public Fichero() {
    }

    public Fichero(File fichero) {
        this.fichero = fichero;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.ficheroStr);
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
        final Fichero other = (Fichero) obj;
        if (!Objects.equals(this.ficheroStr, other.ficheroStr)) {
            return false;
        }
        return true;
    }
    
    public File getFichero() {
        if (this.fichero == null && this.ficheroStr != null) {
            this.fichero = new File(this.ficheroStr);
        }
        return this.fichero;
    }

    public void setFichero(File fichero) {
        this.fichero = fichero;
    }
    
    public boolean isDirectory(){
        return this.getFichero().isDirectory();
    }
    
    public boolean isFile(){
        return this.getFichero().isFile();
    }

    @Override
    public String toString() {
        return this.getFichero().getName();
    }
}
