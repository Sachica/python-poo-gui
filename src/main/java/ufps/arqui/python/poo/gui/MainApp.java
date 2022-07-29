package ufps.arqui.python.poo.gui;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.controllers.impl.FXMLBaseController;
import ufps.arqui.python.poo.gui.controllers.impl.FXMLFicheroController;
import ufps.arqui.python.poo.gui.controllers.impl.FXMLMenuController;
import ufps.arqui.python.poo.gui.controllers.impl.FXMLMundoController;
import ufps.arqui.python.poo.gui.controllers.impl.FXMLProyectoController;
import ufps.arqui.python.poo.gui.controllers.impl.FXMLTerminalController;
import ufps.arqui.python.poo.gui.models.Editor;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.utils.BluePyUtilities;
import ufps.arqui.python.poo.gui.utils.TerminalInteractiva;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Scene mainScene = this.getMainScene(stage);
        
        stage.setTitle("JavaFX and Maven");
        stage.setScene(mainScene);
        stage.show();
    }
    
    private Scene getMainScene(Stage stage) throws IOException{
        ResourceBundle resources = BluePyUtilities.getDefaultResources();
        
        Proyecto proyecto = new Proyecto(new TerminalInteractiva(), new Editor());
        
        FXMLBaseController baseController = new FXMLBaseController(stage, proyecto);
        
        //View Menu
        Object objMenu[] = BluePyUtilities.loadView(BluePyUtilities.VIEW_MENU, baseController.getControllerFactory(), resources);
        FXMLMenuController menuController = BluePyUtilities.get(FXMLMenuController.class, objMenu);
        Parent menuView = BluePyUtilities.get(Parent.class, objMenu);
        
        //View Fichero
        Object objFichero[] = BluePyUtilities.loadView(BluePyUtilities.VIEW_FICHERO, baseController.getControllerFactory(), resources);
        FXMLFicheroController ficheroController = BluePyUtilities.get(FXMLFicheroController.class, objFichero);
        Parent ficheroView = BluePyUtilities.get(Parent.class, objFichero);
        
        //View Project
        Object objProject[] = BluePyUtilities.loadView(BluePyUtilities.VIEW_PROYECTO, baseController.getControllerFactory(), resources);
        FXMLProyectoController proyectoController = BluePyUtilities.get(FXMLProyectoController.class, objProject);
        Parent proyectoView = BluePyUtilities.get(Parent.class, objProject);
        
        //View Mundo
        Object objMundo[] = BluePyUtilities.loadView(BluePyUtilities.VIEW_MUNDO, baseController.getControllerFactory(), resources);
        FXMLMundoController mundoController = BluePyUtilities.get(FXMLMundoController.class, objMundo);
        Parent mundoView = BluePyUtilities.get(Parent.class, objMundo);
        
        //View Terminal
        Object objTerminal[] = BluePyUtilities.loadView(BluePyUtilities.VIEW_TERMINAL, baseController.getControllerFactory(), resources);
        FXMLTerminalController terminalController = BluePyUtilities.get(FXMLTerminalController.class, objTerminal);
        Parent terminalView = BluePyUtilities.get(Parent.class, objTerminal);
        
        SplitPane ficheroProyecto = new SplitPane(ficheroView, proyectoView);
        ficheroProyecto.setDividerPositions(0.2);
        
        SplitPane mundoTerminal = new SplitPane(mundoView, terminalView);
        mundoTerminal.setDividerPositions(0.6);
        
        SplitPane mainSplitPane = new SplitPane(ficheroProyecto, mundoTerminal);
        mainSplitPane.setDividerPositions(0.65);
        mainSplitPane.setOrientation(Orientation.VERTICAL);
        
        BorderPane root = new BorderPane();
        root.setPrefSize(1110, 650);
        root.setTop(menuView);
        root.setCenter(mainSplitPane);
        
        return new Scene(root);
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
