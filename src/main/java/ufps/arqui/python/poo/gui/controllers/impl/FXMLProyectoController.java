package ufps.arqui.python.poo.gui.controllers.impl;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.models.ClasePython;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.views.impl.ViewProject;

/**
 *
 * @author Sachikia
 */
public class FXMLProyectoController extends FXMLBaseController<GridPane, ViewProject> implements Initializable{
    
    public FXMLProyectoController(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.resources = resources;
        super.init(ViewProject.class);
        
        this.view.setResources(resources);
        this.view.setControllerFactory(super.getControllerFactory());
        
        this.proyecto.getCurrentListClassesProperty().addListener(new ChangeListener<List<ClasePython>>() {
            @Override
            public void changed(ObservableValue<? extends List<ClasePython>> observable, List<ClasePython> oldValue, List<ClasePython> newValue) {
                view.drawClasses(newValue);
            }
        });
    }
}
