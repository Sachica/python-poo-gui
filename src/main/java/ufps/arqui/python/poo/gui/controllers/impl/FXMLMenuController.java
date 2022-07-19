package ufps.arqui.python.poo.gui.controllers.impl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import ufps.arqui.python.poo.gui.models.Proyecto;

/**
 *
 * @author Sachikia
 */
public class FXMLMenuController extends FXMLBaseController implements Initializable{

    /**
     * Stage para cargar el FXML "OpenProject"
     */
    private final Stage modalOpenProject = new Stage();
    
    /**
     * Stage para cargar el FXML "CreateProject"
     */
    private final Stage modalCreateProject = new Stage();

    public FXMLMenuController(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.resources = resources;
    }
       
    @FXML
    private void handleModalOpenProject(ActionEvent event) throws IOException {
        this.modalOpenProject.show();
    }
    
    @FXML
    private void handleModalCreateProject(ActionEvent event){
        this.modalCreateProject.show();
    }

    public void setModalOpenProject(Parent root) {
        this.modalOpenProject.setScene(new Scene(root));
        this.modalOpenProject.setTitle(resources.getString("Modal.openProject"));
        this.modalOpenProject.initModality(Modality.APPLICATION_MODAL);
        this.modalOpenProject.initOwner(this.stage);
    }
    
    public void setModalCreateProject(Parent root) {
        this.modalCreateProject.setScene(new Scene(root));
        this.modalCreateProject.setTitle(resources.getString("Modal.createProject"));
        this.modalCreateProject.initModality(Modality.APPLICATION_MODAL);
        this.modalCreateProject.initOwner(this.stage);
    }
}
