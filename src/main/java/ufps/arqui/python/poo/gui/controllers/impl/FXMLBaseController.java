
package ufps.arqui.python.poo.gui.controllers.impl;

import java.lang.reflect.Constructor;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.util.Callback;
import ufps.arqui.python.poo.gui.exceptions.Exceptions;
import ufps.arqui.python.poo.gui.models.Proyecto;

public class FXMLBaseController {
    protected final Stage stage;
    protected final Proyecto proyecto;
    protected ResourceBundle resources;

    public FXMLBaseController(Stage stage, Proyecto proyecto) {
        this.stage = stage;
        this.proyecto = proyecto;
    }

    protected void showError(Exceptions e){
        Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
        alert.show();
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public Stage getStage() {
        return stage;
    }

    public ResourceBundle getResources() {
        return resources;
    }

    public void setResources(ResourceBundle resources) {
        this.resources = resources;
    }
    
    public Callback<Class<?>, Object> getControllerFactory(){
        return type -> {
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
    }
}
