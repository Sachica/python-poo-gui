package ufps.arqui.python.poo.gui.controllers.impl.modals;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.controllers.impl.FXMLBaseController;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.views.impl.modals.ViewModalCreateProject;

/**
 *
 * @author Sachikia
 */
public class FXMLControllerCreateProject extends FXMLBaseController<BorderPane, ViewModalCreateProject> implements Initializable{
    
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
        super.resources = resources;
        
        super.init(ViewModalCreateProject.class);
        
        super.view.setTxtName(this.txtName);
        super.view.setTxtPathFolder(this.txtPathFolder);
        super.view.setTxtPython(this.txtPython);
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
