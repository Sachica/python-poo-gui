/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufps.arqui.python.poo.gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.collections.ListChangeListener;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.models.ArchivoPython;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.views.ViewEditorTexto;

/**
 *
 * @author dunke
 */
public class FXMLEditorTextoController extends FXMLBaseController<ViewEditorTexto>{

    @FXML
    private TabPane tabPane;
    
    private final Consumer<ArchivoPython> onCloseTab;

    public FXMLEditorTextoController(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
        
        this.onCloseTab = (archivoPython) -> {
            this.proyecto.getEditor().cerrarArchivo(archivoPython);
        };
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        
        this.bindOpenFiles();
        
        this.view.setOnCloseTab(this.onCloseTab);
    }

    public void handleSaveFile(ActionEvent actionEvent) {
    }

    public void handleSaveAllFiles(ActionEvent actionEvent) {
    }

    public void handleNewClass(ActionEvent actionEvent) {
    }

    public void handleDeleteClass(ActionEvent actionEvent) {
    }
    
    private void bindOpenFiles(){
        super.proyecto.getEditor().getArchivosAbiertos().addListener(new ListChangeListener<ArchivoPython>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends ArchivoPython> e) {
                if( e.next()){
                    //Ya estaba abierto o fue un nuevo archivo?
                    if(e.wasReplaced() || e.wasAdded()){
                        view.abriArchivos(e.getAddedSubList());
                    }else if(e.wasRemoved()){
                        view.cerrarArchivos(e.getRemoved());
                    }
                }
            }
        });
    }
}
