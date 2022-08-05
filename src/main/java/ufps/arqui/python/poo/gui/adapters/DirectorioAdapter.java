/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufps.arqui.python.poo.gui.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.File;
import java.io.IOException;
import ufps.arqui.python.poo.gui.models.Directorio;

/**
 *
 * @author dunke
 */
public class DirectorioAdapter extends TypeAdapter<Directorio>{

    @Override
    public void write(JsonWriter writer, Directorio value) throws IOException {
        //Se deberia implementar pero en ningun momento del proyecto se esta transformando
        //un objeto a json
    }

    @Override
    public Directorio read(JsonReader reader) throws IOException {
        Directorio directorio = new Directorio();
        reader.beginObject();
        
        while(reader.hasNext()){
            switch(reader.nextName()){
                case "ficheroStr": 
                    directorio.setFichero(new File(reader.nextString()));
                    break;
                case "files": 
                    reader.beginArray();
                    while(reader.hasNext()){
                        directorio.addFile(reader.nextString());
                    }
                    reader.endArray();
                    break;
                case "directorys": 
                    reader.beginArray();
                    while(reader.hasNext()){
                        directorio.addDirectory(reader.nextString());
                    }
                    reader.endArray();
            }
        }
        
        reader.endObject();
        return  directorio;
    }
    
    public static void main(String[] args) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Directorio.class, new DirectorioAdapter().nullSafe());
        
        String test = "{\"ficheroStr\": \"path/to/test\",\"files\": [\"path/to/test/a.py\",\"path/to/test/b.py\"],\"directorys\": [\"path/to/test/sub1\",\"path/to/test/sub2\"]}";
        Gson gson = builder.create();
        
        Directorio dir = gson.fromJson(test, Directorio.class);
        System.out.println(dir);
    }
}
