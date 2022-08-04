/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufps.arqui.python.poo.gui.views.modals;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ufps.arqui.python.poo.gui.models.Fichero;
import ufps.arqui.python.poo.gui.views.ViewBase;

/**
 *
 * @author dunke
 */
public class ViewModalCreateFile extends ViewBase<BorderPane, Fichero>{
    private TextField txtNameFile;
    
    private Label lblParentName;
    
    private Fichero fichero;
    
    public ViewModalCreateFile() {
        super();
    }

    @Override
    public void preload(Fichero object) {
        this.fichero = object;
        this.lblParentName.setText(this.fichero.getFichero().getAbsolutePath());
    }

    @Override
    public void initialize() {
        super.initialize();
        
        super.modal = new Stage();
        super.modal.setScene(new Scene(super.root));
        super.modal.setTitle(super.resources.getString("ModalCreateFile.title"));
        super.modal.initModality(Modality.APPLICATION_MODAL);
        super.modal.initOwner(this.stage);
    }

    public Fichero getFichero() {
        return fichero;
    }

    public String getNameFile() {
        return txtNameFile.getText();
    }
}
