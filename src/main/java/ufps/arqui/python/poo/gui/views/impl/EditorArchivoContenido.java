package ufps.arqui.python.poo.gui.views.impl;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import ufps.arqui.python.poo.gui.models.ArchivoPython;

/**
 *Clase Editor Archivo Contenido
 * Clase encargada de recibir la ruta del la clase para despues tomar su contenido y llenarlo en text area, para mostrar.
 * Tambien toma el nombre de clase desde la ruta.
 * 
 * @author Rafael Peña
 */
public class EditorArchivoContenido {
    private JTabbedPane tabbedPane;
    private String cotenidoInicial;
    private String titulo;
    private String ruta;
    private JTextArea txtArea;
    private NumeroLinea numero;
    private JScrollPane jsCroll;
    private final UndoManager undo = new UndoManager(); //instantiate an UndoManager
    private final Document doc;
    
    public EditorArchivoContenido(ArchivoPython archivoPython, JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
        this.cotenidoInicial = archivoPython.getContenido().toString();
                        
        this.ruta = archivoPython.getArchivo().getAbsolutePath();
        
        this.txtArea = new JTextArea();
        this.txtArea.setTabSize(2);
        this.txtArea.setText(this.cotenidoInicial);
        this.doc = txtArea.getDocument();  //instantiate a Document class of the txtArea
        
        this.jsCroll = new JScrollPane();
        this.numero = new NumeroLinea(txtArea);
        
        this.jsCroll.setRowHeaderView(numero);
        this.jsCroll.setViewportView(txtArea);
        
        this.titulo = archivoPython.getArchivo().getName();
        
        this.addEvent();
    }

    private void addEvent(){
        doc.addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent evt) {
                undo.addEdit(evt.getEdit());
            }
        });
        txtArea.getActionMap().put("Undo", new AbstractAction("Undo") {
            public void actionPerformed(ActionEvent evt) {
                try {
                    if (undo.canUndo()) {
                        undo.undo();
                    }
                } catch (CannotUndoException e) {
                }
            }
        });
        txtArea.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "Undo");
        this.txtArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                this.validateContent();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                this.validateContent();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                this.validateContent();
            }
            
            private void validateContent(){
                if(!cotenidoInicial.equals(txtArea.getText())){
                    tabbedPane.setBackgroundAt(tabbedPane.getSelectedIndex(), Color.GRAY);
                }else{
                    tabbedPane.setBackgroundAt(tabbedPane.getSelectedIndex(), new Color(242, 242, 242));
                }
            }
        });
    }
    
    public JScrollPane getPanel() {
        return this.jsCroll;
    }

    public String getPath() {
        return ruta;
    }

    public void setPath(String path) {
        this.ruta = path;
    }

    public JTextArea getTxtArea() {
        return txtArea;
    }

    public void setTxtArea(JTextArea txtArea) {
        this.txtArea = txtArea;
    }

    public String getTitle() {
        return titulo;
    }

    public void setTitle(String title) {
        this.titulo = title;
    }
    
    public void setContenido(String contenido){
        this.txtArea.setText(contenido);
        this.cotenidoInicial = contenido;
        tabbedPane.setBackgroundAt(tabbedPane.getSelectedIndex(), new Color(242, 242, 242));
    }
    
    public String getContenido(){
        return this.txtArea.getText();
    }
}
