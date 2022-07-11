package ufps.arqui.python.poo.gui;

import com.formdev.flatlaf.FlatLightLaf;
import ufps.arqui.python.poo.gui.controllers.IMenuController;
import ufps.arqui.python.poo.gui.controllers.IMundoController;
import ufps.arqui.python.poo.gui.controllers.IProyectoController;
import ufps.arqui.python.poo.gui.controllers.ITerminalController;
import ufps.arqui.python.poo.gui.controllers.impl.MenuController;
import ufps.arqui.python.poo.gui.controllers.impl.MundoController;
import ufps.arqui.python.poo.gui.controllers.impl.ProyectoController;
import ufps.arqui.python.poo.gui.controllers.impl.TerminalController;
import ufps.arqui.python.poo.gui.models.Editor;
import ufps.arqui.python.poo.gui.models.Mundo;
import ufps.arqui.python.poo.gui.models.Proyecto;
import ufps.arqui.python.poo.gui.utils.TerminalInteractiva;
import ufps.arqui.python.poo.gui.views.*;
import ufps.arqui.python.poo.gui.views.impl.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;


/**
 * Clase Main para la inicialización del proyecto.
 *
 * Clase iniciadora del sistema.
 *
 * @author Omar Ramón Montes
 */
public class PythonPooGui {

    public static void main(String[] args) throws Exception {

        // Diseño de la aplicación
        FlatLightLaf.setup();

        // Modelo
        TerminalInteractiva terminalInteractiva = new TerminalInteractiva();

        Editor editor = new Editor();
        Proyecto proyecto = new Proyecto(terminalInteractiva, editor);
        Mundo mundo = new Mundo(terminalInteractiva);

        // Inicializar el menú.
        IMenuController menuController = new MenuController(proyecto);
        IPanelMenu panelMenu = new PanelMenu(menuController);

        // Inicializar el terminal.
        ITerminalController terminalController = new TerminalController(proyecto, mundo);
        IPanelTerminal panelTerminal = new PanelTerminal(terminalController);

        // Inicializar el mundo.
        IMundoController mundoController = new MundoController(proyecto, mundo);
        IPanelMundo panelMundo = new PanelMundo(mundoController);

        // Inicializar el proyecto.
        IProyectoController proyectoController = new ProyectoController(proyecto);
        IPanelProyecto panelProyecto = new PanelProyecto(proyectoController);
        
        EditorTexto editorTexto = new EditorTexto(proyectoController);
        editor.addObserver(editorTexto);
        
        IPanelFichero panelFichero = new PanelFichero(proyectoController);

        IPanelMundoTerminal panelMundoTerminal = new PanelMundoTerminal(panelMundo, panelTerminal);
        IPanelFicheroProyecto panelFicheroProyecto = new PanelFicheroProyecto(panelFichero, panelProyecto);

        // Agregar Observadores del proyecto
        proyecto.addObserver(panelMenu);
        proyecto.addObserver(panelFichero);
        proyecto.addObserver(panelTerminal);
        proyecto.addObserver(panelMundo);
        proyecto.addObserver(panelProyecto);
        proyecto.addObserver(panelTerminal);
        mundo.addObserver(panelTerminal);
        mundo.addObserver(panelMundo);
        terminalInteractiva.addObserver(mundo);
        terminalInteractiva.addObserver(proyecto);

        // Crear splash al momento de iniciar el sistema.
        JWindow window = new JWindow();
        window.setSize(460, 344);
        window.setLocationRelativeTo(null);
        InputStream logo = PythonPooGui.class.getClassLoader().getResourceAsStream("logo.gif");
        window.getContentPane().add(new JLabel(new ImageIcon(ImageIO.read(logo))), SwingConstants.CENTER);
        window.setVisible(true);
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Iniciar de ventana principal.
        IMainView main = new MainView("BluePy", panelMenu, panelFicheroProyecto, panelMundoTerminal);

        // Cerrar splash cuando la interfaz ya es visible.
        window.dispose();

    }

}
