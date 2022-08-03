package ufps.arqui.python.poo.gui.views;

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
    private Stage modalOpenProject;
    
    /**
     * Stage para cargar el FXML "CreateProject"
     */
    private Stage modalCreateProject;

    public ViewMenu() {
        super();
    }

    @Override
    public void initialize() {
        super.initialize();
        
        this.modalOpenProject = MainApp.getStageView(BluePyUtilities.MODAL_OPEN_PROJECT);
        this.modalCreateProject = MainApp.getStageView(BluePyUtilities.MODAL_CREATE_PROJECT);
        
        this.modalConfig(this.modalOpenProject, super.resources.getString("Modal.openProject"));
        this.modalConfig(this.modalCreateProject, super.resources.getString("Modal.createProject"));
    }
    
    public void showModalOpenProject(){
        this.modalOpenProject.show();
    }
    
    public void showModalCreateProject(){
        this.modalCreateProject.show();
    }
    
    private void modalConfig(Stage modal, String title){
        modal.setTitle(title);
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initOwner(this.stage);
    }
}
