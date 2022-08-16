package ufps.arqui.python.poo.gui.views.modals;

import java.util.function.Consumer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
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
public class ViewModalInfoInstance extends ViewBase<VBox, MundoInstancia>{
    private Label lblName;
    
    private Label lblClass;

    private TableView<AttrInstancia> tableAttributes;

    private TableColumn<AttrInstancia, String> colAttr;

    private TableColumn<AttrInstancia, String> colValue;

    private TableColumn<AttrInstancia, String> colType;
    
    private TableColumn<AttrInstancia, String> colOwnerAttr;
    
    private TableView<AttrInstancia> tableCollection;

    private TableColumn<AttrInstancia, String> colCPosKey;

    private TableColumn<AttrInstancia, String> colCValue;

    private TableColumn<AttrInstancia, String> colCType;

    private TableView<MethodInstancia> tableMethods;

    private TableColumn<MethodInstancia, String> colMethod;

    private TableColumn<MethodInstancia, String> coLParams;

    private TableColumn<MethodInstancia, String> colOwnerMeth;
    
    private TableColumn<MethodInstancia, String> colInfo;
    
    //[Stage(Back Stage), ID_Reference(Key to Next Stage)]
    private Consumer<Object[]> onClickObject;
    
    private boolean wasMoved;
    
    private final Image imageRef;
    
    private final Image imageInfo;
    
    public ViewModalInfoInstance() {
        this.imageRef = new Image(getClass().getResourceAsStream(BluePyUtilities.REF_ARROW));
        this.imageInfo = new Image(getClass().getResourceAsStream(BluePyUtilities.INFO_ICON));
    }

    @Override
    public void initialize() {
        this.configColsAttributes();
        this.configColsCollection();
        this.configColsMethods();
        
        super.modal = new Stage();
        super.modal.setScene(new Scene(super.root));
    }
    
    @Override
    public void preload(MundoInstancia object) {
        this.lblName.setText(object.getName());
        this.lblClass.setText(object.getName_class());
        
        //Valida si es una clase que esta heredando de una colección y no una colección pura
        if(!object.getName_class().equals(object.getCollectionType())){
            object.getAttrs().forEach((clazz, list) -> this.tableAttributes.getItems().addAll(list));
            super.root.getChildren().add(this.tableAttributes);
        }
        
        if(object.getIsCollection()){
            this.configForCollection(object);
            object.getCollectionValues().forEach((list) -> this.tableCollection.getItems().addAll(list));
            super.root.getChildren().add(this.tableCollection);
        }
        
        object.getMethods().forEach((clazz, list) -> this.tableMethods.getItems().addAll(list));
        super.root.getChildren().add(this.tableMethods);
        
        super.modal.setTitle(object.getName());
    }
    
    public void setOnClickObject(Consumer<Object[]> onClickObject) {
        this.onClickObject = onClickObject;
    }
    
    private void handleClickedReference(AttrInstancia selected){
        if(selected != null && selected.getToReference()){
            this.onClickObject.accept(new Object[]{super.modal, selected.getValue()});
        }
    }
    
    private void handleClickedInfo(MethodInstancia method){
        if(method == null) return;
        
        String docs = method.getDocs();
        if(docs != null && !docs.isBlank() && !docs.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.NONE, docs, ButtonType.OK);
            alert.setTitle(method.getName());
            alert.show();
        }
    }
    
    private void configForCollection(MundoInstancia instance){
        switch(instance.getCollectionType()){
            case "list":
            case "tuple":
                this.colCPosKey.setText(super.resources.getString("ModalInfoInstance.tableIndex"));
            break;
            case "dict":
                this.colCPosKey.setText(super.resources.getString("ModalInfoInstance.tableKey"));
            break;
            case "set":
                this.tableCollection.getColumns().remove(this.colCPosKey);
        }
    }
    
    private void configColsCollection(){
        this.colCPosKey.setCellValueFactory(new PropertyValueFactory("key"));
        this.colCValue.setCellValueFactory(new PropertyValueFactory("value"));
        this.colCValue.setCellFactory(this.getCallbackCellFactoryForReferencedValue());
        this.colCType.setCellValueFactory(new PropertyValueFactory("type"));
    }
    
    private void configColsAttributes(){
        this.colAttr.setCellValueFactory(new PropertyValueFactory("key"));
        this.colValue.setCellValueFactory(new PropertyValueFactory("value"));
        this.colValue.setCellFactory(this.getCallbackCellFactoryForReferencedValue());
        
        this.colType.setCellValueFactory(new PropertyValueFactory("type"));
        this.colOwnerAttr.setCellValueFactory(new PropertyValueFactory("owner"));
    }
    
    private Callback getCallbackCellFactoryForReferencedValue(){
        return (param) ->  {
            TableCell<AttrInstancia, String> cell = new TableCell<>(){
                @Override
                protected void updateItem(String value, boolean empty) {
                    super.updateItem(value, empty);
                    if(value != null && !empty && getTableRow() != null
                            && getTableRow().getItem().getToReference()){
                        ImageView imgView = new ImageView(imageRef);
                        setText(null);
                        setGraphic(imgView);
                    }else{
                        setText(value);
                        setGraphic(null);
                    }
                }
            };

            cell.setOnMouseClicked(e -> handleClickedReference(cell.getTableRow().getItem()));

            return cell;
        };
    }
    
    private void configColsMethods(){
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
        
        this.colInfo.setCellValueFactory(new PropertyValueFactory("docs"));
        this.colInfo.setCellFactory(this.getCallbackCellFactoryForInfo());
    }
    
    private Callback getCallbackCellFactoryForInfo(){
        return (param) -> {
            TableCell<MethodInstancia, String> cell = new TableCell<>(){
                @Override
                protected void updateItem(String value, boolean empty) {
                    super.updateItem(value, empty);
                    if(value != null && !empty){
                        ImageView imgView = new ImageView(imageInfo);
                        imgView.setFitWidth(20);
                        imgView.setFitHeight(20);
                        setGraphic(imgView);
                    }else{
                        setGraphic(null);
                    }
                }
            };

            cell.setOnMouseClicked(e -> handleClickedInfo(cell.getTableRow().getItem()));

            return cell;
        };
    }
    
    public void setCurrentPosition(Stage preStage){
        if(!this.wasMoved){
            super.modal.setX(preStage.getX()+40);
            super.modal.setY(preStage.getY()+40);
            this.wasMoved = true;
        }
    }
}
