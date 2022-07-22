package ufps.arqui.python.poo.gui.views.impl.complements;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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
    
    private double mouseAnchorX;
    private double mouseAnchorY;
    private double initialTranslateX;
    private double initialTranslateY;
    private DoubleProperty currentTranslateX = new SimpleDoubleProperty();
    private DoubleProperty currentTranslateY = new SimpleDoubleProperty();
    
    private ClasePython clasePython;
    
    private final List<ViewPanelClass> herencia = new ArrayList<>();
    
    public ViewPanelClass(BorderPane view, Stage stage, ResourceBundle resources) {
        super(view, stage, resources);
    }
    
    public void setLblName(Label lblName) {
        this.lblName = lblName;
    }

    public List<ViewPanelClass> getHerencia() {
        return herencia;
    }

    public void addHerencia(ViewPanelClass viewHerencia){
        this.herencia.add(viewHerencia);
    }
    
    @Override
    public void preload(ClasePython object) {
        this.clasePython = object;
                
        this.lblName.setText(this.clasePython.getNombre());
        
            this.initialTranslateX = this.clasePython.getPosicion().getX().get();
            this.initialTranslateY = this.clasePython.getPosicion().getY().get();
            this.currentTranslateX.setValue(this.initialTranslateX);
            this.currentTranslateY.setValue(this.initialTranslateY);
            
            super.root.setTranslateX(this.initialTranslateX);
            super.root.setTranslateY(this.initialTranslateY);
        
        this.clasePython.getPosicion().bindBidirectional(this.currentTranslateX, this.currentTranslateY);
    }
    
    public Node makeDraggable(Consumer<ViewPanelClass> onDraggable) {
        final Node node = (Node)super.root;
        final Group wrapGroup = new Group(node);
 
        ViewPanelClass myself = this;
        wrapGroup.addEventFilter(
                MouseEvent.ANY,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(final MouseEvent mouseEvent) {
                        mouseEvent.consume();
                    }
                });
 
        wrapGroup.addEventFilter(
                MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(final MouseEvent mouseEvent) {
                        mouseAnchorX = mouseEvent.getX();
                        mouseAnchorY = mouseEvent.getY();
                        initialTranslateX = node.getTranslateX();
                        initialTranslateY = node.getTranslateY();
                    }
                });

        wrapGroup.addEventFilter(
                MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(final MouseEvent mouseEvent) {
                        double currentX = initialTranslateX + mouseEvent.getX() - mouseAnchorX;
                        double currentY = initialTranslateY + mouseEvent.getY() - mouseAnchorY;
                        node.setTranslateX(currentX);
                        node.setTranslateY(currentY);
                        
                        onDraggable.accept(myself);
                        
                        currentTranslateX.setValue(currentX);
                        currentTranslateY.setValue(currentY);
                    }
                });
        return wrapGroup;
    }
    
    public void unbind(){
        this.clasePython.getPosicion().unbindBidirectional(this.currentTranslateX, this.currentTranslateY);
    }
}
