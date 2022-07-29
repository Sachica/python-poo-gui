package ufps.arqui.python.poo.gui.controllers.impl;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.annotations.SharedView;
import ufps.arqui.python.poo.gui.exceptions.Exceptions;
import ufps.arqui.python.poo.gui.models.Directorio;
import ufps.arqui.python.poo.gui.models.Fichero;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.views.impl.ViewFichero;

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
    private Consumer<Fichero> onClickItem;
    
    public FXMLFicheroController(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
        
        this.onClickItem = (Fichero t) -> {
            proyecto.obtenerClases(t);
        };
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        
        this.proyecto.getDirectorioTrabajoProperty().addListener(new ChangeListener<Directorio>() {
            @Override
            public void changed(ObservableValue<? extends Directorio> observable, Directorio oldValue, Directorio newValue) {
                view.populateTreeView(newValue);
            }
        });
    }
    
    @FXML
    private void handleCreate(){
        
    }
    
    @FXML
    private void handleDelete(){
        Fichero fichero = this.view.getCurrentFicheroSelected();
        if(fichero!=null){
            try{
                this.proyecto.eliminarArchivoV2(fichero);
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
