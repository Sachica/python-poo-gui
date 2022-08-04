package ufps.arqui.python.poo.gui.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import ufps.arqui.python.poo.gui.MainApp;
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
    
    private final Map<String, TreeItem> treeItems = new HashMap<>();
    
    private final List<TreeItem<Fichero>> waitingForParent = new ArrayList<>();
    
    private final Image imageFile;
    
    private final Image imageFolder;
    
    public ViewFichero() {
        super();

        this.imageFile = new Image(getClass().getResourceAsStream(BluePyUtilities.PYTHON_FILE_LOGO));
        this.imageFolder = new Image(getClass().getResourceAsStream(BluePyUtilities.FOLDER_ICON));
    }
    
    //treeItem si o si sera directorio
    private void searchForChilds(TreeItem<Fichero> parent){
        parent.setExpanded(true);
        List<TreeItem<Fichero>> wfpTemp = new ArrayList<>(this.waitingForParent);
        for(TreeItem<Fichero> child: wfpTemp){
            if(((Directorio)parent.getValue()).isMyChild(child.getValue().getFichero().getAbsolutePath())){
                parent.getChildren().add(child);
                this.treeItems.put(child.getValue().getFichero().getAbsolutePath(), child);
                
                this.waitingForParent.remove(child);
                if(child.getValue().isDirectory()){
                    this.searchForChilds(child);
                }
            }
        }
    }
    
    private void searchForParent(TreeItem<Fichero> child){
        boolean found = false;
        for(TreeItem<Fichero> parent: this.treeItems.values()){
            if(parent.getValue().isDirectory() &&
                    ((Directorio)parent.getValue()).isMyChild(child.getValue().getFichero().getAbsolutePath())){
                
                parent.getChildren().add(child);
                this.waitingForParent.remove(child);
                found = true;
                break;
            }
        }
        
        if(found){
            this.treeItems.put(child.getValue().getFichero().getAbsolutePath(), child);
            if(child.getValue().isDirectory()){
                this.searchForChilds(child);
            }
        }
    }
    
    public void setRootTreeView(Fichero fichero){
        Platform.runLater(() -> {
            TreeItem<Fichero> rootItem = this.getTreeItem(fichero);
            this.treeView.setRoot(rootItem);
            this.treeItems.put(fichero.getFichero().getAbsolutePath(), rootItem);
            searchForChilds(rootItem);
        });
    }
    
    private void onAddedItem(Fichero fichero) {
        Platform.runLater(() -> {
            TreeItem<Fichero> treeItem = this.getTreeItem(fichero);
            this.waitingForParent.add(treeItem);
            this.searchForParent(treeItem);
        });
    }
    
    private void onRemovedItem(Fichero fichero) {
        Platform.runLater(() -> {
            TreeItem<Fichero> removed = this.treeItems.get(fichero.getFichero().getAbsolutePath());
            removed.getParent().getChildren().remove(removed);
            this.treeItems.remove(removed.getValue().getFichero().getAbsolutePath());
        });
    }
    
    private TreeItem<Fichero> getTreeItem(Fichero fichero){
        Image img = (fichero.isFile()) ? this.imageFile : this.imageFolder;
        
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(15);
        imgView.setFitWidth(15);
        
        return new TreeItem<>(fichero, imgView){
            @Override
            public boolean isLeaf() {
                return fichero.isFile();
            }
        };
    }
    
    public void updateTreeItem(MapChangeListener.Change<? extends String, ? extends Fichero> event){
        if(event.wasAdded()){
            String key = event.getValueAdded().getFichero().getAbsolutePath();
            //El fichero ya esta en el arbol? Si es asi fue una actualizacion
            if(this.treeItems.containsKey(key)){
                this.treeItems.get(key).setValue(event.getValueAdded());
            }else{
                //Es un fichero nuevo
                this.onAddedItem(event.getValueAdded());
            }
        }else{
            //El fichero fue removido
            this.onRemovedItem(event.getValueRemoved());
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
                    MainApp.getView(BluePyUtilities.MODAL_CREATE_FILE).preload(selected);
                }else{
                    this.contextMenuFile.show(this.treeView, event.getScreenX(), event.getScreenY());
                    this.contextMenuFolder.hide();
                }
            }
        });
    }
}
