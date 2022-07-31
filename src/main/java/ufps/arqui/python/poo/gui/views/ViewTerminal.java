package ufps.arqui.python.poo.gui.views;

import ufps.arqui.python.poo.gui.views.ViewBase;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import ufps.arqui.python.poo.gui.models.Mensaje;
import ufps.arqui.python.poo.gui.models.TipoMensaje;

/**
 *
 * @author Sachikia
 */
public class ViewTerminal extends ViewBase<BorderPane, Object> {

    private TextField textInput;

    private ScrollPane scrollPaneOutput;

    private VBox paneOutput;

    private Consumer<String> onEnterCommand;

    private final List<String> historicCommands = new ArrayList<>();
    private int cursor;

    @Override
    public void initialize() {
        super.initialize();

        this.textInput.setOnKeyPressed((KeyEvent e) -> {
            this.handleKeyPressed(e);
        });

        this.scrollPaneOutput.vvalueProperty().bind(this.paneOutput.heightProperty());
    }
    
    /**
     * Metodo para manejar el evento de tecla presionada sobre el {@link ViewTerminal#textInput}
     * @param e - evento producido al usuario presionar una tecla sobre el {@link ViewTerminal#textInput}
     */
    private void handleKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            String command = this.getCommand();
            this.onEnterCommand.accept(command);
        } else if (e.getCode() == KeyCode.UP) {
                putHistoricCommand(false);
        } else if (e.getCode() == KeyCode.DOWN) {
                putHistoricCommand(true);
        }
    }

    /**
     * Obtiene el comando, añade el comando ingresado al {@link ViewTerminal#paneOutput}, 
     * administra el historial de comando y por ultimo limpia el comando actual del {@link ViewTerminal#textInput}
     * @return 
     */
    private String getCommand() {
        String command = this.textInput.getText();
        Mensaje mensaje = new Mensaje(command, TipoMensaje.COMANDO);
        
        this.appendOutput(mensaje);
        this.historicCommands.add(command);
        this.cursor = this.historicCommands.size() - 1;

        this.textInput.setText("");

        return command;
    }

    /**
     * Cambia el valor del {@link ViewTerminal#textInput} con un valor historico
     * contenido en la lista {@link ViewTerminal#historicCommands}
     * @param back - boolean para navegar entre los comandos, indica si el usuario
     * quiere avanzar sobre la lista de comandos o retrocedes
     */
    private void putHistoricCommand(boolean back) {
        this.cursor += (back && this.cursor-1 >= 0) ? -1 : (!back && this.cursor+1 < this.historicCommands.size() ? 1 : 0);
        
        if(this.historicCommands.size() > 0){
        this.textInput.setText(this.historicCommands.get(this.cursor));
        }
    }
    
    /**
     * Añade la cadena de salida al {@link ViewTerminal#paneOutput} contenido dentro de un nodo 
     * objeto {@link Label}
     * @param output 
     */
    public void appendOutput(Mensaje output) {
        Platform.runLater(() -> {
            Label lbl = new Label(output.getLinea());

            if (output.getTipo().equals(TipoMensaje.COMANDO)) {
                lbl.setText(">>> " + output.getLinea());
            } else if (output.getTipo().equals(TipoMensaje.ERROR)) {
                lbl.setStyle("-fx-text-fill: #f00");
            }

            this.paneOutput.getChildren().add(lbl);
        });
    }
}
