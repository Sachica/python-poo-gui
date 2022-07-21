package ufps.arqui.python.poo.gui.views.impl;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import ufps.arqui.python.poo.gui.controllers.impl.FXMLBaseController;
import ufps.arqui.python.poo.gui.models.ClasePython;
import ufps.arqui.python.poo.gui.utils.BluePyUtilities;
import ufps.arqui.python.poo.gui.views.IViewProject;

/**
 *
 * @author Sachikia
 */
public class ViewProject extends ViewBase<GridPane, Object> implements IViewProject{
    private Callback<Class<?>, Object> controllerFactory;

    public ViewProject(GridPane view, Stage stage, ResourceBundle resources) {
        super(view, stage, resources);
    }

    @Override
    public void drawClasses(List<ClasePython> classes){
        Platform.runLater(() -> {
            try{
                super.root.getChildren().clear();
                int i = 0, j = 0;
                for(ClasePython clasePython: classes){
                    Object objsClassPanel[] = BluePyUtilities.loadView(BluePyUtilities.COMPLEMENT_PANEL_CLASS, this.controllerFactory, this.resources);
                    
                    Parent classPanel = BluePyUtilities.get(Parent.class, objsClassPanel);
                    FXMLBaseController controller = BluePyUtilities.get(FXMLBaseController.class, objsClassPanel);
                    ((ViewBase)controller.getView()).preload(clasePython);
                    
                    super.root.add(classPanel, j, i);
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
    public void setControllerFactory(Callback<Class<?>, Object> controllerFactory) {
        this.controllerFactory = controllerFactory;
    }
}
