package ufps.arqui.python.poo.gui.views.modals;

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.views.ViewBase;

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

    public ViewModalCreateProject() {
        super();
    }

    @Override
    public void initialize() {
        super.modal = new Stage();
        super.modal.setScene(new Scene(super.root));
        super.modal.setTitle(super.resources.getString("Modal.createProject"));
        super.modal.initModality(Modality.APPLICATION_MODAL);
        super.modal.initOwner(this.stage);
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

    public TextField getTxtName() {
        return txtName;
    }
    
    public TextField getTxtPython() {
        return txtPython;
    }
}
