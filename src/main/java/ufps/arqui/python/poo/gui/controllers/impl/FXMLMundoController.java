package ufps.arqui.python.poo.gui.controllers.impl;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.models.MundoInstancia;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.views.impl.ViewMundo;

/**
 *
 * @author Sachikia
 */
public class FXMLMundoController extends FXMLBaseController<ViewMundo>{
    
    @FXML
    private FlowPane paneInstances;
    
    public FXMLMundoController(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        
        super.proyecto.getCurrentInstancesProperty().addListener(new ChangeListener<MundoInstancia[]>() {
            @Override
            public void changed(ObservableValue<? extends MundoInstancia[]> observable, MundoInstancia[] oldValue, MundoInstancia[] newValue) {
                view.populateMundo(newValue);
            }
        });
    }
    
}
