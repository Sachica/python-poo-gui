package ufps.arqui.python.poo.gui.views;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.MainApp;
import ufps.arqui.python.poo.gui.utils.BluePyUtilities;

/**
 *
 * @author Sachikia
 */
public class ViewMenu extends ViewBase<BorderPane, Object>{
    public ViewMenu() {
        super();
    }

    @Override
    public void initialize() {
        super.initialize();
    }
    
    public void showModalOpenProject(){
        MainApp.getView(BluePyUtilities.MODAL_OPEN_PROJECT).showModal(true);
    }
    
    public void showModalCreateProject(){
        MainApp.getView(BluePyUtilities.MODAL_CREATE_PROJECT).showModal(true);
    }
}
