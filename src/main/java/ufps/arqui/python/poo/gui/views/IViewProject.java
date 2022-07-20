package ufps.arqui.python.poo.gui.views;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import ufps.arqui.python.poo.gui.controllers.impl.complements.FXMLPanelClassController;
import ufps.arqui.python.poo.gui.models.ClasePython;

/**
 *
 * @author Sachikia
 */
public interface IViewProject {
    public void drawClasses(List<ClasePython> classes);
    
    public void setRoot(GridPane root);
    
    public void setControllerFactory(Callback<Class<?>, Object> controllerFactory);
    
    public void setResources(ResourceBundle resources);
}
