package ufps.arqui.python.poo.gui.controllers.impl.complements;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.controllers.impl.FXMLBaseController;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.views.impl.complements.ViewPanelInstance;

/**
 *
 * @author Sachikia
 */
public class FXMLPanelInstanceController extends FXMLBaseController<ViewPanelInstance>{
    
    @FXML
    private Label lblName;
    
    @FXML
    private Label lblClass;
    
    @FXML
    private ContextMenu contextMenu;

    public FXMLPanelInstanceController(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }
    
    @FXML
    private void handleInspectInstance(ActionEvent evt){
        this.view.showModalInfoInstance();
    }
    
    @FXML
    private void handleDeleteInstance(ActionEvent evt){
    }
}
