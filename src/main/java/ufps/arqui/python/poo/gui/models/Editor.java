package ufps.arqui.python.poo.gui.models;

import ufps.arqui.python.poo.gui.exceptions.Exceptions;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;

/**
 * Clase del modelo encargada de gestionar los archivos abiertos en el editor de
 * texto por el usuario, y realizar las correspondientes acciones sobre los mismos
 * archivos
 * @author Sachikia
 */
public class Editor{
    private final ObservableList<ArchivoPython> archivosAbiertos = FXCollections.observableArrayList();

    public Editor(Proyecto proyecto) {
        proyecto.getScan().getFiles().addListener(new MapChangeListener<String, ArchivoPython>() {
            @Override
            public void onChanged(MapChangeListener.Change<? extends String, ? extends ArchivoPython> e) {
                if(e.wasRemoved()){
                    archivosAbiertos.remove(e.getValueRemoved());
                }
            }
        });
    }
    
    /**
     * Abre un <code>ArchivoPython</code>
     * Si actualmente ya estaba abierto, no lo vuelve a leer
     * @param archivoPython
     * @throws Exceptions 
     */
    public void abrirArchivo(ArchivoPython archivoPython) throws Exceptions{
        if(!this.archivosAbiertos.contains(archivoPython)){
            this.archivosAbiertos.add(archivoPython);
        }else{
            //Ya estaba abierto por lo tanto 
            this.archivosAbiertos.setAll(archivoPython);
        }
    }
    
    /**
     * Cierra el archivo python
     * @param archivoPython 
     */
    public void cerrarArchivo(ArchivoPython archivoPython) {
        this.archivosAbiertos.remove(archivoPython);
    }

    public ObservableList<ArchivoPython> getArchivosAbiertos() {
        return archivosAbiertos;
    }
}
