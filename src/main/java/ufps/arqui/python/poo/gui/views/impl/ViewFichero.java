package ufps.arqui.python.poo.gui.views.impl;

import java.util.ResourceBundle;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.models.ArchivoPython;
import ufps.arqui.python.poo.gui.models.Directorio;
import ufps.arqui.python.poo.gui.models.Fichero;
import ufps.arqui.python.poo.gui.utils.BluePyUtilities;

/**
 *
 * @author Sachikia
 */
public class ViewFichero extends ViewBase<BorderPane, Object>{
    private TreeView<Fichero> treeView;

    public ViewFichero(BorderPane view, Stage stage, ResourceBundle resources) {
        super(view, stage, resources);
    }
    
    public void populateTreeView(Directorio directorio) {
        this.populate(directorio, null);
    }
    
    private void populate(Directorio dir, TreeItem<Fichero> parent){
        TreeItem<Fichero> child = new TreeItem<>(dir);
        child.setExpanded(true);
        if(parent == null){
            this.treeView.setRoot(child);
            
        }else{
            parent.getChildren().add(child);
        }
        
        for(ArchivoPython archivoPython: dir.getArchivos()){
            ImageView imgView = new ImageView(new Image(getClass().getResourceAsStream(BluePyUtilities.PYTHON_FILE_LOGO)));
            imgView.setFitHeight(15);
            imgView.setFitWidth(15);
            
            TreeItem<Fichero> treeFile = new TreeItem<>(archivoPython, imgView);
            child.getChildren().add(treeFile);
        }
        
        for(Directorio subDir: dir.getDirectorios()){
            this.populate(subDir, child);
        }
    }

    public void setTreeView(TreeView<Fichero> treeView) {
        this.treeView = treeView;
    }
}
