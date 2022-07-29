package ufps.arqui.python.poo.gui.views.impl.complements;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import ufps.arqui.python.poo.gui.models.MundoInstancia;
import ufps.arqui.python.poo.gui.views.impl.ViewBase;

/**
 *
 * @author Sachikia
 */
public class ViewPanelInstance extends ViewBase<BorderPane, MundoInstancia>{
    private Label lblName;
    
    private Label lblClass;
    
    private ContextMenu contextMenu;
    
    public ViewPanelInstance(){
        super();
    }

    @Override
    public void initialize() {
        super.initialize();
        
        super.root.setOnContextMenuRequested(event -> {
            this.contextMenu.show(super.root, event.getScreenX(), event.getScreenY());
        });
    }
    
    @Override
    public void preload(MundoInstancia object) {
        super.preload(object);
        
        this.lblName.setText(object.getName());
        this.lblClass.setText(object.getName_class());
    }
}
