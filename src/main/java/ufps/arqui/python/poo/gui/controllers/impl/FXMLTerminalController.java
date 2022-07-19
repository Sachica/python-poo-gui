package ufps.arqui.python.poo.gui.controllers.impl;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.models.Proyecto;

/**
 *
 * @author Sachikia
 */
public class FXMLTerminalController extends FXMLBaseController implements Initializable{
    
    @FXML
    private TextField textInput;

    @FXML
    private VBox paneOutput;

    public FXMLTerminalController(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            this.init(location, resources);
        });
    }
    
    private void init(URL location, ResourceBundle resources){
        System.out.println(textInput);
    }
    
    @FXML
    private void handleKeyPressed(KeyEvent event){
        System.out.println(event.getText());
    }
}
