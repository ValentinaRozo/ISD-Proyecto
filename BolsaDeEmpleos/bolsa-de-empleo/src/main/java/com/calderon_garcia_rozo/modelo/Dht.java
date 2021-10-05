package com.calderon_garcia_rozo.modelo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;



public class Dht {

    private static Dht instance;
    private static HashMap<String, Object> dht = new HashMap<String, Object>();

    private Dht(){
    };
   
    public static Dht getInstance() {
        if (instance == null) {
            instance = new Dht();
        }
        return instance;
    }
    
    public HashMap<String, Object> getDMap() {
        return dht;
    }

    public void insertar(String codOferta, String codEmpleador, int sector, int servidor) {
        System.out.println(dht.size());
        Oferta oferta = new Oferta();
        oferta.setCodEmpleador(codEmpleador);
        oferta.setCodOferta(codOferta);
        oferta.setSector(sector);
        oferta.setServidor(servidor);
        dht.put(codEmpleador+codOferta, oferta);

        String archCSV = "bd.csv";
        
        try {
            FileWriter csvWriter = new FileWriter(archCSV, true);
            csvWriter.append(codEmpleador+codOferta);
            csvWriter.append(",");
            csvWriter.append(String.valueOf(servidor));
            csvWriter.append(",");
            csvWriter.append(oferta.getCodEmpleador());
            csvWriter.append(",");
            csvWriter.append(oferta.getCodOferta());
            csvWriter.append(",");
            csvWriter.append(String.valueOf(sector));
            csvWriter.append("\n");

            csvWriter.flush();
            csvWriter.close();
                       
        } catch (IOException e) {
            e.printStackTrace();
        }
       
        System.out.println(dht.size());
        System.out.println(instance.toString());
    }
}
