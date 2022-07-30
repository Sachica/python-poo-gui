package ufps.arqui.python.poo.gui.models;

import com.google.gson.Gson;
import ufps.arqui.python.poo.gui.exceptions.Exceptions;
import ufps.arqui.python.poo.gui.utils.AdministrarArchivo;
import ufps.arqui.python.poo.gui.utils.ConfScanFile;
import ufps.arqui.python.poo.gui.utils.TerminalInteractiva;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ufps.arqui.python.poo.gui.utils.ScannerProject;

/**
 * Modelo para la gestión del proyecto del usuario.
 * <p>
 * Aquí se almacenará la información del proyecto, para que los demás
 * componentes puedan interacturar con el proyecto, como por ejemplo lista de
 * clases con sus metodos y parametros; datos del proyecto; datos del mundo como
 * los objetos.
 *
 * @author Omar Ramón Montes
 */
public class Proyecto extends Observable implements Observer {

    /**
     * Nombre del proyecto.
     */
    private String nombre;

    /**
     * Comando para inicializar python.
     */
    private String comandoPython;

    /**
     * Archivo de propiedades del proyecto.
     */
    private Properties fileProperties = new Properties();

    /**
     * Ubicación del directorio donde se ejecutará la aplicación.
     */
    private File directorioRaiz;

    /**
     * Directtorio de trabajo, donde el usuario tendra su proyecto.
     * <p>
     * El directorio debe llamarse src, y debe estár dentro del directorio raiz.
     */
    private final ObjectProperty<Directorio> directorioTrabajo = new SimpleObjectProperty<>();
    
    private final ObjectProperty<List<ClasePython>> currentListClasses = new SimpleObjectProperty<>();
            
    private final ObjectProperty<MundoInstancia[]> currentInstances = new SimpleObjectProperty<>();

    /**
     * Editor de texto, encargado de administrar las operaciones sobre los archivos <br>
     * del proyecto
     */
    private Editor editor;

    /**
     * Instancia de la terminal interactiva.
     */
    private TerminalInteractiva terminalInteractiva;

    public Proyecto() {
    }

    public Proyecto(TerminalInteractiva terminalInteractiva, Editor editor) {
        this.terminalInteractiva = terminalInteractiva;
        this.editor = editor;
        
        terminalInteractiva.getCurrentMessage().addListener(new ChangeListener<Mensaje>() {
            @Override
            public void changed(ObservableValue<? extends Mensaje> observable, Mensaje oldValue, Mensaje newValue) {
                update(null, newValue);
            }
        });
        
    }

    /**
     * Escanea el proyecto en busca de clases declaradas en todos los
     * directorios y subdirectorios
     *
     * @throws ufps.arqui.python.poo.gui.exceptions.Exceptions
     */
    public void escanearProyecto() throws Exceptions {
        if (this.directorioRaiz == null) {
            throw new Exceptions("El proyecto no ha sido seleccionado", null);
        }
        // Crear directorio src
        File file = new File(this.directorioRaiz + File.separator + "src");
        if (!file.exists()) {
            file.mkdir();
        }

        //Si el archivo scan no esta en la raiz del proyecto, lo crea
        try {
            ConfScanFile.actualizarArchivoScan(this.directorioRaiz);
        } catch (IOException e) {
            throw new Exceptions("No se ha podido actualizar el archivo scan", e);
        }
        
        //Es necesario resetear la terminal debido a que este metodo es llamado de
        //diferentes lugares luego de realizar una accion sobre el proyecto
        this.terminalInteractiva.inicializarTerminal();
        
        this.terminalInteractiva.ingresarComando("scanner_project()");
    }

    /**
     * Lista las clases correspondientes a un directorio.
     * <p>
     * Se toma la ruta relativa y se concatena a la ruta del proyecto para asi
     * obtener la ruta absooluta del directorio en el cual se extraeran las
     * clases
     *
     * @param fichero
     */
    public void obtenerClases(Fichero fichero) {
        List<ClasePython> classes = this.obtenerClasesDesde(fichero);
        
        this.currentListClasses.setValue(classes);
        this.setChanged();
        this.notifyObservers(classes);
    }

    /**
     * Lista las clases correspondientes a un directorio.
     *
     * @param directorio
     * @return listado de clases de python de un directivo dado.
     */
    private List<ClasePython> obtenerClasesDesde(Fichero fichero) {
        if(fichero.isFile()) return ((ArchivoPython)fichero).getClases();
        
        List<ClasePython> clases = new ArrayList<>();
        this.obtenerClasesFolder((Directorio)fichero, clases);
        return clases;
    }

