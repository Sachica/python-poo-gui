package ufps.arqui.python.poo.gui.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import ufps.arqui.python.poo.gui.models.ArchivoPython;
import ufps.arqui.python.poo.gui.models.ClasePython;
import ufps.arqui.python.poo.gui.models.Directorio;
import ufps.arqui.python.poo.gui.models.Posicion;

/**
 *
 * @author Sachikia
 */
public class ScannerProject {
    private class DirectorioJSON extends Directorio{
        private List<ArchivoJSON> archivosJSON = new ArrayList<>();
        private List<DirectorioJSON> directoriosJSON = new ArrayList<>();

        public List<ArchivoJSON> getArchivosJSON() {
            return archivosJSON;
        }

        public List<DirectorioJSON> getDirectoriosJSON() {
            return directoriosJSON;
        }
        
        public void loadInSuperClass(){
            this.initAfterJSONParse();
            super.archivos = new ArrayList<>(this.archivosJSON);
            super.directorios = new ArrayList<>(this.directoriosJSON);
        }
        
        public void initAfterJSONParse(){
            super.directorios = new ArrayList<>();
            super.archivos = new ArrayList<>();
        }
    }
    
    private class ArchivoJSON extends ArchivoPython{
        private List<Integer> clasesJSON = new ArrayList<>();

        public List<Integer> getClasesJson() {
            return this.clasesJSON;
        }
        
        public void convertToObject(Map<Integer, ClasePythonJSON> classesObj){
            this.initAfterJSONParse();
            for(Integer id: this.clasesJSON){
                super.clases.add(classesObj.get(id));
            }
        }
        
        public void initAfterJSONParse(){
            super.clases = new ArrayList<>();
        }
    }
    
    private class ClasePythonJSON extends ClasePython{
        private Integer id;
        private List<Integer> herenciaJSON = new ArrayList<>();

        public List<Integer> getClassBasesIds() {
            return this.herenciaJSON;
        }
        
        public void convertToObject(Map<Integer, ClasePythonJSON> classesObj){
            this.initAfterJSONParse();
            for(Integer id: this.herenciaJSON){
                super.herencia.add(classesObj.get(id));
            }
        }
        
        public void initAfterJSONParse(){
            super.herencia = new ArrayList<>();
            super.posicion = new Posicion();
        }
    }
    
    private DirectorioJSON directory;
    
    private HashMap<Integer, ClasePythonJSON> classes = new HashMap<>();
    
    public Directorio getRelationalData(){
        for(ClasePythonJSON clasePythonJSON: this.classes.values()){
            clasePythonJSON.convertToObject(this.classes);
        }
        
        Stack<DirectorioJSON> stack = new Stack<>();
        stack.push(this.directory);
        while(!stack.isEmpty()){
            DirectorioJSON directorio = stack.pop();
            directorio.loadInSuperClass();
            for(ArchivoJSON archivoJSON: directorio.getArchivosJSON()){
                archivoJSON.convertToObject(this.classes);
            }
            for(DirectorioJSON directorioJSON: directorio.getDirectoriosJSON()){
                stack.push(directorioJSON);
            }
        }
        return this.directory;
    }
}
