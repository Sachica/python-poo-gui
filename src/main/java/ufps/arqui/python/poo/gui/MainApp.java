package ufps.arqui.python.poo.gui;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.controllers.impl.FXMLFicheroController;
import ufps.arqui.python.poo.gui.controllers.impl.FXMLMenuController;
import ufps.arqui.python.poo.gui.controllers.impl.FXMLMundoController;
import ufps.arqui.python.poo.gui.controllers.impl.FXMLProyectoController;
import ufps.arqui.python.poo.gui.controllers.impl.FXMLTerminalController;
import ufps.arqui.python.poo.gui.controllers.impl.modals.FXMLControllerCreateProject;
import ufps.arqui.python.poo.gui.controllers.impl.modals.FXMLControllerOpenProject;
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
        
        //View Menu
        FXMLControllerOpenProject controllerModalOpenProject = new FXMLControllerOpenProject(stage, proyecto);
        Parent modalOpenProject = BluePyUtilities.getView(BluePyUtilities.MODAL_OPEN_PROJECT, controllerModalOpenProject, resources);
        
        FXMLControllerCreateProject controllerCreateProject = new FXMLControllerCreateProject(stage, proyecto);
        Parent modalCreateProject = BluePyUtilities.getView(BluePyUtilities.MODAL_CREATE_PROJECT, controllerCreateProject, resources);
        
        FXMLMenuController menuController = new FXMLMenuController(stage, proyecto);
        Parent menuView = BluePyUtilities.getView(BluePyUtilities.VIEW_MENU, menuController, resources);
        
        menuController.setModalOpenProject(modalOpenProject);
        menuController.setModalCreateProject(modalCreateProject);
        
        //View Fichero
        FXMLFicheroController ficheroController = new FXMLFicheroController(stage, proyecto);
        Parent ficheroView = BluePyUtilities.getView(BluePyUtilities.VIEW_FICHERO, ficheroController, resources);
        
        //View Project
        FXMLProyectoController proyectoController = new FXMLProyectoController(stage, proyecto);
        Parent proyectoView = BluePyUtilities.getView(BluePyUtilities.VIEW_PROYECTO, proyectoController, resources);
        
        //View Mundo
        FXMLMundoController mundoController = new FXMLMundoController(stage, proyecto);
        Parent mundoView = BluePyUtilities.getView(BluePyUtilities.VIEW_MUNDO, mundoController, resources);
        
        //View Terminal
        FXMLTerminalController terminalController = new FXMLTerminalController(stage, proyecto);
        Parent terminalView = BluePyUtilities.getView(BluePyUtilities.VIEW_TERMINAL, terminalController, resources);
        
        SplitPane ficheroProyecto = new SplitPane(ficheroView, proyectoView);
        ficheroProyecto.setDividerPositions(0.2);
        
        SplitPane mundoTerminal = new SplitPane(mundoView, terminalView);
        mundoTerminal.setDividerPositions(0.7);
        
        SplitPane mainSplitPane = new SplitPane(ficheroProyecto, mundoTerminal);
        mainSplitPane.setDividerPositions(0.7);
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
