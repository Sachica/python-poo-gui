package ufps.arqui.python.poo.gui.controllers.impl;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import ufps.arqui.python.poo.gui.models.ClasePython;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.views.IViewProject;
import ufps.arqui.python.poo.gui.views.impl.ViewProject;

/**
 *
 * @author Sachikia
 */
public class FXMLProyectoController extends FXMLBaseController implements Initializable{
    private final IViewProject view = new ViewProject();
    
    @FXML
    private GridPane root;
    
    public FXMLProyectoController(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.resources = resources;
        
        final Callback<Class<?>, Object> controllerFactory = type -> {
            try {
                for (Constructor<?> c : type.getConstructors()) {
                    if (c.getParameterCount() == 2 && c.getParameterTypes()[0] == Stage.class
                            && c.getParameterTypes()[1] == Proyecto.class) {
                        return c.newInstance(this.stage, this.proyecto);
                    }
                }
                return type.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        
        this.view.setRoot(this.root);
        this.view.setResources(resources);
        this.view.setControllerFactory(controllerFactory);
        
        this.proyecto.getCurrentListClassesProperty().addListener(new ChangeListener<List<ClasePython>>() {
            @Override
            public void changed(ObservableValue<? extends List<ClasePython>> observable, List<ClasePython> oldValue, List<ClasePython> newValue) {
                view.drawClasses(newValue);
            }
        });
    }
}
