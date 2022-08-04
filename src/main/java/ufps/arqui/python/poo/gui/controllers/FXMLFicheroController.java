package ufps.arqui.python.poo.gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.MainApp;
import ufps.arqui.python.poo.gui.annotations.SharedView;
import ufps.arqui.python.poo.gui.exceptions.Exceptions;
import ufps.arqui.python.poo.gui.models.ArchivoPython;
import ufps.arqui.python.poo.gui.models.Directorio;
import ufps.arqui.python.poo.gui.models.Fichero;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.utils.BluePyUtilities;
import ufps.arqui.python.poo.gui.views.ViewFichero;

/**
 *
 * @author Sachikia
 */
public class FXMLFicheroController extends FXMLBaseController<ViewFichero>{
    
    @FXML
    private TreeView<Fichero> treeView;
    
    @FXML
    private ContextMenu contextMenuFolder;
    
    @FXML
    private ContextMenu contextMenuFile;
    
    @SharedView
    private Consumer<Fichero> onRequestPaintClass;
    
    @SharedView
    private Consumer<Fichero> onRequestEditor;
    
    public FXMLFicheroController(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
        
        this.onRequestPaintClass = (Fichero t) -> {
            proyecto.obtenerClases(t);
        };
        
        this.onRequestEditor = (Fichero t) -> {
            try{
                proyecto.abrirArchivoV2((ArchivoPython)t);
            }catch(Exceptions e){
            }
        };
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        
        this.proyecto.getDirectorioTrabajoProperty().addListener(new ChangeListener<Directorio>() {
            @Override
            public void changed(ObservableValue<? extends Directorio> observable, Directorio oldValue, Directorio newValue) {
                view.setRootTreeView(newValue);
            }
        });
        
        MapChangeListener<String, Fichero> mapChange = new MapChangeListener<String, Fichero>() {
            @Override
            public void onChanged(MapChangeListener.Change<? extends String, ? extends Fichero> event) {
                view.updateTreeItem(event);
            }
        };
        
        this.proyecto.getScan().getDirectorys().addListener(mapChange);
        this.proyecto.getScan().getFiles().addListener(mapChange);
    }
    
    @FXML
    private void handleCreate(ActionEvent actionEvent){
        MainApp.getView(BluePyUtilities.MODAL_CREATE_FILE).showModal(true);
    }
    
    @FXML
    private void handleDelete(ActionEvent actionEvent){
        Fichero fichero = this.view.getCurrentFicheroSelected();
        if(fichero!=null){
            try{
                this.proyecto.eliminarArchivo(fichero);
            }catch(Exceptions e){
                //
            }
        }
    }

    public void handleRename(ActionEvent actionEvent) {
    }

    public void handleFindUsages(ActionEvent actionEvent) {
    }

    public void handleOpenFile(ActionEvent actionEvent) {
    }
}
