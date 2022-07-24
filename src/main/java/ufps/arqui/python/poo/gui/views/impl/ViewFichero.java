package ufps.arqui.python.poo.gui.views.impl;

import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

    private ContextMenu contextMenu;
    
    private MenuItem menuItemCreate;
    
    private MenuItem menuItemDelete;

    public ViewFichero(BorderPane view, Stage stage, ResourceBundle resources) {
        super(view, stage, resources);
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

    public void setTreeView(TreeView<Fichero> treeView, Consumer<Fichero> consume) {
        this.treeView = treeView;
        this.treeView.setOnMouseClicked((MouseEvent event) -> {
            if(!this.treeView.getSelectionModel().isEmpty()){
                Fichero selected = treeView.getSelectionModel().getSelectedItem().getValue();
                if (selected.isDirectory()) consume.accept(selected);
            }
        });
        
        this.treeView.setOnContextMenuRequested(event -> {
            if(!this.treeView.getSelectionModel().isEmpty()){
                switchTextContextMenu();
                this.contextMenu.show(this.treeView, event.getScreenX(), event.getScreenY());
            }
        });
    }

    private void switchTextContextMenu(){
        String createPrefixProp = "PanelFichero.create";
        String deletePrefixProp = "PanelFichero.delete";
        String file = "File", folder = "Folder";
        
        Fichero selected = treeView.getSelectionModel().getSelectedItem().getValue();
        String property = selected.isDirectory() ? folder : file;
        this.menuItemCreate.setText(super.resources.getString(createPrefixProp+property));
        this.menuItemDelete.setText(super.resources.getString(deletePrefixProp+property));
    }
    
    public void configContextMenu(ContextMenu contextMenu, MenuItem menuItemCreate, MenuItem menuItemDelete) {
        this.contextMenu = contextMenu;
        this.menuItemCreate = menuItemCreate;
        this.menuItemDelete = menuItemDelete;
    }
    
    public Fichero getCurrentFicheroSelected(){
        if(!this.treeView.getSelectionModel().isEmpty()){
            return treeView.getSelectionModel().getSelectedItem().getValue();
        }
        return null;
    }
}
