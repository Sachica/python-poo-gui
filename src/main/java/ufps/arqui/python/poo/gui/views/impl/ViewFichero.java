package ufps.arqui.python.poo.gui.views.impl;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ufps.arqui.python.poo.gui.models.ArchivoPython;
import ufps.arqui.python.poo.gui.models.Directorio;
import ufps.arqui.python.poo.gui.utils.BluePyUtilities;
import ufps.arqui.python.poo.gui.views.IViewFichero;

/**
 *
 * @author Sachikia
 */
public class ViewFichero implements IViewFichero{
    private TreeView<String> treeView;
    
    @Override
    public void populateTreeView(Directorio directorio) {
        this.populate(directorio, null);
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

    @Override
    public void setTreeView(TreeView<String> treeView) {
        this.treeView = treeView;
    }
}
