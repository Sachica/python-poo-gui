package ufps.arqui.python.poo.gui.views.impl.modals;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import ufps.arqui.python.poo.gui.models.AttrInstancia;
import ufps.arqui.python.poo.gui.models.MethodInstancia;
import ufps.arqui.python.poo.gui.models.MundoInstancia;
import ufps.arqui.python.poo.gui.views.impl.ViewBase;

/**
 *
 * @author Sachikia
 */
public class ViewModalInfoInstance extends ViewBase<BorderPane, MundoInstancia>{
    public Label lblName;
    
    public Label lblClass;

    public TableView<AttrInstancia> tableAttributes;

    public TableColumn<AttrInstancia, String> colAttr;

    public TableColumn<AttrInstancia, String> colValue;

    public TableColumn<AttrInstancia, String> colType;

    public TableView<MethodInstancia> tableMethods;

    public TableColumn<MethodInstancia, String> colMethod;

    public TableColumn<MethodInstancia, String> coLParams;
    
    public ViewModalInfoInstance() {
        super();
    }

    @Override
    public void initialize() {
        this.colAttr.setCellValueFactory(new PropertyValueFactory("key"));
        this.colValue.setCellValueFactory(new PropertyValueFactory("value"));
        this.colType.setCellValueFactory(new PropertyValueFactory("type"));
        
        this.colMethod.setCellValueFactory(new PropertyValueFactory("name"));
        this.coLParams.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<MethodInstancia, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<MethodInstancia, String> param) {
                String params = "[", sep;
                for(int i = 0; i < param.getValue().getArgs().length; i++){
                    sep = "";
                    if(i+1 < param.getValue().getArgs().length){
                        sep = ", ";
                    }
                    params += param.getValue().getArgs()[i] + sep;
                }
                params += "]";
                return new SimpleObjectProperty<>(params);
            }
        });
    }
    
    @Override
    public void preload(MundoInstancia object) {
        this.lblName.setText(object.getName());
        this.lblClass.setText(object.getName_class());
        
        this.tableAttributes.getItems().addAll(object.getAttrs());
        this.tableMethods.getItems().addAll(object.getMethods());
    }
}
