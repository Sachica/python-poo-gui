package ufps.arqui.python.poo.gui.controllers.impl.modals;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.controllers.impl.FXMLBaseController;
import ufps.arqui.python.poo.gui.exceptions.Exceptions;
import ufps.arqui.python.poo.gui.models.Proyecto;

/**
 *
 * @author Sachikia
 */
public class FXMLControllerOpenProject extends FXMLBaseController implements Initializable{
    private final DirectoryChooser folderChooser = new DirectoryChooser();
    
    @FXML
    private TextField txtPathProject; 

    public FXMLControllerOpenProject(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.resources = resources;
    }
    
    @FXML
    private void handleSearchFolder(ActionEvent event){
        final File selectedFolder = this.folderChooser.showDialog(this.stage);
        
        if(selectedFolder != null){
            this.txtPathProject.setText(selectedFolder.getAbsolutePath());
        }
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
            super.showError(e);
        }
        
    }
}
