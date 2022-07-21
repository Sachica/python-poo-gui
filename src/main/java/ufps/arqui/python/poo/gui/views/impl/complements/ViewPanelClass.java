package ufps.arqui.python.poo.gui.views.impl.complements;

import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.models.ClasePython;
import ufps.arqui.python.poo.gui.views.IViewPanelClass;
import ufps.arqui.python.poo.gui.views.impl.ViewBase;

/**
 *
 * @author Sachikia
 */
public class ViewPanelClass extends ViewBase<BorderPane, ClasePython>implements IViewPanelClass{
    private Label lblName;

    public ViewPanelClass(BorderPane view, Stage stage, ResourceBundle resources) {
        super(view, stage, resources);
    }
    
    public void setLblName(Label lblName) {
        this.lblName = lblName;
    }

    @Override
    public void preload(ClasePython object) {
        this.lblName.setText(object.getNombre());
    }
}
