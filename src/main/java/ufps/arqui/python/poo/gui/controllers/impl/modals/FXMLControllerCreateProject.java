package ufps.arqui.python.poo.gui.controllers.impl.modals;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.models.Proyecto;

/**
 *
 * @author Sachikia
 */
public class FXMLControllerCreateProject extends FXMLControllerOpenProject implements Initializable{
    
    public FXMLControllerCreateProject(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.resources = resources;
    }
}
