package ufps.arqui.python.poo.gui.controllers.modals;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.controllers.FXMLBaseController;
import ufps.arqui.python.poo.gui.models.AttrInstancia;
import ufps.arqui.python.poo.gui.models.MethodInstancia;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.views.modals.ViewModalInfoInstance;

/**
 *
 * @author Sachikia
 */
public class FXMLControllerInfoInstance extends FXMLBaseController<ViewModalInfoInstance>{
    @FXML
    private Label lblName;
    @FXML
    private Label lblClass;

    @FXML private TableView<AttrInstancia> tableAttributes;

    @FXML private TableColumn<AttrInstancia, String> colAttr;

    @FXML private TableColumn<AttrInstancia, String> colValue;

    @FXML private TableColumn<AttrInstancia, String> colType;
    
    @FXML private TableColumn<AttrInstancia, String> colOwnerAttr;
    
    @FXML private TableView<AttrInstancia> tableCollection;

    @FXML private TableColumn<AttrInstancia, String> colCPosKey;

    @FXML private TableColumn<AttrInstancia, String> colCValue;

    @FXML private TableColumn<AttrInstancia, String> colCType;

    @FXML private TableView<MethodInstancia> tableMethods;

    @FXML private TableColumn<MethodInstancia, String> colMethod;

    @FXML private TableColumn<MethodInstancia, String> coLParams;
    
    @FXML private TableColumn<MethodInstancia, String> colOwnerMeth;
    
    @FXML private TableColumn<MethodInstancia, String> colInfo;

    public FXMLControllerInfoInstance(Stage stage, Proyecto proyecto) {
        super(stage, proyecto);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }
    
}
