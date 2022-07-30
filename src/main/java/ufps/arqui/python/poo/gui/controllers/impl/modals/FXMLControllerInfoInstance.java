package ufps.arqui.python.poo.gui.controllers.impl.modals;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.controllers.impl.FXMLBaseController;
import ufps.arqui.python.poo.gui.models.AttrInstancia;
import ufps.arqui.python.poo.gui.models.MethodInstancia;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.views.impl.modals.ViewModalInfoInstance;

/**
 *
 * @author Sachikia
 */
public class FXMLControllerInfoInstance extends FXMLBaseController<ViewModalInfoInstance>{
    @FXML
    public Label lblName;
    @FXML
    public Label lblClass;

    @FXML public TableView<AttrInstancia> tableAttributes;

    @FXML public TableColumn<AttrInstancia, String> colAttr;

    @FXML public TableColumn<AttrInstancia, String> colValue;

    @FXML public TableColumn<AttrInstancia, String> colType;

    @FXML public TableView<MethodInstancia> tableMethods;

    @FXML public TableColumn<MethodInstancia, String> colMethod;

    @FXML public TableColumn<MethodInstancia, String> coLParams;

    public FXMLControllerInfoInstance(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }
    
}
