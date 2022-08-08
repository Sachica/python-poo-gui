package ufps.arqui.python.poo.gui.views.modals;

import java.util.function.Consumer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import ufps.arqui.python.poo.gui.models.AttrInstancia;
import ufps.arqui.python.poo.gui.models.MethodInstancia;
import ufps.arqui.python.poo.gui.models.MundoInstancia;
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

    private TableView<MethodInstancia> tableMethods;

    private TableColumn<MethodInstancia, String> colMethod;

    private TableColumn<MethodInstancia, String> coLParams;
    
    private Consumer<String> onClickObject;
    
    public ViewModalInfoInstance() {
        super();
    }

    @Override
    public void initialize() {
        this.colAttr.setCellValueFactory(new PropertyValueFactory("key"));
        this.colValue.setCellValueFactory(new PropertyValueFactory("value"));
        this.colType.setCellValueFactory(new PropertyValueFactory("type"));
        
        this.colMethod.setCellValueFactory(new PropertyValueFactory("name"));
        
        //Los parametros vienen en una lista por lo tanto se personaliza la 
        //celda para mostrar los valores(String) dentro de la lista
        this.coLParams.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<MethodInstancia, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<MethodInstancia, String> param) {
                String params = "(";
                for(int i = 0; i < param.getValue().getArgs().length; i++){
                    params += param.getValue().getArgs()[i];
                    params += (i+1 < param.getValue().getArgs().length) ? ", " : "";
                }
                params += ")";
                return new SimpleObjectProperty<>(params);
            }
        });
        
        super.modal = new Stage();
        super.modal.setScene(new Scene(super.root));
    }
    
    @Override
    public void preload(MundoInstancia object) {
        this.lblName.setText(object.getName());
        this.lblClass.setText(object.getName_class());
        
        this.tableAttributes.getItems().addAll(object.getAttrs());
        this.tableMethods.getItems().addAll(object.getMethods());
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
