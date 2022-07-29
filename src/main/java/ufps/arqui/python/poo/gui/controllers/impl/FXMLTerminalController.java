package ufps.arqui.python.poo.gui.controllers.impl;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.annotations.SharedView;
import ufps.arqui.python.poo.gui.exceptions.Exceptions;
import ufps.arqui.python.poo.gui.models.Mensaje;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.views.impl.ViewTerminal;

/**
 *
 * @author Sachikia
 */
public class FXMLTerminalController extends FXMLBaseController<ViewTerminal>{
    
    @FXML
    private TextField textInput;

    @FXML
    private ScrollPane scrollPaneOutput;
    
    @FXML
    private VBox paneOutput;
    
    @SharedView
    private Consumer<String> onEnterCommand;

    public FXMLTerminalController(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
        
        this.onEnterCommand = new Consumer<String>() {
            @Override
            public void accept(String command) {
                try{
                    proyecto.ingresarComandoTerminal(command);
                }catch(Exceptions e){
                }
            }
        };
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        
        this.proyecto.getTerminalInteractiva().getCurrentMessage().addListener(new ChangeListener<Mensaje>() {
            @Override
            public void changed(ObservableValue<? extends Mensaje> observable, Mensaje oldValue, Mensaje newValue) {
                if(!newValue.getTipo().esInterno()){
                    view.appendOutput(newValue);
                }
            }
        });
    }
}
