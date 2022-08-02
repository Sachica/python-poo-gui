package ufps.arqui.python.poo.gui.views;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.MainApp;
import ufps.arqui.python.poo.gui.utils.BluePyUtilities;

/**
 *
 * @author Sachikia
 */
public class ViewMenu extends ViewBase<BorderPane, Object>{
    /**
     * Stage para cargar el FXML "OpenProject"
     */
    private final Stage modalOpenProject = new Stage();
    
    /**
     * Stage para cargar el FXML "CreateProject"
     */
    private final Stage modalCreateProject = new Stage();

    public ViewMenu() {
        super();
    }

    @Override
    public void initialize() {
        super.initialize();
        
        this.modalConfig(this.modalOpenProject, MainApp.getView(BluePyUtilities.MODAL_OPEN_PROJECT).getRoot(), super.resources.getString("Modal.openProject"));
        this.modalConfig(this.modalCreateProject, MainApp.getView(BluePyUtilities.MODAL_CREATE_PROJECT).getRoot(), super.resources.getString("Modal.createProject"));
    }
    
    public void showModalOpenProject(){
        this.modalOpenProject.show();
    }
    
    public void showModalCreateProject(){
        this.modalCreateProject.show();
    }
    
    private void modalConfig(Stage modal, Parent root, String title){
        modal.setScene(new Scene(root));
        modal.setTitle(title);
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initOwner(this.stage);
    }
}
