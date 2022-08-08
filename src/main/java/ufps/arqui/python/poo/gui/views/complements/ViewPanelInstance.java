package ufps.arqui.python.poo.gui.views.complements;

import java.io.IOException;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import ufps.arqui.python.poo.gui.controllers.FXMLBaseController;
import ufps.arqui.python.poo.gui.models.MundoInstancia;
import ufps.arqui.python.poo.gui.utils.BluePyUtilities;
import ufps.arqui.python.poo.gui.views.ViewBase;
import ufps.arqui.python.poo.gui.views.modals.ViewModalInfoInstance;

/**
 *
 * @author Sachikia
 */
public class ViewPanelInstance extends ViewBase<BorderPane, MundoInstancia>{
    private Label lblName;
    
    private Label lblClass;
    
    private ContextMenu contextMenu;
        
    private Callback<Class<?>, Object> controllerFactory;
    
    private ViewModalInfoInstance modalInfoInstance;
    
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
            this.modalInfoInstance = (ViewModalInfoInstance)controller.getView();
            this.modalInfoInstance.preload(object);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void showModalInfoInstance(){
        this.modalInfoInstance.showModal(true);
    }

    public ViewModalInfoInstance getModalInfoInstance() {
        return modalInfoInstance;
    }
}
