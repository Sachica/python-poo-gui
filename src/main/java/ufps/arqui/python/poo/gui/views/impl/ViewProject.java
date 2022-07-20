package ufps.arqui.python.poo.gui.views.impl;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import ufps.arqui.python.poo.gui.controllers.impl.complements.FXMLPanelClassController;
import ufps.arqui.python.poo.gui.models.ClasePython;
import ufps.arqui.python.poo.gui.utils.BluePyUtilities;
import ufps.arqui.python.poo.gui.views.IViewProject;

/**
 *
 * @author Sachikia
 */
public class ViewProject implements IViewProject{
    @FXML
    private GridPane root;
    
    private Callback<Class<?>, Object> controllerFactory;
    
    private ResourceBundle resources;

    @Override
    public void drawClasses(List<ClasePython> classes){
        Platform.runLater(() -> {
            try{
                int i = 0, j = 0;
                for(ClasePython clasePython: classes){
                    Parent classPanel = BluePyUtilities.getView(BluePyUtilities.COMPLEMENT_PANEL_CLASS, this.controllerFactory, this.resources);
                    ((Label)classPanel.lookup("#lblName")).setText(clasePython.getNombre());
                    root.add(classPanel, j, i);
                    j++;
                    if(j==3){
                        j = 0;
                        i++;
                    }
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        });
    }

    @Override
    public void setRoot(GridPane root) {
        this.root = root;
    }
    
    @Override
    public void setControllerFactory(Callback<Class<?>, Object> controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    @Override
    public void setResources(ResourceBundle resources) {
        this.resources = resources;
    }
}
