package ufps.arqui.python.poo.gui.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación para indicar que el campo en el controlador sera compartido con la vista
 * asociada al mismo, para que se pueda lograr compartir el atributo la vista debe
 * definir un atributo del mismo tipo y con el mismo nombre
 * @author Sachikia
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SharedView {
}
