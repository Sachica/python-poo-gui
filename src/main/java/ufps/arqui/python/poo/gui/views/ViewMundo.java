package ufps.arqui.python.poo.gui.views;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import ufps.arqui.python.poo.gui.controllers.FXMLBaseController;
import ufps.arqui.python.poo.gui.controllers.complements.FXMLPanelInstanceController;
import ufps.arqui.python.poo.gui.models.MundoInstancia;
import ufps.arqui.python.poo.gui.utils.BluePyUtilities;
import ufps.arqui.python.poo.gui.views.complements.ViewPanelInstance;
import ufps.arqui.python.poo.gui.views.modals.ViewModalInfoInstance;

/**
 *
 * @author Sachikia
 */
public class ViewMundo extends ViewBase<ScrollPane, Object> {
    
    private FlowPane paneInstances;
    
    private final Map<String, ViewModalInfoInstance> viewInstances = new HashMap<>();
    
    private Callback<Class<?>, Object> controllerFactory;
    
    //[Stage(Back Stage), ID_Reference(Key to Next Stage)]
    private Consumer<Object[]> onClickObject;

    public ViewMundo() {
        super();
        
        this.onClickObject = (object) -> {
            this.showModal((Stage)object[0], object[1].toString());
        };
    }

    @Override
    public void initialize() {
        super.initialize();
        
        this.root.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                paneInstances.setPrefWidth(newValue.doubleValue());
            }
        });
    }
    
    /**
     * Puebla el panel {@link ViewMundo#paneInstances} con las instancias pasadas como
     * parametro, cada una sera contenida por un objeto {@link ViewPanelInstance}
     * y añadida al panel
     * @param instances 
     */
    public void populateMundo(Map<String, MundoInstancia> instances) {
        Platform.runLater(() -> {
            this.clear();

            for (MundoInstancia instance : instances.values()) {
                try{
                    ViewModalInfoInstance modal;
                    if(instance.getIsDeclared()){
                        modal = this.forDeclaredInstance(instance);
                    }else{
                        modal = this.forNonDeclaredInstance(instance);
                    }
                    
                    modal.setOnClickObject(this.onClickObject);
                    this.viewInstances.put(instance.getId(), modal);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    
    private void clear(){
        for(ViewModalInfoInstance modal: this.viewInstances.values()){
            modal.showModal(false);
        }
        this.viewInstances.clear();
        this.paneInstances.getChildren().clear();
    }
    
    private ViewModalInfoInstance forDeclaredInstance(MundoInstancia instance) throws IOException{
        Object objsClassPanel[] = BluePyUtilities.loadView(BluePyUtilities.COMPLEMENT_PANEL_INSTANCE, this.controllerFactory, this.resources);

        FXMLPanelInstanceController controller = BluePyUtilities.get(FXMLPanelInstanceController.class, objsClassPanel);
        ViewPanelInstance view = (ViewPanelInstance)controller.getView();
        view.preload(instance);

        this.paneInstances.getChildren().add(view.getRoot());
        return view.getModalInfoInstance();
    }
    
    private ViewModalInfoInstance forNonDeclaredInstance(MundoInstancia instance) throws IOException{
        Object objInfoInstance[] = BluePyUtilities.loadView(BluePyUtilities.MODAL_INFO_INSTANCE, this.controllerFactory, resources);
            
        FXMLBaseController controller = BluePyUtilities.get(FXMLBaseController.class, objInfoInstance);
        ViewModalInfoInstance modalInfoInstance = (ViewModalInfoInstance)controller.getView();
        modalInfoInstance.preload(instance);
        
        return modalInfoInstance;
    }
    
    private void showModal(Stage preStage, String id){
        ViewModalInfoInstance modalInfo = this.viewInstances.get(id);
        modalInfo.setCurrentPosition(preStage);
        modalInfo.getModal().show();
        modalInfo.getModal().requestFocus();
    }
}
