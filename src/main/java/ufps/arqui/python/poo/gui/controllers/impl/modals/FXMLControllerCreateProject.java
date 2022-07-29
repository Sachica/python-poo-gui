package ufps.arqui.python.poo.gui.controllers.impl.modals;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.controllers.impl.FXMLBaseController;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.views.impl.modals.ViewModalCreateProject;

/**
 *
 * @author Sachikia
 */
public class FXMLControllerCreateProject extends FXMLBaseController<ViewModalCreateProject> {
    
    @FXML
    private TextField txtPathFolder;
    
    @FXML
    private TextField txtName;
    
    @FXML
    private TextField txtPython;
    
    public FXMLControllerCreateProject(Stage stage, Proyecto proyecto) {
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
    private void handleChangeName(KeyEvent event){
        this.view.updateTxtPathFolder();
    }
}
