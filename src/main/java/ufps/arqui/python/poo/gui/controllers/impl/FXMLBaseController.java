package ufps.arqui.python.poo.gui.controllers.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.util.Callback;
import ufps.arqui.python.poo.gui.annotations.SharedView;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.views.impl.ViewBase;

public class FXMLBaseController<K extends ViewBase> implements Initializable {
    
    @SharedView
    protected final Stage stage;
    
    @SharedView
    protected final Proyecto proyecto;
    
    @SharedView
    protected ResourceBundle resources;
    
    @SharedView
    protected Callback<Class<?>, Object> controllerFactory;
    
    @FXML
    protected Object root;
    protected K view;

    public FXMLBaseController(Stage stage, Proyecto proyecto) {
        this.stage = stage;
        this.proyecto = proyecto;
        
        this.controllerFactory = (type) -> {
            try {
                for (Constructor<?> c : type.getConstructors()) {
                    //Busca el constructor adecuado por si llega a existir otro(aunque lo dudo)
                    //el controlador adecuado consta de dos argumentos de tipos Stage.class
                    //y Proyecto.class
                    if (c.getParameterCount() == 2 && c.getParameterTypes()[0] == Stage.class
                            && c.getParameterTypes()[1] == Proyecto.class) {
                        return c.newInstance(this.stage, this.proyecto);
                    }
                }
                return type.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public Stage getStage() {
        return stage;
    }

    public ResourceBundle getResources() {
        return resources;
    }

    public void setResources(ResourceBundle resources) {
        this.resources = resources;
    }

    public K getView() {
        return view;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        this.init();
    }
    
    /**
     * Fabrica de controladores personalizada <br>
     * Con el fin poder inicializar cualquier controlador que extienda de
     * {@link FXMLBaseController} con parametros de manera mas sencilla
     * @return callback - Callback usado por JavaFX para instanciar el controlador
     * que se va asociar a la vista FXML correspondiente
     */
    public Callback<Class<?>, Object> getControllerFactory() {
        return this.controllerFactory;
    }

    /**
     * Inicializa la implementacion de vista correspondiente a este controlador
     */
    private void init() {
        try {
            //Instancia la vista correspondiente al tipo generico K
            this.view = (K) ((Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]).newInstance();
            
            //Une los campos de la clase base y subclase
            List<Field> fields = new ArrayList<>();
            fields.addAll(Arrays.asList(getClass().getDeclaredFields()));
            fields.addAll(Arrays.asList(getClass().getSuperclass().getDeclaredFields()));
            
            //Inyección de atributos del controlador a la vista correspondiente
            //mediante java reflect
            for(Field field: fields){
                field.setAccessible(true);
                for(Annotation anotation: field.getAnnotations()){
                    
                    //Si el atributo esta anotado con las anotaciones FXML y SharedView
                    //es probable que la view tenga estos mismos atributos
                    if(anotation instanceof FXML || anotation instanceof SharedView){
                        Field viewField = this.getFieldView(field.getName());
                        if(viewField != null){
                            viewField.setAccessible(true);
                            viewField.set(this.view, field.get(this));
                        }
                    }
                }
            }
            this.view.initialize();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Obtiene el campo de la vista correspondiente, se atrapa la excepción por si el
     * campo no llega a estar en la clase base o subclase
     * @param fieldName
     * @return 
     */
    private Field getFieldView(String fieldName){
        try{
            return this.view.getClass().getDeclaredField(fieldName);
        }catch(Exception e){}
        
        try{
            return this.view.getClass().getSuperclass().getDeclaredField(fieldName);
        }catch(Exception e){}
        
        return null;
    }
}
