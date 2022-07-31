package ufps.arqui.python.poo.gui.views;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.util.Callback;
import ufps.arqui.python.poo.gui.controllers.complements.FXMLPanelInstanceController;
import ufps.arqui.python.poo.gui.models.MundoInstancia;
import ufps.arqui.python.poo.gui.utils.BluePyUtilities;
import ufps.arqui.python.poo.gui.views.complements.ViewPanelInstance;

/**
 *
 * @author Sachikia
 */
public class ViewMundo extends ViewBase<ScrollPane, Object> {
    
    private FlowPane paneInstances;
    
    private Callback<Class<?>, Object> controllerFactory;

    public ViewMundo() {
        super();
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
    public void populateMundo(MundoInstancia[] instances) {
        Platform.runLater(() -> {
            this.paneInstances.getChildren().clear();

            for (MundoInstancia instance : instances) {
                try{
                    Object objsClassPanel[] = BluePyUtilities.loadView(BluePyUtilities.COMPLEMENT_PANEL_INSTANCE, this.controllerFactory, this.resources);

                    FXMLPanelInstanceController controller = BluePyUtilities.get(FXMLPanelInstanceController.class, objsClassPanel);
                    ViewPanelInstance view = (ViewPanelInstance)controller.getView();
                    view.preload(instance);

                    this.paneInstances.getChildren().add(view.getRoot());
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
