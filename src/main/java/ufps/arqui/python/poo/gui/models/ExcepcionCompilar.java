package ufps.arqui.python.poo.gui.models;

/**
 *
 * @author Sachikia
 */
public class ExcepcionCompilar {
    private Integer nlinea;
    private String problema;
    private String error;

    public ExcepcionCompilar() {
    }

    public ExcepcionCompilar(Integer nlinea, String problema, String error) {
        this.nlinea = nlinea;
        this.problema = problema;
        this.error = error;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Error: ").append(this.error);
        sb.append("\nLinea: ").append(this.nlinea);
        sb.append("\nProblema:\n>> ").append(this.problema);
        return sb.toString();
    }
    
    public Integer getNlinea() {
        return nlinea;
    }

    public void setNlinea(Integer nlinea) {
        this.nlinea = nlinea;
    }

    public String getProblema() {
        return problema;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
