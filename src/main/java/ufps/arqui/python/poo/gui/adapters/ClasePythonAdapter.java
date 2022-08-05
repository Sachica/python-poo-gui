/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufps.arqui.python.poo.gui.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import ufps.arqui.python.poo.gui.models.ClasePython;

/**
 *
 * @author dunke
 */
public class ClasePythonAdapter extends TypeAdapter<ClasePython>{

    @Override
    public void write(JsonWriter arg0, ClasePython arg1) throws IOException {
        //Se deberia implementar pero en ningun momento del proyecto se esta transformando
        //un objeto a json
    }

    @Override
    public ClasePython read(JsonReader reader) throws IOException {
        ClasePython clasePython = new ClasePython();
        reader.beginObject();
        
        while(reader.hasNext()){
            switch(reader.nextName()){
                case "name": 
                    clasePython.setName(reader.nextString());
                    break;
                case "pathModule": 
                    clasePython.setPathModule(reader.nextString());
                    break;
                case "bases": 
                    reader.beginArray();
                    while(reader.hasNext()){
                        clasePython.addBase(reader.nextString());
                    }
                    reader.endArray();
            }
        }
        
        reader.endObject();
        return clasePython;
    }
}
