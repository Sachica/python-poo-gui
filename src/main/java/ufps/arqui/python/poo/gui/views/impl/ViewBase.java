package ufps.arqui.python.poo.gui.views.impl;

import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 *
 * @author Sachikia
 * @param <T>
 * @param <K>
 */
public class ViewBase<T extends Parent, K extends Object> {
    protected Stage stage;
    protected ResourceBundle resources;
    protected T root;

    public ViewBase(T view, Stage stage, ResourceBundle resources) {
        this.root = view;
        this.stage = stage;
        this.resources = resources;
    }
    
    public void preload(K object){
    }
    
    public void reset(){
    }

    public T getRoot() {
        return root;
    }

    public void setRoot(T root) {
        this.root = root;
    }

    public ResourceBundle getResources() {
        return resources;
    }

    public void setResources(ResourceBundle resources) {
        this.resources = resources;
    }
}
