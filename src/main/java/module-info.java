module ufps.arqui.python.poo.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.fxmisc.richtext;
    requires org.fxmisc.flowless;
    requires org.fxmisc.undo;
    requires reactfx;
    requires wellbehavedfx;

    opens ufps.arqui.python.poo.gui to javafx.controls, javafx.fxml, reactfx, wellbehavedfx, org.fxmisc.undo, org.fxmisc.richtext, org.fxmisc.flowless;
    opens ufps.arqui.python.poo.gui.models to javafx.base;
    opens ufps.arqui.python.poo.gui.controllers to javafx.fxml;
    opens ufps.arqui.python.poo.gui.controllers.modals to javafx.fxml;
    opens ufps.arqui.python.poo.gui.controllers.complements to javafx.fxml;
    
    opens ufps.arqui.python.poo.gui.adapters to com.google.gson;
    opens ufps.arqui.python.poo.gui.utils to com.google.gson;
    
    exports ufps.arqui.python.poo.gui;
}
