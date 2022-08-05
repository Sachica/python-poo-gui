package ufps.arqui.python.poo.gui.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import ufps.arqui.python.poo.gui.adapters.ArchivoPythonAdapter;
import ufps.arqui.python.poo.gui.adapters.ClasePythonAdapter;
import ufps.arqui.python.poo.gui.adapters.DirectorioAdapter;
import ufps.arqui.python.poo.gui.models.ArchivoPython;
import ufps.arqui.python.poo.gui.models.ClasePython;
import ufps.arqui.python.poo.gui.models.Directorio;

/**
 *
 * @author Sachikia
 */
public class ScannerProject {
    private final ObservableMap<String, ClasePython> classes = FXCollections.observableMap(new HashMap<>());
    private final ObservableMap<String, ArchivoPython> files = FXCollections.observableMap(new HashMap<>());
    private final ObservableMap<String, Directorio> directorys = FXCollections.observableMap(new HashMap<>());
    private final Gson gson;
    
    public ScannerProject() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Directorio.class, new DirectorioAdapter().nullSafe());
        builder.registerTypeAdapter(ArchivoPython.class, new ArchivoPythonAdapter().nullSafe());
        builder.registerTypeAdapter(ClasePython.class, new ClasePythonAdapter().nullSafe());
        
        this.gson = builder.create();
    }
    
    public void load(String res){
        ResultJSON resJson = this.gson.fromJson(res, ResultJSON.class);
        
        this.directorys.keySet().retainAll(resJson.directorys.keySet());
        this.files.keySet().retainAll(resJson.files.keySet());
        this.classes.keySet().retainAll(resJson.classes.keySet());
        
        //Acutaliza los maps
        this.updateMaps(this.classes, resJson.classes);
        this.updateMaps(this.files, resJson.files);
        this.updateMaps(this.directorys, resJson.directorys);
    }
    
    private void updateMaps(Map currentClass, Map entryClasses){
        for(Object keyOldClasses: entryClasses.keySet()){
            Object c1 = currentClass.get(keyOldClasses);
            Object c2 = entryClasses.get(keyOldClasses);
            
            //Es un nuevo objeto o existe diferencia entre el objeto actual y el que llega?
            if(c1 == null) currentClass.put(keyOldClasses, c2);
            else if(!c1.equals(c2)) currentClass.replace(keyOldClasses, c1, c2);
            
            c1 = c2;
            if(c1 instanceof ClasePython){
                ((ClasePython)c1).setAllClass(this.classes);
            }else if(c1 instanceof ArchivoPython){
                ((ArchivoPython)c1).setAllClass(this.classes);
            }else{
                ((Directorio)c1).setAllFiles(this.files);
                ((Directorio)c1).setAllDirectorys(this.directorys);
            }
        }
    }

    public ObservableMap<String, ClasePython> getClasses() {
        return classes;
    }

    public ObservableMap<String, ArchivoPython> getFiles() {
        return files;
    }

    public ObservableMap<String, Directorio> getDirectorys() {
        return directorys;
    }
    
    public Directorio getDirectorioTrabajo(File root){
        String directorioTrabajo = root.getAbsolutePath() + File.separator + "src";
        return this.directorys.get(directorioTrabajo);
    }
    
    
    private class ResultJSON{
        Map<String, ClasePython> classes;
        Map<String, ArchivoPython> files;
        Map<String, Directorio> directorys;
    }
}
