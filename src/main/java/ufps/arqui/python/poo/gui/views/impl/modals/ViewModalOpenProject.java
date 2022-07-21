package ufps.arqui.python.poo.gui.views.impl.modals;

import java.io.File;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.views.impl.ViewBase;

/**
 *
 * @author Sachikia
 */
public class ViewModalOpenProject  extends ViewBase<BorderPane, Object>{
    private final DirectoryChooser folderChooser = new DirectoryChooser();
    
    @FXML
    private TextField txtPathProject; 
    
    public ViewModalOpenProject(BorderPane view, Stage stage, ResourceBundle resources) {
        super(view, stage, resources);
    }
    
    public void showSearchFolder(){
        final File selectedFolder = this.folderChooser.showDialog(this.stage);
        
        if(selectedFolder != null){
            this.txtPathProject.setText(selectedFolder.getAbsolutePath());
        }
    }
    
    public void setTextTxtPathProject(String textPathProject) {
        this.txtPathProject.setText(textPathProject);
    }

    public TextField getTxtPathProject() {
        return txtPathProject;
    }

    public void setTxtPathProject(TextField txtPathProject) {
        this.txtPathProject = txtPathProject;
    }
}
