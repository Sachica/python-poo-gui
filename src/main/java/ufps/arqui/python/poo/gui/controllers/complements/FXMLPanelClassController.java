package ufps.arqui.python.poo.gui.controllers.complements;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.controllers.FXMLBaseController;
import ufps.arqui.python.poo.gui.exceptions.Exceptions;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.views.complements.ViewPanelClass;

/**
 * FXML Controller class
 *
 * @author Sachikia
 */
public class FXMLPanelClassController extends FXMLBaseController<ViewPanelClass> {

    @FXML
    private Label lblName;
    
    @FXML
    private ContextMenu contextMenu;
    
    public FXMLPanelClassController(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }    
    
    @FXML
    private void handleDeleteClass(){
        try{
            super.proyecto.eliminarClase(this.lblName.getText());
        }catch(Exceptions e){
            e.printStackTrace();
        }
    }
    
    
    @FXML
    private void handleEditClass(){
        
    }
}
