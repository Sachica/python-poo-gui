package ufps.arqui.python.poo.gui.controllers.modals;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.controllers.FXMLBaseController;
import ufps.arqui.python.poo.gui.exceptions.Exceptions;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.views.modals.ViewModalOpenProject;

/**
 *
 * @author Sachikia
 */
public class FXMLControllerOpenProject extends FXMLBaseController<ViewModalOpenProject>{
    @FXML
    private TextField txtPathProject; 

    public FXMLControllerOpenProject(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }
    
    @FXML
    private void handleSearchFolder(ActionEvent event){
        this.view.showSearchFolder();
    }
   
    @FXML
    private void handleOpenProject(ActionEvent event){
        try {
            File directorio = this.view.getValidatePathProject();
            
            this.proyecto.resetearProyecto();
            this.proyecto.setDirectorioRaiz(directorio);
            this.proyecto.cargarProyecto(false);
        }catch (Exceptions e){
//            super.showError(e);
        }
        
    }
}
