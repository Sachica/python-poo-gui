/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufps.arqui.python.poo.gui.controllers.modals;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.controllers.FXMLBaseController;
import ufps.arqui.python.poo.gui.exceptions.Exceptions;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.views.modals.ViewModalCreateFile;

/**
 *
 * @author dunke
 */
public class FXMLControllerCreateFile extends FXMLBaseController<ViewModalCreateFile>{
    
    @FXML
    private TextField txtNameFile;
    
    @FXML
    private Label lblParentName;
    
    public FXMLControllerCreateFile(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }

    @FXML
    public void handleCreateFile(ActionEvent actionEvent) {
        try{
            super.proyecto.crearFichero(this.view.getFichero(), this.view.getNameFile());
        }catch(Exceptions e){
        }
    }
}
