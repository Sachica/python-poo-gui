module ufps.arqui.python.poo.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens ufps.arqui.python.poo.gui to javafx.fxml;
    opens ufps.arqui.python.poo.gui.models to javafx.base;
    opens ufps.arqui.python.poo.gui.controllers to javafx.fxml;
    opens ufps.arqui.python.poo.gui.controllers.modals to javafx.fxml;
    opens ufps.arqui.python.poo.gui.controllers.complements to javafx.fxml;
    
    opens ufps.arqui.python.poo.gui.utils to com.google.gson;
    
    exports ufps.arqui.python.poo.gui;
    exports ufps.arqui.python.poo.gui.controllers;
    exports ufps.arqui.python.poo.gui.controllers.modals;
    exports ufps.arqui.python.poo.gui.controllers.complements;
}
