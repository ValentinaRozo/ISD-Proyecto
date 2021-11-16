package com.calderon_garcia_rozo.servidores;
import com.calderon_garcia_rozo.modelo.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.util.StringTokenizer;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;

import org.zeromq.ZContext;


public class Servidor1 {
    public static void main(String[] args) throws Exception
    {
        try (ZContext context = new ZContext()) {
            // Socket to talk to clients
            ZMQ.Socket socket = context.createSocket(SocketType.REP);
            socket.bind("tcp://*:5555");
            System.out.println("Servidor 1 activo...");
            System.out.println("Recibiendo...");

            

            while (!Thread.currentThread().isInterrupted()) {
                String string = socket.recvStr(0).trim();
                StringTokenizer sscanf = new StringTokenizer(string, " ");
                int val = Integer.valueOf(sscanf.nextToken());

                if(val == 0){
                    String oferta = String.valueOf(sscanf.nextToken());
                    String codEmpleador = String.valueOf(sscanf.nextToken());
                    String codOferta = String.valueOf(sscanf.nextToken());
                    int sector = Integer.valueOf(sscanf.nextToken());
                    String capacidades = String.valueOf(sscanf.nextToken());

                    System.out.println(
                            String.format(
                                    "Recibida '%s' con código '%s' del sector '%d'",
                                    oferta,
                                    codOferta,
                                    sector
                            )
                    );
                    //dht.insertar("1", "1", sector, 1);
                    //Dht.dht.put(codEmpleador+codOferta, insertar(codOferta, codEmpleador, sector, 1));
                    insertar(codOferta, codEmpleador, sector, 1, capacidades);
                    //System.out.println(Dht.dht);
                    String response = "1";
                    socket.send(response.getBytes(ZMQ.CHARSET));
                }else if(val == 2){
                    ArrayList <String> ar = leer();

                    for (String a: ar){
                        System.out.println(a);
                        socket.send(a.getBytes(ZMQ.CHARSET));
                    }
                }else{
                    String response = "1";
                    socket.send(response.getBytes(ZMQ.CHARSET));
                }
                
            }
            
        }

        
    }

    public static void insertar(String codOferta, String codEmpleador, int sector, int servidor, String capacidades) {
        Oferta oferta = new Oferta();
        oferta.setCodEmpleador(codEmpleador);
        oferta.setCodOferta(codOferta);
        oferta.setSector(sector);
        oferta.setServidor(servidor);
        oferta.setCapacidades(capacidades);

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
            csvWriter.append(",");
            csvWriter.append(oferta.getCapacidades());
            csvWriter.append("\n");

            csvWriter.flush();
            csvWriter.close();
                       
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList <String> leer(){
        String archCSV = "bd.csv";
        ArrayList <String[]> arr = new ArrayList<>();
        ArrayList <String> ar = new ArrayList<>();
        String line = "";
        String splitBy = ",";
        
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(archCSV));
                while ((line = br.readLine()) != null)
                {
                    String[] e = line.split(splitBy);
                    arr.add(e);
                }
            } catch (IOException e) {
               
                e.printStackTrace();
            }

        for (String[] i: arr){
            String clave, codOferta, codEmpleador, capacidades;
            int sector, servidor;

            clave = i[0];
            servidor = Integer.valueOf(i[1]);
            codEmpleador = i[2];
            codOferta = i[3];
            sector = Integer.valueOf(i[4]);
            capacidades = i[5];

            String va = String.format("%d %s %d %s %s %s", 
                sector, 
                clave, 
                servidor, 
                codEmpleador, 
                codOferta, 
                capacidades
            );
            ar.add(va);
        }

        return ar;


    }

    
}
