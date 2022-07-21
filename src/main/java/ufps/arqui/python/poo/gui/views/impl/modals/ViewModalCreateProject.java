package ufps.arqui.python.poo.gui.views.impl.modals;

import java.io.File;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.views.impl.ViewBase;

/**
 *
 * @author Sachikia
 */
public class ViewModalCreateProject extends ViewBase<BorderPane, Object>{
    @FXML
    private TextField txtPathFolder;
    
    @FXML
    private TextField txtName;
    
    @FXML
    private TextField txtPython;
    
    private final DirectoryChooser folderChooser = new DirectoryChooser();
    
    private File currentFolder;

    public ViewModalCreateProject(BorderPane view, Stage stage, ResourceBundle resources) {
        super(view, stage, resources);
    }

    public void showSearchFolder() {
        this.currentFolder = this.folderChooser.showDialog(super.stage);
        
        if(this.currentFolder != null){
            this.updateTxtPathFolder();
        }
    }
    
    public void updateTxtPathFolder(){
        String folder = this.currentFolder != null ? this.currentFolder.getAbsolutePath() : "";
        this.txtPathFolder.setText(folder + File.separator + this.txtName.getText());
    }
    
    public TextField getTxtPathFolder() {
        return txtPathFolder;
    }

    public void setTxtPathFolder(TextField txtPathFolder) {
        this.txtPathFolder = txtPathFolder;
    }

    public TextField getTxtName() {
        return txtName;
    }

    public void setTxtName(TextField txtName) {
        this.txtName = txtName;
    }

    public TextField getTxtPython() {
        return txtPython;
    }

    public void setTxtPython(TextField txtPython) {
        this.txtPython = txtPython;
    }
}
