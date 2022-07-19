package ufps.arqui.python.poo.gui.controllers.impl.modals;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.models.Proyecto;

/**
 *
 * @author Sachikia
 */
public class FXMLControllerCreateProject extends FXMLControllerOpenProject implements Initializable{
    
    @FXML
    private TextField txtPathFolder;
    
    @FXML
    private TextField txtName;
    
    @FXML
    private TextField txtPython;
    
    private final DirectoryChooser folderChooser = new DirectoryChooser();
    
    private File currentFolder;
    
    public FXMLControllerCreateProject(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.resources = resources;
    }
    
    @FXML
    private void handleSearchFolder(ActionEvent event){
        this.currentFolder = this.folderChooser.showDialog(this.stage);
        
        if(this.currentFolder != null){
            this.updateTxtPathFolder();
        }
    }
    
    @FXML
    private void handleChangeName(KeyEvent event){
        this.updateTxtPathFolder();
    }
    
    private void updateTxtPathFolder(){
        String folder = this.currentFolder != null ? this.currentFolder.getAbsolutePath() : "";
        this.txtPathFolder.setText(folder + File.separator + this.txtName.getText());
    }
}
