package ufps.arqui.python.poo.gui.utils;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author Sachikia
 */
public class BluePyUtilities {
    private static final String RESOURCES_LOCATION = "strings/";
    
    public  static final String RESOURCES_ES = RESOURCES_LOCATION + "strings_es";
    public  static final String RESOURCES_EN = RESOURCES_LOCATION + "strings_en";
    
    
    private static final String FXML_LOCATION = "/fxml/";
    private static final String FXML_MODAL_LOCATION = FXML_LOCATION + "modals/";
    private static final String FXML_COMPLEMENTS_LOCATION = FXML_LOCATION + "complements/";
    
    public static final String VIEW_MENU = FXML_LOCATION + "PanelMenu.fxml";
    public static final String VIEW_FICHERO = FXML_LOCATION + "PanelFichero.fxml";
    public static final String VIEW_PROYECTO = FXML_LOCATION + "PanelProyecto.fxml";
    public static final String VIEW_MUNDO = FXML_LOCATION + "PanelMundo.fxml";
    public static final String VIEW_TERMINAL = FXML_LOCATION + "PanelTerminal.fxml";
    
    public final static String MODAL_OPEN_PROJECT = FXML_MODAL_LOCATION + "OpenProject.fxml";
    public final static String MODAL_CREATE_PROJECT = FXML_MODAL_LOCATION + "CreateProject.fxml";
    
    public final static String COMPLEMENT_PANEL_CLASS = FXML_COMPLEMENTS_LOCATION + "PanelClass.fxml";
    
    public final static String PYTHON_FILE_LOGO = "/python_logo.png";
    
    public static Parent getView(String fxml, Object controller, ResourceBundle resources) throws IOException{
        FXMLLoader loader = new FXMLLoader(BluePyUtilities.class.getResource(fxml), resources);
        
        if(controller instanceof Callback) loader.setControllerFactory((Callback<Class<?>, Object>)controller);
        else loader.setController(controller);
        
        Parent root = loader.load();
        
        return root;
    }
    
    public static ResourceBundle getResource(String resourcesName){
        return ResourceBundle.getBundle(resourcesName);
    }
    
    public static ResourceBundle getDefaultResources(){
        return BluePyUtilities.getResource(BluePyUtilities.RESOURCES_ES);
    }
}
