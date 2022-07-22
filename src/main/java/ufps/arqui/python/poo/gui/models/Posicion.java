package ufps.arqui.python.poo.gui.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Representa los puntos cardinales de un panel.
 *
 * @author Sachikia
 */
public class Posicion {

    /**
     * Posición en el plano horizontal.
     */
    private final DoubleProperty x = new SimpleDoubleProperty();

    /**
     * Posición en el plano vertical.
     */
    private final DoubleProperty y = new SimpleDoubleProperty();

    public Posicion() {
    }

    public Posicion(int x, int y) {
        this.x.setValue(x);
        this.y.setValue(y);
    }

    public DoubleProperty getX() {
        return x;
    }

    public DoubleProperty getY() {
        return y;
    }
    
    public void setPosicion(double x, double y){
        this.setX(x);
        this.setY(y);
    }
    
    public void setX(double x){
        this.x.setValue(x);
    }
    
    public void setY(double y){
        this.y.setValue(y);
    }
    
    public void unbind(){
        this.x.unbind();
        this.y.unbind();
    }
    
    public void bindBidirectional(Property<Number> x, Property<Number> y){
        this.x.bindBidirectional(x);
        this.y.bindBidirectional(y);
    }
    
    public void unbindBidirectional(Property<Number> x, Property<Number> y){
        this.x.unbindBidirectional(x);
        this.y.unbindBidirectional(y);
    }
    
    public boolean isUnassigned(){
        return this.x.get() == 0 && this.y.get() == 0;
    }
}
