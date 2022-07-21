package ufps.arqui.python.poo.gui.controllers.impl.complements;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.controllers.impl.FXMLBaseController;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.views.impl.complements.ViewPanelClass;

/**
 * FXML Controller class
 *
 * @author Sachikia
 */
public class FXMLPanelClassController extends FXMLBaseController<BorderPane, ViewPanelClass> implements Initializable {

    @FXML
    private Label lblName;
    
    public FXMLPanelClassController(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.resources = resources;
        
        super.init(ViewPanelClass.class);
        
        super.view.setLblName(this.lblName);
    }    
    
}
