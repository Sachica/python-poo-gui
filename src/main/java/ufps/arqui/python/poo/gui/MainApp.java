package ufps.arqui.python.poo.gui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import ufps.arqui.python.poo.gui.controllers.FXMLBaseController;
import ufps.arqui.python.poo.gui.models.Editor;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.utils.BluePyUtilities;
import ufps.arqui.python.poo.gui.utils.TerminalInteractiva;
import ufps.arqui.python.poo.gui.views.ViewBase;


public class MainApp extends Application {
    private static final Map<String, ViewBase> VIEWS = new HashMap<>();

    @Override
    public void start(Stage stage) throws Exception {
        Scene mainScene = this.getMainScene(stage);
        
        stage.setTitle("Blue Py");
        stage.setScene(mainScene);
        stage.setMaximized(true);
        stage.show();
    }
    
    private Scene getMainScene(Stage stage) throws IOException{
        ResourceBundle resources = BluePyUtilities.getDefaultResources();
        
        Proyecto proyecto = new Proyecto(new TerminalInteractiva(), new Editor());
        
        FXMLBaseController baseController = new FXMLBaseController(stage, proyecto);
        Callback controllerFactory = baseController.getControllerFactory();
        
        //-----------------------------MODALS-------------------------------------
        //Modal Open Project
        this.loadView(BluePyUtilities.MODAL_OPEN_PROJECT, controllerFactory, resources);
        
        //Modal Create Project
        this.loadView(BluePyUtilities.MODAL_CREATE_PROJECT, controllerFactory, resources);

        //-----------------------------VIEWS-------------------------------------
        //View Menu
        ViewBase viewMenu = this.loadView(BluePyUtilities.VIEW_MENU, controllerFactory, resources);
        
        //View Fichero
        ViewBase viewFichero = this.loadView(BluePyUtilities.VIEW_FICHERO, controllerFactory, resources);
        
        //View Project
        ViewBase viewProyecto = this.loadView(BluePyUtilities.VIEW_PROYECTO, controllerFactory, resources);
        
        //View Mundo
        ViewBase viewMundo = this.loadView(BluePyUtilities.VIEW_MUNDO, controllerFactory, resources);
        
        //View Terminal
        ViewBase viewTerminal = this.loadView(BluePyUtilities.VIEW_TERMINAL, controllerFactory, resources);
        
        SplitPane ficheroProyecto = new SplitPane(viewFichero.getRoot(), viewProyecto.getRoot());
        ficheroProyecto.setDividerPositions(0);
        
        SplitPane mundoTerminal = new SplitPane(viewMundo.getRoot(), viewTerminal.getRoot());
        mundoTerminal.setDividerPositions(0.7);
        
        SplitPane mainSplitPane = new SplitPane(ficheroProyecto, mundoTerminal);
        mainSplitPane.setDividerPositions(0.8);
        mainSplitPane.setOrientation(Orientation.VERTICAL);
        
        BorderPane root = new BorderPane();
        root.setTop(viewMenu.getRoot());
        root.setCenter(mainSplitPane);
        
        return new Scene(root);
    }
    
    private ViewBase loadView(String view, Callback controllerFactory, ResourceBundle resources) throws IOException{
        Object objMenu[] = BluePyUtilities.loadView(view, controllerFactory, resources);
        FXMLBaseController controller = BluePyUtilities.get(FXMLBaseController.class, objMenu);
        this.addView(view, controller.getView());
        
        return controller.getView();
    }
    
    private void addView(String viewKey, ViewBase view){
        VIEWS.put(viewKey, view);
    }
    
    public static ViewBase getView(String viewKey){
        return VIEWS.get(viewKey);
    }
    
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
