package ufps.arqui.python.poo.gui.controllers.impl;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.exceptions.Exceptions;
import ufps.arqui.python.poo.gui.models.Directorio;
import ufps.arqui.python.poo.gui.models.Fichero;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.views.impl.ViewFichero;

/**
 *
 * @author Sachikia
 */
public class FXMLFicheroController extends FXMLBaseController<BorderPane, ViewFichero>  implements Initializable{
    
    @FXML
    private TreeView<Fichero> treeView;
    
    @FXML
    private ContextMenu contextMenuFolder;
    
    @FXML
    private ContextMenu contextMenuFile;
    
    public FXMLFicheroController(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.resources = resources;
        
        super.init(ViewFichero.class);
        
        Consumer<Fichero> consume = new Consumer<Fichero>() {
            @Override
            public void accept(Fichero t) {
                proyecto.obtenerClasesDesde(t);
            }
        };
        this.view.setTreeView(this.treeView, consume);
        this.view.configContextMenu(this.contextMenuFolder, this.contextMenuFile);
        
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
