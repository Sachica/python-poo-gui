package ufps.arqui.python.poo.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.views.ViewMenu;

/**
 *
 * @author Sachikia
 */
public class FXMLMenuController extends FXMLBaseController<ViewMenu> {

    public FXMLMenuController(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }
       
    @FXML
    private void handleModalOpenProject(ActionEvent event) {
        super.view.showModalOpenProject();
    }
    
    @FXML
    private void handleModalCreateProject(ActionEvent event){
        this.view.showModalCreateProject();
    }
}
