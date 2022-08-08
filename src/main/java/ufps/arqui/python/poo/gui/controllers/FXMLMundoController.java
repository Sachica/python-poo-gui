package ufps.arqui.python.poo.gui.controllers;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.models.MundoInstancia;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.views.ViewMundo;

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
        
        super.proyecto.getCurrentInstancesProperty().addListener(new ChangeListener<Map<String, MundoInstancia>>() {
            @Override
            public void changed(ObservableValue<? extends Map<String, MundoInstancia>> arg0, Map<String, MundoInstancia> oldInstances, Map<String, MundoInstancia> newInstances) {
                view.populateMundo(newInstances);
            }
        });
    }
    
}
