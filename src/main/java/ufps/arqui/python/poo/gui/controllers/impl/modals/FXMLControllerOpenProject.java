package ufps.arqui.python.poo.gui.controllers.impl.modals;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.controllers.impl.FXMLBaseController;
import ufps.arqui.python.poo.gui.exceptions.Exceptions;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.views.impl.modals.ViewModalOpenProject;

/**
 *
 * @author Sachikia
 */
public class FXMLControllerOpenProject extends FXMLBaseController<BorderPane, ViewModalOpenProject> implements Initializable{
    @FXML
    private TextField txtPathProject; 

    public FXMLControllerOpenProject(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.resources = resources;
        
        super.init(ViewModalOpenProject.class);
        
        super.view.setTxtPathProject(this.txtPathProject);
    }
    
    @FXML
    private void handleSearchFolder(ActionEvent event){
        this.view.showSearchFolder();
    }
   
    @FXML
    private void handleOpenProject(ActionEvent event){
        String directorio = this.txtPathProject.getText();
        
        try {
            if (directorio == null || directorio.trim().isEmpty()) {
                throw new Exceptions("El directorio del proyecto está vacio", null);
            }
            File dir = new File(directorio);
            if (dir.exists() && dir.isDirectory()) {
                this.proyecto.resetearProyecto();
                this.proyecto.setDirectorioRaiz(dir);
                this.proyecto.cargarProyecto(false);
            } else {
                throw new Exceptions("El directorio no existe", null);
            }
        }catch (Exceptions e){
//            super.showError(e);
        }
        
    }
}
