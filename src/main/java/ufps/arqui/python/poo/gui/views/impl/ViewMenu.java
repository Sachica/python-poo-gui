package ufps.arqui.python.poo.gui.views.impl;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import ufps.arqui.python.poo.gui.utils.BluePyUtilities;

/**
 *
 * @author Sachikia
 */
public class ViewMenu extends ViewBase<BorderPane, Object>{
    private Callback<Class<?>, Object> controllerFactory;
    
    /**
     * Stage para cargar el FXML "OpenProject"
     */
    private final Stage modalOpenProject = new Stage();
    
    /**
     * Stage para cargar el FXML "CreateProject"
     */
    private final Stage modalCreateProject = new Stage();

    public ViewMenu(BorderPane view, Stage stage, ResourceBundle resources) {
        super(view, stage, resources);
    }

    @Override
    public void preload(Object object) {
        try{
            Object objModalOpenProject[] = BluePyUtilities.loadView(BluePyUtilities.MODAL_OPEN_PROJECT, this.controllerFactory, resources);
            Parent modalOpenProject = BluePyUtilities.get(Parent.class, objModalOpenProject);
            Object objModalCreateProject[] = BluePyUtilities.loadView(BluePyUtilities.MODAL_CREATE_PROJECT, this.controllerFactory, resources);
            Parent modalCreateProject = BluePyUtilities.get(Parent.class, objModalCreateProject);
            
            this.modalConfig(this.modalOpenProject, modalOpenProject, super.resources.getString("Modal.openProject"));
            this.modalConfig(this.modalCreateProject, modalCreateProject, super.resources.getString("Modal.createProject"));
        }catch(IOException e){
            e.printStackTrace();
        }
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

    public Callback<Class<?>, Object> getControllerFactory() {
        return controllerFactory;
    }

    public void setControllerFactory(Callback<Class<?>, Object> controllerFactory) {
        this.controllerFactory = controllerFactory;
    }
}
