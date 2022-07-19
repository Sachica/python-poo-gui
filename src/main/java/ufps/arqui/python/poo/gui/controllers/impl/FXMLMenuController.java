package ufps.arqui.python.poo.gui.controllers.impl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import org.controlsfx.validation.ValidationSupport;
import ufps.arqui.python.poo.gui.MainApp;
import ufps.arqui.python.poo.gui.controllers.impl.modals.FXMLControllerOpenProject;
import ufps.arqui.python.poo.gui.exceptions.Exceptions;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.utils.BluePyUtilities;

/**
 *
 * @author Sachikia
 */
public class FXMLMenuController extends FXMLBaseController implements Initializable{

    /**
     * Recursos del proyecto
     */
    private ResourceBundle resources;

    /**
     * Stage para cargar el FXML "OpenProject"
     */
    private Stage modalOpenProject;

    public FXMLMenuController(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
    }
    
    @FXML
    private MenuBar menuBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
    }
       
    @FXML
    private void handleModalOpenProject(ActionEvent event) throws IOException {
        if(modalOpenProject == null){
            this.modalOpenProject = new Stage();
            
            FXMLControllerOpenProject controller = new FXMLControllerOpenProject(this.stage, this.proyecto);
            Parent root = BluePyUtilities.getView(BluePyUtilities.MODAL_OPEN_PROJECT, controller, this.resources);
            
            this.modalOpenProject.setScene(new Scene(root));
            this.modalOpenProject.setTitle(resources.getString("Modal.openProject"));
            this.modalOpenProject.initModality(Modality.APPLICATION_MODAL);
            this.modalOpenProject.initOwner(this.stage);
    }
        this.modalOpenProject.show();
    }
    
    @FXML
    private void handleModalCreateProject(ActionEvent event){
    
    }
    
}
