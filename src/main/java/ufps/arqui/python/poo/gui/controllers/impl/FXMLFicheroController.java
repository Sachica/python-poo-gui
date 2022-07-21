package ufps.arqui.python.poo.gui.controllers.impl;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import ufps.arqui.python.poo.gui.models.Directorio;
import ufps.arqui.python.poo.gui.models.Fichero;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.views.impl.ViewFichero;

/**
 *
 * @author Sachikia
 */
public class FXMLFicheroController extends FXMLBaseController<BorderPane, ViewFichero>  implements Initializable{
    
    @FXML
    private TreeView<Fichero> treeView;
    
    public FXMLFicheroController(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.resources = resources;
        
        super.init(ViewFichero.class);
        
        Consumer<Fichero> consume = new Consumer<Fichero>() {
            @Override
            public void accept(Fichero t) {
                proyecto.obtenerClasesDesde(t);
            }
        };
        this.view.setTreeView(this.treeView, consume);
        
        this.proyecto.getDirectorioTrabajoProperty().addListener(new ChangeListener<Directorio>() {
            @Override
            public void changed(ObservableValue<? extends Directorio> observable, Directorio oldValue, Directorio newValue) {
                view.populateTreeView(newValue);
            }
        });
    }
}
