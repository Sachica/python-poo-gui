package ufps.arqui.python.poo.gui.utils;

import ufps.arqui.python.poo.gui.exceptions.Exceptions;
import ufps.arqui.python.poo.gui.models.Mensaje;
import ufps.arqui.python.poo.gui.models.TipoMensaje;

import java.io.*;
import java.util.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Terminal interactiva que interactura con python.
 *
 * @author Omar Ramón Montes
 */
public class TerminalInteractiva extends Observable {

    private File directorio;
    private String parameters[];
    private Process process;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private BufferedReader bufferedReaderError;
    
    private final ObjectProperty<Mensaje> currentMessage = new SimpleObjectProperty<>();
    
    public TerminalInteractiva() {
    }

    public TerminalInteractiva(File directorio, String[] parameters) {
        this.directorio = directorio;
        this.parameters = parameters;
    }

    /**
     * Verifica si la terminal interactiva cuenta con el proceso activo.
     * @return true si la terminal está activa.
     */
    public boolean terminalActiva() {
        return this.process != null;
    }

    /**
     * Inicializa la terminal siempre y cuando este activa,
     * de lo contrario hara un reset
     */
    public void inicializarTerminal() throws Exceptions {
        try {
            //Si la terminal esta activa se hara un reset
            if (terminalActiva()) {
                this.process.destroyForcibly();
                this.bufferedReader.close();
                this.bufferedWriter.close();
                this.bufferedReaderError.close();
            }

            this.process = new ProcessBuilder(this.parameters).directory(this.directorio).start();
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.process.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(this.process.getInputStream()));
            this.bufferedReaderError = new BufferedReader(new InputStreamReader(this.process.getErrorStream()));

            this.leerSalida(this.bufferedReader, false);
            this.leerSalida(this.bufferedReaderError, true);
        } catch (IOException e) {
            throw new Exceptions("La terminal ha fallado", null);
        }
    }


    /**
     * Ingresar comando para ejecutar.
     *
     * @param command comando de python, debe ser una sola linea, sin salto de linea.
     * @throws IOException en caso de que los buffers no están abiertos.
     */
    public void ingresarComando(String command) throws Exceptions {
        try {
            if (terminalActiva()) {
                bufferedWriter.write(command);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } else {
                throw new Exceptions("Terminal inactiva", null);
            }
        } catch (IOException e) {
            throw new Exceptions("La terminal ha fallado", e);
        }
    }

    /**
     *
     * Lee la salida linea por linea de la terminal de python.
     *
     * @param buffered bufer del archivo de lectura.
     * @param error true si el buffer es de la salida de errores.
     */
    private void leerSalida(BufferedReader buffered, boolean error) {
        new Thread(() -> {
            try {
                String linea = "";
                while ((linea = buffered.readLine()) != null) {
                    this.currentMessage.setValue(new Mensaje(linea, error ? TipoMensaje.ERROR : TipoMensaje.SALIDA));
                }
            } catch (IOException e) {
            }
        }).start();
    }

    public void setDirectorio(File directorio) {
        this.directorio = directorio;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

    public ObjectProperty<Mensaje> getCurrentMessage() {
        return currentMessage;
    }

    public void stop() throws Exceptions {
        if (terminalActiva()) {
            try{
                this.process.destroyForcibly();
                this.bufferedReader.close();
                this.bufferedWriter.close();
                this.bufferedReaderError.close();
            }catch(IOException e){
                throw new Exceptions("La terminal interactiva no logro finalizar correctamente");
            }
        }
    }
}