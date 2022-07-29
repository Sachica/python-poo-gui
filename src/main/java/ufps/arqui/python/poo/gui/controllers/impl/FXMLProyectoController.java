package ufps.arqui.python.poo.gui.controllers.impl;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.models.ClasePython;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.views.impl.ViewProject;

/**
 *
 * @author Sachikia
 */
public class FXMLProyectoController extends FXMLBaseController<ViewProject> {
    @FXML
    private Pane gridpane;
    
    @FXML
    private Canvas canvas;
    
    public FXMLProyectoController(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        
        this.view.setControllerFactory(super.getControllerFactory());
        this.view.setGridpane(this.gridpane);
        this.view.setCanvas(this.canvas);
        
        this.proyecto.getCurrentListClassesProperty().addListener(new ChangeListener<List<ClasePython>>() {
            @Override
            public void changed(ObservableValue<? extends List<ClasePython>> observable, List<ClasePython> oldValue, List<ClasePython> newValue) {
                view.drawClasses(newValue);
            }
        });
    }
}
