/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufps.arqui.python.poo.gui.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.File;
import java.io.IOException;
import ufps.arqui.python.poo.gui.models.ArchivoPython;

/**
 *
 * @author dunke
 */
public class ArchivoPythonAdapter extends TypeAdapter<ArchivoPython>{

    @Override
    public void write(JsonWriter arg0, ArchivoPython arg1) throws IOException {
        //Se deberia implementar pero en ningun momento del proyecto se esta transformando
        //un objeto a json
    }

    @Override
    public ArchivoPython read(JsonReader reader) throws IOException {
        ArchivoPython archivoPython = new ArchivoPython();
        reader.beginObject();
        
        while(reader.hasNext()){
            switch(reader.nextName()){
                case "ficheroStr": 
                    archivoPython.setFichero(new File(reader.nextString()));
                    break;
                case "module": 
                    archivoPython.setModule(reader.nextString());
                    break;
                case "classes": 
                    reader.beginArray();
                    while(reader.hasNext()){
                        archivoPython.addClass(reader.nextString());
                    }
                    reader.endArray();
            }
        }
        
        reader.endObject();
        return  archivoPython;
    }
    
}
