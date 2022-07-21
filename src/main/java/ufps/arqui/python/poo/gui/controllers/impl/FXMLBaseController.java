package ufps.arqui.python.poo.gui.controllers.impl;

import java.lang.reflect.Constructor;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.util.Callback;
import ufps.arqui.python.poo.gui.models.Proyecto;

public class FXMLBaseController<T, K> {

    protected final Stage stage;
    protected final Proyecto proyecto;

    @FXML
    protected T root;
    protected K view;

    protected ResourceBundle resources;

    public FXMLBaseController(Stage stage, Proyecto proyecto) {
        this.stage = stage;
        this.proyecto = proyecto;
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

    public K getView() {
        return view;
    }

    public Callback<Class<?>, Object> getControllerFactory() {
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

    public void init(Class<K> view) {
        try {
            for (Constructor c : view.getConstructors()) {
                if (c.getParameterCount() == 3) {
                    this.view = (K) c.newInstance(this.root, this.stage, this.resources);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
