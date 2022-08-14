package ufps.arqui.python.poo.gui.views.modals;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import ufps.arqui.python.poo.gui.models.AttrInstancia;
import ufps.arqui.python.poo.gui.models.MethodInstancia;
import ufps.arqui.python.poo.gui.models.MundoInstancia;
import ufps.arqui.python.poo.gui.utils.BluePyUtilities;
import ufps.arqui.python.poo.gui.views.ViewBase;

/**
 *
 * @author Sachikia
 */
public class ViewModalInfoInstance extends ViewBase<BorderPane, MundoInstancia>{
    private Label lblName;
    
    private Label lblClass;

    private TableView<AttrInstancia> tableAttributes;

    private TableColumn<AttrInstancia, String> colAttr;

    private TableColumn<AttrInstancia, String> colValue;

    private TableColumn<AttrInstancia, String> colType;
    
    private TableColumn<AttrInstancia, String> colOwnerAttr;

    private TableView<MethodInstancia> tableMethods;

    private TableColumn<MethodInstancia, String> colMethod;

    private TableColumn<MethodInstancia, String> coLParams;

    private TableColumn<MethodInstancia, String> colOwnerMeth;
    
    private Consumer<String> onClickObject;
    
    private final Image imageRef;
    
    public ViewModalInfoInstance() {
        this.imageRef = new Image(getClass().getResourceAsStream(BluePyUtilities.REF_ARROW));
    }

    @Override
    public void initialize() {
        this.colAttr.setCellValueFactory(new PropertyValueFactory("key"));
        this.colValue.setCellValueFactory(new PropertyValueFactory<AttrInstancia, String>("value"){
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AttrInstancia, String> param) {
                if(param.getValue().getToReference()){
                    return new SimpleObjectProperty(new ImageView(imageRef));
                }
                return new SimpleStringProperty(param.getValue().getValue());
            }
        });
        this.colType.setCellValueFactory(new PropertyValueFactory("type"));
        this.colOwnerAttr.setCellValueFactory(new PropertyValueFactory("owner"));
        
        this.colMethod.setCellValueFactory(new PropertyValueFactory("name"));
        this.colOwnerMeth.setCellValueFactory(new PropertyValueFactory("owner"));
        this.coLParams.setCellValueFactory(new PropertyValueFactory<MethodInstancia, String>("args"){
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<MethodInstancia, String> value) {
                String params = "(";
                for(int i = 0; i < value.getValue().getArgs().length; i++){
                    params += value.getValue().getArgs()[i];
                    params += (i+1 < value.getValue().getArgs().length) ? ", " : "";
                }
                params += ")";
                return new SimpleStringProperty(params);
            }
            
        });
        
        super.modal = new Stage();
        super.modal.setScene(new Scene(super.root));
    }
    
    @Override
    public void preload(MundoInstancia object) {
        this.lblName.setText(object.getName());
        this.lblClass.setText(object.getName_class());
        
        object.getAttrs().forEach((clazz, list) -> this.tableAttributes.getItems().addAll(list));
        object.getMethods().forEach((clazz, list) -> this.tableMethods.getItems().addAll(list));
        super.modal.setTitle(object.getName());
    }
    
    public void setOnClickObject(Consumer<String> onClickObject) {
        this.onClickObject = onClickObject;
        this.tableAttributes.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                if(!tableAttributes.getSelectionModel().isEmpty()){
                    AttrInstancia selected = tableAttributes.getSelectionModel().getSelectedItem();
                    if(selected.getToReference()){
                        onClickObject.accept(selected.getValue());
                    }
                }
            }
        });
    }
}
