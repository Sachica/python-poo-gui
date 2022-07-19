package ufps.arqui.python.poo.gui.controllers.impl;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.models.ArchivoPython;
import ufps.arqui.python.poo.gui.models.Directorio;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.utils.BluePyUtilities;

/**
 *
 * @author Sachikia
 */
public class FXMLFicheroController extends FXMLBaseController implements Initializable{
    
    @FXML
    private TreeView<String> treeView;
    
    public FXMLFicheroController(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.resources = resources;
        
        this.proyecto.getDirectorioTrabajoProperty().addListener(new ChangeListener<Directorio>() {
            @Override
            public void changed(ObservableValue<? extends Directorio> observable, Directorio oldValue, Directorio newValue) {
                populate(newValue, null);
            }
        }
        );
    }
    
    
    private void populate(Directorio dir, TreeItem<String> parent){
        TreeItem<String> child = new TreeItem<>(dir.getDirectorio().getName());
        if(parent == null){
            this.treeView.setRoot(child);
        }else{
            parent.getChildren().add(child);
        }
        
        for(ArchivoPython archivoPython: dir.getArchivos()){
            ImageView imgView = new ImageView(new Image(getClass().getResourceAsStream(BluePyUtilities.PYTHON_FILE_LOGO)));
            imgView.setFitHeight(15);
            imgView.setFitWidth(15);
            
            TreeItem<String> treeFile = new TreeItem<>(archivoPython.getArchivo().getName(), imgView);
            child.getChildren().add(treeFile);
        }
        
        for(Directorio subDir: dir.getDirectorios()){
            this.populate(subDir, child);
        }
    }
}
