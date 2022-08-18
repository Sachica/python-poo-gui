/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufps.arqui.python.poo.gui.views;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.fxmisc.flowless.VirtualizedScrollPane;
import ufps.arqui.python.poo.gui.exceptions.Exceptions;
import ufps.arqui.python.poo.gui.models.ArchivoPython;
import ufps.arqui.python.poo.gui.views.editor.CustomCodeArea;
import ufps.arqui.python.poo.gui.views.editor.ManageTextEditor;

/**
 *
 * @author dunke
 */
public class ViewEditorTexto extends ViewBase<BorderPane, ArchivoPython>{
    private TabPane tabPane;
    
    private Consumer<ArchivoPython> onCloseTab;
    
    //[PathFile, [Tab, CodeArea, ArchivoPython]]
    private final ObservableMap<String, TabCodeArea> tabs = FXCollections.observableMap(new HashMap());

    public ViewEditorTexto() {
        super();
    }
    
    @Override
    public void initialize() {
        super.initialize(); 
        
        this.bindMapTabsWithTabPane();
        
        super.modal = new Stage();
        super.modal.setScene(new Scene(super.root));
        super.modal.setTitle(super.resources.getString("PanelEditorTexto.title"));
        
        super.modal.getScene().getStylesheets().add(getClass().getResource("/styles/python-keywords.css").toExternalForm());
    }

    @Override
    public void preload(ArchivoPython object) {
        if(object == null) return;
        try{
            TabCodeArea tabCodeArea = this.tabs.getOrDefault(
                    object.getFichero().getAbsolutePath(),
                    this.createTabCodeArea(object)
            );
            this.tabPane.getSelectionModel().select(tabCodeArea.tab);
            super.modal.requestFocus();
        }catch(Exceptions e){
            e.printStackTrace();
        }
    }
    
    private TabCodeArea createTabCodeArea(ArchivoPython object) throws Exceptions{
        CustomCodeArea codeArea = new CustomCodeArea(object.getContenido());
        Tab tab = this.createConfigTab(object, codeArea);
        TabCodeArea tabCodeArea = new TabCodeArea(tab, codeArea, object);

        this.tabs.put(object.getFichero().getAbsolutePath(), tabCodeArea);
        this.tabPane.getTabs().add(tab);
        
        return tabCodeArea;
    }
    
    private Tab createConfigTab(ArchivoPython object, CustomCodeArea codeArea){
        Tab tab = new Tab();
        tab.setText(object.getFichero().getName());
        tab.setOnClosed((e) -> {
            this.onCloseTab.accept(object);
        });
        tab.setContent(new VirtualizedScrollPane<>(codeArea.getCodeArea()));
        
        return tab;
    }
    
    public void abriArchivos(List<? extends ArchivoPython> archivosPython){
        for(ArchivoPython archivoPython: archivosPython){
            this.preload(archivoPython);
        }
    }
    
    public void cerrarArchivos(List<? extends ArchivoPython> archivosPython){
        for(ArchivoPython archivoPython: archivosPython){
            this.tabs.remove(archivoPython.getFichero().getAbsolutePath());
        }
    }
    
    public void setOnCloseTab(Consumer<ArchivoPython> onCloseTab){
        this.onCloseTab = onCloseTab;
    }
    
    public void stop(){
        for(TabCodeArea tabCodeArea: this.tabs.values()){
            tabCodeArea.codeArea.stop();
        }
        ManageTextEditor.getExecutor().shutdown();
    }
    
    private void bindMapTabsWithTabPane(){
        this.tabs.addListener(new MapChangeListener<String, TabCodeArea>() {
            @Override
            public void onChanged(MapChangeListener.Change<? extends String, ? extends TabCodeArea> e) {
                if(e.wasRemoved()){
                    Platform.runLater(() -> {
                        tabPane.getTabs().remove(e.getValueRemoved().tab);
                    });
                }else if(e.wasAdded()){
                    Platform.runLater(() -> {
                        tabPane.getTabs().add(e.getValueAdded().tab);
                    });
                }
            }
        });
    }
    
    private class TabCodeArea{
        Tab tab;
        CustomCodeArea codeArea;
        ArchivoPython archivoPython;

        public TabCodeArea(Tab tab, CustomCodeArea codeArea, ArchivoPython archivoPython) {
            this.tab = tab;
            this.codeArea = codeArea;
            this.archivoPython = archivoPython;
        }
    }
}
