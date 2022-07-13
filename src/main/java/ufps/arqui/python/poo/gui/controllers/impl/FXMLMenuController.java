package ufps.arqui.python.poo.gui.controllers.impl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;

/**
 *
 * @author Sachikia
 */
public class FXMLMenuController implements Initializable{
    
    private ResourceBundle resources;
    private Stage modalOpenProject;

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
            Parent root = FXMLLoader.load(
                    Objects.requireNonNull(
                            getClass().getResource("/fxml/modals/OpenProject.fxml")
                    ), resources);
            this.modalOpenProject.setScene(new Scene(root));
            this.modalOpenProject.setTitle(resources.getString("modalOpenProject"));
            this.modalOpenProject.initModality(Modality.WINDOW_MODAL);
            this.modalOpenProject.initOwner((Stage) this.menuBar.getScene().getWindow());
        }
        this.modalOpenProject.show();
    }

    @FXML
    private void handleModalCreateProject(ActionEvent event){

    }

    @FXML
    private void handleOpenProject(ActionEvent event){

    }

    @FXML
    private void handleCreateProject(ActionEvent event){

    }
}
