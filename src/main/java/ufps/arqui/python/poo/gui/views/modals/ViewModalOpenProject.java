package ufps.arqui.python.poo.gui.views.modals;

import java.io.File;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.exceptions.Exceptions;
import ufps.arqui.python.poo.gui.views.ViewBase;

/**
 *
 * @author Sachikia
 */
public class ViewModalOpenProject  extends ViewBase<BorderPane, Object>{
    private final DirectoryChooser folderChooser = new DirectoryChooser();
    
    private TextField txtPathProject; 
    
    public ViewModalOpenProject() {
        super();
    }

    @Override
    public void initialize() {
        super.modal = new Stage();
        super.modal.setScene(new Scene(super.root));
        super.modal.setTitle(super.resources.getString("Modal.openProject"));
        super.modal.initModality(Modality.APPLICATION_MODAL);
        super.modal.initOwner(this.stage);
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
    
    public File getValidatePathProject() throws Exceptions {
        String directorio = this.txtPathProject.getText();
        File pathProject;
        if (directorio == null || directorio.trim().isEmpty()) {
                throw new Exceptions("El directorio del proyecto está vacio", null);
            }
            pathProject = new File(directorio);
            if (!pathProject.exists() || !pathProject.isDirectory()) {
                throw new Exceptions("El directorio no existe", null);
            }
        return pathProject;
    }

    public void setTxtPathProject(TextField txtPathProject) {
        this.txtPathProject = txtPathProject;
    }
}
