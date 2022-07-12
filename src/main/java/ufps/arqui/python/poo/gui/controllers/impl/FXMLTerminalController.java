package ufps.arqui.python.poo.gui.controllers.impl;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

/**
 *
 * @author Sachikia
 */
public class FXMLTerminalController implements Initializable{
    
    @FXML
    private TextField textInput;

    @FXML
    private VBox paneOutput;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    @FXML
    private void handleKeyPressed(KeyEvent event){
        System.out.println(event.getText());
    }
}
