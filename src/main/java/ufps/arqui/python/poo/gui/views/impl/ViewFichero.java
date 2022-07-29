package ufps.arqui.python.poo.gui.views.impl;

import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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

    private ContextMenu contextMenuFolder;
    
    private ContextMenu contextMenuFile;
    
    private Consumer<Fichero> onClickItem;
    
    private Image image;

    public ViewFichero() {
        super();
        
        this.image = new Image(getClass().getResourceAsStream(BluePyUtilities.PYTHON_FILE_LOGO));
    }
    
    public void populateTreeView(Directorio directorio) {
        Platform.runLater(() -> {
            this.populate(directorio, null);
        });
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
            ImageView imgView = new ImageView(this.image);
            imgView.setFitHeight(15);
            imgView.setFitWidth(15);
            
            TreeItem<Fichero> treeFile = new TreeItem<>(archivoPython, imgView);
            child.getChildren().add(treeFile);
        }
        
        for(Directorio subDir: dir.getDirectorios()){
            this.populate(subDir, child);
        }
    }
    
    public Fichero getCurrentFicheroSelected(){
        if(!this.treeView.getSelectionModel().isEmpty()){
            return treeView.getSelectionModel().getSelectedItem().getValue();
        }
        return null;
    }

    @Override
    public void initialize() {
        super.initialize();
        
        this.treeView.setOnMouseClicked((MouseEvent event) -> {
            if(!this.treeView.getSelectionModel().isEmpty()){
                Fichero selected = treeView.getSelectionModel().getSelectedItem().getValue();
                this.onClickItem.accept(selected);
            }
        });
        
        this.treeView.setOnContextMenuRequested(event -> {
            if(!this.treeView.getSelectionModel().isEmpty()){
                Fichero selected = treeView.getSelectionModel().getSelectedItem().getValue();
                if(selected.isDirectory()){
                    this.contextMenuFolder.show(this.treeView, event.getScreenX(), event.getScreenY());
                    this.contextMenuFile.hide();
                }else{
                    this.contextMenuFile.show(this.treeView, event.getScreenX(), event.getScreenY());
                    this.contextMenuFolder.hide();
                }
            }
        });
    }
}
