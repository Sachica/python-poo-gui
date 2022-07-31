package ufps.arqui.python.poo.gui.views;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import ufps.arqui.python.poo.gui.controllers.FXMLBaseController;
import ufps.arqui.python.poo.gui.models.ClasePython;
import ufps.arqui.python.poo.gui.utils.BluePyUtilities;
import ufps.arqui.python.poo.gui.views.complements.ViewPanelClass;

/**
 *
 * @author Sachikia
 */
public class ViewProject extends ViewBase<ScrollPane, Object>{
    private Pane gridpane;
        
    private Canvas canvas;
    
    private Callback<Class<?>, Object> controllerFactory;
    
    private final Map<String, ViewPanelClass> panels = new HashMap<>();

    public ViewProject() {
        super();
    }

    public void drawClasses(List<ClasePython> classes){
        Platform.runLater(() -> {
            try{
                for(ViewPanelClass base: this.panels.values()){
                    base.unbind();
                }
                this.panels.clear();
                this.gridpane.getChildren().clear();
                
                Consumer<ViewPanelClass> onDraggable = viewPanelClass -> {
                    this.drawClassLines();
                };
                
                double width = 140.0, height = 80.0, dGap = 20.0, count = 3;
                double sumWidth = 0.0, sumHeight = 0.0, gap;
                String key = "";
                for(ClasePython clasePythonBase: classes){
                    key = clasePythonBase.getPathModule()+clasePythonBase.getNombre();
                    
                    gap = !this.panels.containsKey(key) ? dGap : 0;
                    ViewPanelClass base = this.loadPanelClass(clasePythonBase, onDraggable, key, sumWidth, sumHeight);
                    
                    sumWidth += gap!=0 ? width + gap : 0;
                    if(sumWidth > (width*count) + (dGap * count)){
                        sumWidth = 0;
                        sumHeight += height + dGap;
                    }
                    
                    for(ClasePython clasePythonHerencia: clasePythonBase.getHerencia()){
                        key = clasePythonHerencia.getPathModule()+clasePythonHerencia.getNombre();
                        
                        gap = !this.panels.containsKey(key) ? dGap : 0;
                        ViewPanelClass herencia = this.loadPanelClass(clasePythonHerencia, onDraggable, key, sumWidth, sumHeight);
                        
                        sumWidth += gap!=0 ? width + gap : 0;
                        if(sumWidth > (width*count) + (dGap * count)){
                            sumWidth = 0;
                            sumHeight += height + dGap;
                        }
                        
                        base.addHerencia(herencia);
                    }
                }
                this.drawClassLines();
            }catch(IOException e){
                e.printStackTrace();
            }
        });
    }
    
    private ViewPanelClass loadPanelClass(ClasePython clasePython, Consumer<ViewPanelClass> onDraggable, String key, double sumWidth, double sumHeight) throws IOException{
        if(!this.panels.containsKey(key)){
            if(clasePython.getPosicion().isUnassigned()){
                clasePython.getPosicion().setPosicion(sumWidth, sumHeight);
            }
            
            Object objsClassPanel[] = BluePyUtilities.loadView(BluePyUtilities.COMPLEMENT_PANEL_CLASS, this.controllerFactory, this.resources);
        
            FXMLBaseController controller = BluePyUtilities.get(FXMLBaseController.class, objsClassPanel);
            ViewPanelClass view = (ViewPanelClass)controller.getView();
            view.preload(clasePython);
            
            this.panels.put(key, view);
            this.gridpane.getChildren().add(view.makeDraggable(onDraggable));
        }
                    
        return this.panels.get(key);
    }
    
    private void drawClassLines(){
        Platform.runLater(() -> {
            GraphicsContext gc = this.canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, this.gridpane.widthProperty().get(), this.gridpane.heightProperty().get());
            for(ViewPanelClass base: this.panels.values()){
                Bounds bd = ((Node)base.getRoot()).getBoundsInParent();
                double x1 = bd.getMinX() + (bd.getWidth() / 2);
                double y1 = bd.getMinY() + (bd.getHeight()/ 2);

                for(ViewPanelClass herencia: base.getHerencia()){
                    Bounds bh = ((Node)herencia.getRoot()).getBoundsInParent();
                    double x2 = bh.getMinX() + (bh.getWidth() / 2);
                    double y2 = bh.getMinY() + (bh.getHeight()/ 2);
                    
                    gc.strokeLine(x1, y1, x2, y2);
                    
                    double x = (x1 + x2) / 2;
                    double y = (y1 + y2) / 2;
                    drawArrowRelationship(gc, x1, y1, x, y, 15, 15);
                }
            }
        });
    }
    
    /**
     * Draw an arrow line between two points.
     *
     * @param g the graphics component.
     * @param x1 x-position of first point.
     * @param y1 y-position of first point.
     * @param x2 x-position of second point.
     * @param y2 y-position of second point.
     * @param d the width of the arrow.
     * @param h the height of the arrow.
     */
    private void drawArrowRelationship(GraphicsContext gc, double x1, double y1, double x2, double y2, double d, double h) {
        double dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;

        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;

        double [] xpoints = {x2, xm, xn};
        double [] ypoints = {y2, ym, yn};

        gc.fillPolygon(xpoints, ypoints, 3);
    }
    
    @Override
    public void initialize() {
        super.initialize();
        
        this.canvas.widthProperty().bind(this.gridpane.widthProperty());
        this.canvas.heightProperty().bind(this.gridpane.heightProperty());
    }
}
