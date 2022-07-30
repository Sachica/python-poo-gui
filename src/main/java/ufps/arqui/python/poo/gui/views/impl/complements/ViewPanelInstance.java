package ufps.arqui.python.poo.gui.views.impl.complements;

import java.io.IOException;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import ufps.arqui.python.poo.gui.controllers.impl.FXMLBaseController;
import ufps.arqui.python.poo.gui.models.MundoInstancia;
import ufps.arqui.python.poo.gui.utils.BluePyUtilities;
import ufps.arqui.python.poo.gui.views.impl.ViewBase;
import ufps.arqui.python.poo.gui.views.impl.modals.ViewModalInfoInstance;

/**
 *
 * @author Sachikia
 */
public class ViewPanelInstance extends ViewBase<BorderPane, MundoInstancia>{
    private Label lblName;
    
    private Label lblClass;
    
    private ContextMenu contextMenu;
    
    private final Stage modalInfoInstance = new Stage();
    
    private Callback<Class<?>, Object> controllerFactory;
    
    public ViewPanelInstance(){
        super();
    }

    @Override
    public void initialize() {
        super.initialize();
        
        super.root.setOnContextMenuRequested(event -> {
            this.contextMenu.show(super.root, event.getScreenX(), event.getScreenY());
        });
    }
    
    @Override
    public void preload(MundoInstancia object) {
        super.preload(object);
        
        this.lblName.setText(object.getName());
        this.lblClass.setText(object.getName_class());
        
        try{
            Object objInfoInstance[] = BluePyUtilities.loadView(BluePyUtilities.MODAL_INFO_INSTANCE, this.controllerFactory, resources);
            
            FXMLBaseController controller = BluePyUtilities.get(FXMLBaseController.class, objInfoInstance);
            ViewModalInfoInstance view = (ViewModalInfoInstance)controller.getView();
            view.preload(object);
            
            this.modalInfoInstance.setScene(new Scene(view.getRoot()));
            this.modalInfoInstance.setTitle("");
            this.modalInfoInstance.initModality(Modality.APPLICATION_MODAL);
            this.modalInfoInstance.initOwner(this.stage);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void showModalInfoInstance(){
        this.modalInfoInstance.show();
    }
}
