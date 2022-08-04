/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufps.arqui.python.poo.gui.views;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.fxmisc.flowless.VirtualizedScrollPane;
import ufps.arqui.python.poo.gui.exceptions.Exceptions;
import ufps.arqui.python.poo.gui.models.ArchivoPython;
import ufps.arqui.python.poo.gui.views.editor.CustomCodeArea;
import ufps.arqui.python.poo.gui.views.editor.ManageTextEditor;

/**
 *
 * @author dunke
 */
public class ViewEditorTexto extends ViewBase<BorderPane, ArchivoPython>{
    private TabPane tabPane;
    private Map<String, CustomCodeArea> tabs = new HashMap<>();

    public ViewEditorTexto() {
        super();
    }
    
    @Override
    public void initialize() {
        super.initialize(); //To change body of generated methods, choose Tools | Templates.
        
        super.modal = new Stage();
        super.modal.setScene(new Scene(super.root));
        super.modal.setTitle(super.resources.getString("PanelEditorTexto.title"));
        
        super.modal.getScene().getStylesheets().add(getClass().getResource("/styles/python-keywords.css").toExternalForm());
    }

    @Override
    public void preload(ArchivoPython object) {
        try{
            CustomCodeArea codeArea = this.tabs.get(object.getFichero().getAbsolutePath());
            if(codeArea == null){
                Tab tab = new Tab();
                codeArea = new CustomCodeArea(object, tab);
                
                tab.setText(object.getFichero().getName());
                tab.setContent(new VirtualizedScrollPane<>(codeArea.getCodeArea()));
                
                this.tabs.put(object.getFichero().getAbsolutePath(), codeArea);
                this.tabPane.getTabs().add(tab);
            }
            
            this.tabPane.getSelectionModel().select(codeArea.getTab());
            super.modal.requestFocus();
        }catch(Exceptions e){
            e.printStackTrace();
        }
    }
    
    public void stop(){
        for(CustomCodeArea codeArea: this.tabs.values()){
            codeArea.stop();
        }
        ManageTextEditor.getExecutor().shutdown();
    }
}
