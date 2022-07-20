package ufps.arqui.python.poo.gui.views;

import javafx.scene.control.TreeView;
import ufps.arqui.python.poo.gui.models.Directorio;

/**
 *
 * @author Sachikia
 */
public interface IViewFichero {
    public void populateTreeView(Directorio directorio);
    
    public void setTreeView(TreeView<String> treeView);
}
