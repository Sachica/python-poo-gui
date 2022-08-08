package ufps.arqui.python.poo.gui.views;

import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.controllers.FXMLBaseController;

/**
 *
 * @author Sachikia
 * @param <T>
 * @param <K>
 */
public class ViewBase<T extends Parent, K extends Object> {
    protected Stage stage;
    protected Stage modal;
    protected ResourceBundle resources;
    protected T root;

    public ViewBase() {
    }
    
    /**
     * Este metodo sera llamado despues de inicializar cualquier vista que extienda de {@link ViewBase}
     * en su correspondiente controlador por el metodo {@link FXMLBaseController#init()}.<br>
     * Su proposito es que una vez se instancie la vista e inyecten los atributos 
     * del controlador esta pase a realizar la configuracion necesaria para su funcionamiento
     */
    public void initialize(){
    }
    
    /**
     * Metodo que recibe como argumento un objeto con el cual se desea mostrar alguna información
     * en los componentes de la vista actual
     * @param object 
     */
    public void preload(K object){
    }
    
    /**
     * Metodo para resetear los componentes de la vista actual. Este "reset" es para "limpiar"
     * los componentes de un anterior uso y no para instanciar nuevo o hacer nulos otros
     */
    public void reset(){
    }

    public Stage getStage() {
        return stage;
    }

    public Stage getModal() {
        return modal;
    }
    
    public void showModal(boolean show){
        if(this.modal != null){
            if(show)this.modal.show();
            else this.modal.hide();
        }
    }

    public T getRoot() {
        return root;
    }

    public ResourceBundle getResources() {
        return resources;
    }
}