    /**
     * Lista las clases correspondientes a un directorio de forma recursiva.
     *
     * @param directorio
     * @param clases
     */
    private void obtenerClasesFolder(Directorio directorio, List<ClasePython> clases) {
        for (ArchivoPython archivo : directorio.getArchivos()) {
            clases.addAll(archivo.getClases());
        }
        for (Directorio subdir : directorio.getDirectorios()) {
            this.obtenerClasesFolder(subdir, clases);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String getComandoPython() {
        return comandoPython;
    }

    public String getDirectorio() {
        return directorioRaiz != null ? directorioRaiz.getAbsolutePath() : "";
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        this.update("nombre");
    }

    /**
     * Reinicia todo el proyecto.
     */
    public void resetearProyecto() {
        this.nombre = null;
        this.comandoPython = null;
        this.fileProperties = new Properties();
        this.directorioRaiz = null;
    }

    public void setComandoPython(String comandoPython) {
        this.comandoPython = comandoPython;
        this.update("comandoPython");
    }

    public void setDirectorioRaiz(File directorioRaiz){
        this.directorioRaiz = directorioRaiz;
    }
    
    /**
     * Carga el proyecto por medio de la operación que se desea crear/abrir
     * @param crearProyecto Si es verdadero indica que se desea crear un proyecto, de lo contrario abrir
     * @throws Exceptions 
     */
    public void cargarProyecto(boolean crearProyecto) throws Exceptions {
        File properties = new File(this.directorioRaiz.getAbsolutePath() + File.separator + "project.properties");
        
        if(!crearProyecto) this.cargarPropiedades(properties);
        
        this.testearPython();
        
        if(crearProyecto){
            this.directorioRaiz.mkdirs();
            this.crearPropiedades(properties);
        }
        
        this.inicializarProyecto();
        
        this.update("directorio");
        this.escanearProyecto();
    }
    
    /**
     * Inicializa el proyecto en el sistema, carga los ficheros y clases correspondientes del proyecto
     * @throws Exceptions 
     */
    private void inicializarProyecto() throws Exceptions {
        String commands[] = new String[]{this.comandoPython ,"-i" ,"-q" ,"scan.py"};
        this.terminalInteractiva.setDirectorio(this.directorioRaiz);
        this.terminalInteractiva.setParameters(commands);        
        this.terminalInteractiva.inicializarTerminal();
    }
    
    /**
     * Testea si el comando para ejecutar python es funcional, de no ser funcional <br>
     * lanza una excepción
     * @throws Exceptions 
     */
    private void testearPython() throws Exceptions{
        try{
            this.terminalInteractiva.setParameters(new String[]{this.comandoPython});
            this.terminalInteractiva.inicializarTerminal();
        }catch(Exceptions e){
            throw new Exceptions("El comando para ejecutar python no es funcional", null);
        }
    }
    
    /**
     * Carga el archivo de propiedades del proyecto, es llamado cuando se esta abriendo un proyecto
     * @param properties
     * @throws Exceptions 
     */
    private void cargarPropiedades(File properties) throws Exceptions{
        if (!properties.exists() && this.nombre == null) {
            throw new Exceptions("El directorio seleccionado no contiene ningún proyecto.", null);
        }
        if (properties.exists() && this.nombre != null) {
            throw new Exceptions("El directorio seleccionado ya contiene un proyecto.", null);
        }
        
        try{
            FileInputStream in = new FileInputStream(properties);
            this.fileProperties.load(in);
            this.nombre = this.fileProperties.getProperty("NAME");
            this.comandoPython = this.fileProperties.getProperty("PYTHON");
            in.close();
        }catch(IOException e){
            throw new Exceptions("No se ha podido acceder al archivo de configuración", null);
        }
    }
    
    /**
     * Crea el archivo de propiedades, es llamado cuando se esta creando un proyecto
     * @param properties
     * @throws Exceptions 
     */
    private void crearPropiedades(File properties) throws Exceptions{
        try{
            properties.createNewFile();
            FileOutputStream out = new FileOutputStream(properties);
            this.fileProperties.setProperty("NAME", this.nombre);
            this.fileProperties.setProperty("PYTHON", this.comandoPython);
            this.fileProperties.setProperty("DIR", this.directorioRaiz.getAbsolutePath());
            fileProperties.store(out, null);
            out.close();
        }catch(IOException e){
            throw new Exceptions("Ha ocurrido un problema al crear el archivo de propiedades");
        }
    }
    
    public ObjectProperty<Directorio> getDirectorioTrabajoProperty() {
        return directorioTrabajo;
    }
    
    public Directorio getDirectorioTrabajo() {
        return directorioTrabajo.get();
    }
    
    public ObjectProperty<List<ClasePython>> getCurrentListClassesProperty() {
        return this.currentListClasses;
    }

    public ObjectProperty<MundoInstancia[]> getCurrentInstancesProperty() {
        return currentInstances;
    }

    /**
     * Actualiza el modelo y notifica a los observaciones del Mundo a que se a
     * realizado un cambio
     *
     * @param type representa el tipo de cambio realizado.
     */
    private void update(String type) {
        super.setChanged();
        super.notifyObservers(type);
    }

    @Override
    public void update(Observable o, Object arg) {
        //Proyecto solo esta pendiente de la terminal, por lo tanto solo va ser notificado por esta misma
        if (arg instanceof Mensaje) {
            Mensaje m = (Mensaje) arg;
            Gson gson = new Gson();
            if (m.getTipo().esDirectorio()) {
                try {
                    ScannerProject scan = gson.fromJson(m.getLinea(), ScannerProject.class);
                    this.directorioTrabajo.setValue(scan.getRelationalData());
                    this.currentListClasses.setValue(this.obtenerClasesDesde(this.directorioTrabajo.get()));
                    this.update("directoriosTrabajo");
                } catch (Exception e) {
                }
            }
            if (m.getTipo().esImports()) {
                try {
                    String[] importaciones = gson.fromJson(m.getLinea(), String[].class);
                    for (String impor : importaciones) {
                        this.terminalInteractiva.ingresarComando(impor);
                    }
                } catch (Exception e) {
                }
            }
            if (m.getTipo().esLineClass()) {
                try {
                    LineasClase lineasClase = gson.fromJson(m.getLinea(), LineasClase.class);
                    File file = new File(lineasClase.getArchivo());
                    StringBuilder contenido =  new StringBuilder();
                    AdministrarArchivo.abrirArchivo(file, contenido);
                    String[] lineas = contenido.toString().split("\n");
                    String inicio = "";
                    for (int i = 0; i<lineas.length; i++) {
                        if (i <= lineasClase.getInicio()-1 || i >= lineasClase.getFin()+1) {
                            inicio += lineas[i]+"\n";
                        }
                    }
                    AdministrarArchivo.escribirArchivo(file, inicio, false);
                    this.escanearProyecto();
                } catch (Exception e) {
                }
            }
            
            if(m.getTipo().esErrorCompilacion()){
                try{
                    ExcepcionCompilar excepcionCompilar = gson.fromJson(m.getLinea(), ExcepcionCompilar.class);
                    this.editor.getUltimoArchivoAbierto().setExcepcionCompilar(excepcionCompilar);
                    this.editor.abrirArchivo();
                    this.escanearProyecto();
                }catch(Exceptions e){
                    e.printStackTrace();
                }
            }
            
            if (m.getTipo().esInstancia()) {
                this.currentInstances.setValue(gson.fromJson(m.getLinea(), MundoInstancia[].class));
            }
        }
    }

    public void eliminarArchivo(String relativePath) throws Exceptions {
        File file = new File(this.directorioTrabajo.get().getFichero().getAbsolutePath() + File.separator + relativePath);
        if (file.getAbsolutePath().equals(this.directorioTrabajo.get().getFichero().getAbsolutePath())) {
            throw new Exceptions("No se puede eliminar el Directorio de Trabajo", null);
        }
        AdministrarArchivo.eliminarArchivo(file);

        this.setChanged();
        this.update("archivoBorrado");
    }
    
    public void eliminarArchivoV2(Fichero fichero) throws Exceptions {
        if (fichero == this.directorioTrabajo.get()) {
            //Son la misma referencia
            throw new Exceptions("No se puede eliminar el Directorio de Trabajo", null);
        }
        AdministrarArchivo.eliminarArchivo(fichero.getFichero());

        this.escanearProyecto();
    }

    /**
     * Abre un <code>ArchivoPython</code> que corresponda a la ruta relativa pasada como
     * parametro
     *
     * @param relativaPathFile Ruta relativa del archivo a abrir
     * @throws Exceptions
     */
    public void abrirArchivo(String relativaPathFile) throws Exceptions {
        String absolutePathFile = this.directorioRaiz.getAbsolutePath() + File.separator + relativaPathFile;
        ArchivoPython archivoPython = this.directorioTrabajo.get().getArchivo(absolutePathFile);
        this.editor.setUltimoArchivoAbierto(archivoPython);
        
        this.compilarArchivo(relativaPathFile);
    }

    /**
     * Compila uel archivo python destinado en la ruta dada por absolutePathFile
     * Luego de compilar el archivo se escaneara el proyecto
     * @param absolutePathFile
     * @throws Exceptions 
     */
    private void compilarArchivo(String absolutePathFile) throws Exceptions{
        String separador = File.separator + File.separator;
        absolutePathFile = absolutePathFile.replaceAll(separador, separador+separador);
        this.terminalInteractiva.ingresarComando("compile_file('"+absolutePathFile+"')");
    }
    
    /**
     * Elimina una clase del proyecto
     * @param nombreClase Nombre de la clase a eliminar
     * @throws Exceptions En caso de que no exista la clase
     */
    public void eliminarClase(String nombreClase) throws Exceptions {
        this.terminalInteractiva.ingresarComando("get_lines_class("+nombreClase+")");
    }

    /**
     * Cierra un <code>ArchivoPython</code> que corresponda a la ruta absoluta pasada como
     * parametro
     *
     * @param absolutePathFile Ruta absoluta del archivo a abrir
     * @throws Exceptions
     */
    public void cerrarArchivo(String absolutePathFile) throws Exceptions {
        ArchivoPython archivoPython = this.directorioTrabajo.get().getArchivo(absolutePathFile);
        this.editor.cerrarArchivo(archivoPython);
    }

    /**
     * Guarda el contenido en un <code>ArchivoPython</code> que corresponda con <br>
     * la ruta absoluta pasada como parametro
     *
     * @param absolutePathFile Ruta absoluta del archivo
     * @param contenido        Contenido nuevo a escribir en el archivo
     * @throws Exceptions
     */
    public void guardarArchivo(String absolutePathFile, String contenido) throws Exceptions {
        ArchivoPython archivoPython = this.directorioTrabajo.get().getArchivo(absolutePathFile);
        this.editor.guardarArchivo(archivoPython, contenido);
        this.compilarArchivo(absolutePathFile);
    }

    /**
     * Crear un nuevo archivo
     *
     * @param relativeUrl directorio relativo
     * @param nombre      nombre del archivo
     * @throws Exceptions en caso del que el archivo exista.
     */
    public void crearArchivo(String relativeUrl, String nombre) throws Exceptions {
        File file = new File(this.directorioRaiz.getAbsolutePath() + File.separator + relativeUrl + File.separator + nombre);
        if (file.exists()) {
            throw new Exceptions("El archivo ya existe en el directorio indicado", null);
        }
        if (!nombre.toUpperCase().endsWith(".PY")) {
            file.mkdir();
            File fileInit = new File(file.getAbsolutePath()+ File.separator+"__init__.py");
            try {
                fileInit.createNewFile();
            } catch (IOException e) {
                throw new Exceptions("El archivo inicial no puede ser creado", e);
            }
            this.escanearProyecto();
            return;
        }
        String nombre_clase = nombre.replaceAll(".py", "");
        try {
            file.createNewFile();
            String contenidoClase = "class "+nombre_clase+"(object):\n" +
                    "    def __init__(self):\n" +
                    "        self.a = 1\n" +
                    "        self.b = 3";
            AdministrarArchivo.escribirArchivo(file, contenidoClase, false);
        } catch (IOException e) {
            throw new Exceptions("El archivo no puede ser creado", e);
        }
        ArchivoPython archivo = new ArchivoPython();
        archivo.setFichero(file);
        this.directorioTrabajo.get().addArchivo(archivo);
        this.escanearProyecto();
    }
    
    /**
     * Crea una clase con el nombre <code>nombre</code> en el archivo python que <br>
     * corresponda con la ruta absoluta
     * @param absolutePath Ruta absoluta del archivo al cual se le añadira una nueva clase
     * @param nombre Nombre de la clase a ser añadida
     */
    public void crearClase(String absolutePath, String nombre) throws Exceptions {
        ArchivoPython archivoPython = this.directorioTrabajo.get().getArchivo(absolutePath);
        
        String dirWork = this.directorioRaiz.getAbsolutePath();
        String separador = File.separator+File.separator;
        String module = absolutePath.replace(dirWork, "").substring(1).replaceAll(separador, ".").replace(".py", "");
        
        this.editor.crearClase(archivoPython, module, nombre);
        
        this.escanearProyecto();
    }

    public TerminalInteractiva getTerminalInteractiva() {
        return terminalInteractiva;
    }

    public void ingresarComandoTerminal(String command) throws Exceptions{
        this.terminalInteractiva.ingresarComando(command);
        
        //Luego de enviar comando verificar si el usuario ha creado instancias
        this.terminalInteractiva.ingresarComando("list_all_instancias() if 'list_all_instancias' in dir() else [].clear()");
    }
}
