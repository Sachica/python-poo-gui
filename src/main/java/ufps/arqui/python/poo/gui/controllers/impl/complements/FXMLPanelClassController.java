package ufps.arqui.python.poo.gui.controllers.impl.complements;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.controllers.impl.FXMLBaseController;
import ufps.arqui.python.poo.gui.models.Proyecto;

/**
 * FXML Controller class
 *
 * @author Sachikia
 */
public class FXMLPanelClassController extends FXMLBaseController implements Initializable {

    public FXMLPanelClassController(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.resources = resources;
    }    
    
}
