package ufps.arqui.python.poo.gui.views.impl.modals;

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
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

    public ViewModalCreateProject() {
        super();
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
