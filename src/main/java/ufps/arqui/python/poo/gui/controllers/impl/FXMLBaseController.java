package ufps.arqui.python.poo.gui.controllers.impl;

import java.lang.reflect.Constructor;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.util.Callback;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.views.impl.ViewBase;

public class FXMLBaseController<T extends Parent, K extends ViewBase> {
    
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
    
    /**
     * Fabrica de controladores personalizada <br>
     * Con el fin poder inicializar cualquier controlador que extienda de
     * {@link FXMLBaseController} con parametros de manera mas sencilla
     * @return callback - Este callback es usado por JavaFX para instanciar el controlador
     * que se va asociar a la vista FXML correspondiente
     */
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

    /**
     * Inicializa la implementacion de vista correspondiente a este controlador
     * @param view 
     */
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
