package ufps.arqui.python.poo.gui.models;

import java.io.File;

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
