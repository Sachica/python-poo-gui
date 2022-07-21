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
import ufps.arqui.python.poo.gui.exceptions.Exceptions;
import ufps.arqui.python.poo.gui.models.ArchivoPython;
import ufps.arqui.python.poo.gui.utils.ConfGrid;
import ufps.arqui.python.poo.gui.utils.ViewTool;
import ufps.arqui.python.poo.gui.views.IPanelView;

/**
 *Clase Editor Archivo Contenido
 * Clase encargada de recibir la ruta del la clase para despues tomar su contenido y llenarlo en text area, para mostrar.
 * Tambien toma el nombre de clase desde la ruta.
 * 
 * @author Rafael Peña
 */
public class EditorArchivoContenido implements IPanelView{
    private JPanel panel;
    private JTabbedPane tabbedPane;
    private JTextArea textAreaErrores;
    private JTextArea txtArea;
    private NumeroLinea numero;
    private JScrollPane scrollEditor;
    private final UndoManager undo = new UndoManager(); //instantiate an UndoManager
    private final Document doc;

    
    private String cotenidoInicial;
    private String ruta;
    private boolean sinGuardar;
    
    public EditorArchivoContenido(JTabbedPane tabbedPane) {
        this.panel = new JPanel(new GridBagLayout());
        this.tabbedPane = tabbedPane;
        this.sinGuardar = true;
        
        this.txtArea = new JTextArea();
        this.txtArea.setTabSize(2);
        this.txtArea.setText(this.cotenidoInicial);
        this.doc = txtArea.getDocument();  //instantiate a Document class of the txtArea
        this.textAreaErrores = new JTextArea();
        
        this.scrollEditor = new JScrollPane();
        this.numero = new NumeroLinea(txtArea);
        
        this.scrollEditor.setRowHeaderView(numero);
        this.scrollEditor.setViewportView(txtArea);
        
        this.inicializarContenido();
        this.addEvent();
    }
    
    @Override
    public void inicializarContenido(){
        ConfGrid config = new ConfGrid(this.panel, this.scrollEditor);
        config.setGridx(0);
        config.setGridy(0);
        config.setWeightx(1);
        config.setWeighty(1);
        config.setGridwidth(1);
        config.setGridheight(1);
        config.setFill(GridBagConstraints.BOTH);
        config.setAnchor(GridBagConstraints.CENTER);
        config.setIpadx(0);
        config.setIpady(0);
        ViewTool.insert(config);
        
        this.textAreaErrores.setEditable(false);
        this.textAreaErrores.setFont(new Font("Monospaced", 1, 12));
        this.textAreaErrores.setForeground(Color.RED);
        JScrollPane scrollTextAreaErrores = new JScrollPane(this.textAreaErrores);
        config = new ConfGrid(this.panel, scrollTextAreaErrores);
        config.setGridx(0);
        config.setGridy(1);
        config.setWeightx(1);
        config.setWeighty(0);
        config.setGridwidth(1);
        config.setGridheight(1);
        config.setFill(GridBagConstraints.HORIZONTAL);
        config.setAnchor(GridBagConstraints.CENTER);
        config.setIpadx(0);
        config.setIpady(0);
        ViewTool.insert(config);
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
                if(tabbedPane.getSelectedIndex() != -1){
                    sinGuardar = !cotenidoInicial.equals(txtArea.getText());
                    if(sinGuardar){
                        changeBackgroundColor(tabbedPane.getSelectedIndex(), Color.GRAY);
                    }else{
                        changeBackgroundColor(tabbedPane.getSelectedIndex(), new Color(242, 242, 242));
                    }
                }
            }
        });
    }
    
    @Override
    public JPanel getPanel() {
        return this.panel;
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
    
    public void setContenido(ArchivoPython archivoPython){
        this.ruta = archivoPython.getFichero().getAbsolutePath();
        if(sinGuardar){
            try{
                this.cotenidoInicial = archivoPython.getContenido();
                this.sinGuardar = false;
            }catch(Exceptions e){
                mostrarError(this.panel, e);
            }
        }
        this.txtArea.setText(this.cotenidoInicial);
        
        if(archivoPython.getExcepcionCompilar() != null){
            this.textAreaErrores.setText(archivoPython.getExcepcionCompilar().toString());
        }else{
            this.textAreaErrores.setText("");
        }
        
        this.changeBackgroundColor(tabbedPane.getSelectedIndex(), new Color(242, 242, 242));
    }
    
    private void changeBackgroundColor(int indexTab, Color color){
        if(indexTab != -1){
            tabbedPane.setBackgroundAt(indexTab, color);
        }
    }
    
    public String getContenido(){
        return this.txtArea.getText();
    }
}
