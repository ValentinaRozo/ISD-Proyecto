package com.calderon_garcia_rozo.servidores;

import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import com.calderon_garcia_rozo.modelo.Oferta;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;


public class Servidor3 {
    public static void main(String[] args) throws Exception
    {
        try (ZContext context = new ZContext()) {
            // Socket to talk to clients
            ZMQ.Socket socket = context.createSocket(SocketType.REP);
            socket.bind("tcp://*:5553");
            System.out.println("Servidor 3 activo...");
            System.out.println("Recibiendo...");

            while (!Thread.currentThread().isInterrupted()) {
                String string = socket.recvStr(0).trim();
                StringTokenizer sscanf = new StringTokenizer(string, " ");
                int val = Integer.valueOf(sscanf.nextToken());

                if(val != 1){
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

                    //Dht.dht.put(codEmpleador+codOferta, insertar(codOferta, codEmpleador, sector, 1));
                    insertar(codOferta, codEmpleador, sector, 3, capacidades);
                    //System.out.println(Dht.gety());
                    String response = "1";
                    socket.send(response.getBytes(ZMQ.CHARSET));
                }else{
                    String response = "1";
                    socket.send(response.getBytes(ZMQ.CHARSET), 0);
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

    
}
