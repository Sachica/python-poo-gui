package ufps.arqui.python.poo.gui.controllers.impl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.views.impl.ViewMenu;

/**
 *
 * @author Sachikia
 */
public class FXMLMenuController extends FXMLBaseController<BorderPane, ViewMenu> implements Initializable{

    public FXMLMenuController(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.resources = resources;
        
        super.init(ViewMenu.class);
        
        super.view.setControllerFactory(super.getControllerFactory());
        super.view.preload(null);
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
