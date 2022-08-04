/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufps.arqui.python.poo.gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

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
    public TabPane tabPane;

    public FXMLEditorTextoController(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
        
        super.proyecto.getEditor().getUltimoArchivoAbiertoProperty().addListener(new ChangeListener<ArchivoPython>() {
            @Override
            public void changed(ObservableValue<? extends ArchivoPython> arg0, ArchivoPython arg1, ArchivoPython arg2) {
                view.preload(arg2);
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }

    public void handleSaveFile(ActionEvent actionEvent) {
    }

    public void handleSaveAllFiles(ActionEvent actionEvent) {
    }

    public void handleCloseTab(ActionEvent actionEvent) {
    }

    public void handleNewClass(ActionEvent actionEvent) {
    }

    public void handleDeleteClass(ActionEvent actionEvent) {
    }
}
