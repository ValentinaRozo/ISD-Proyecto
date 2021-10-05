package com.calderon_garcia_rozo.servidores;
import com.calderon_garcia_rozo.modelo.*;

import java.util.StringTokenizer;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;

import org.zeromq.ZContext;


public class Servidor1 {
    public static void main(String[] args) throws Exception
    {
        

        Dht dht = Dht.getInstance();
     
        try (ZContext context = new ZContext()) {
            // Socket to talk to clients
            ZMQ.Socket socket = context.createSocket(SocketType.REP);
            socket.bind("tcp://*:5555");
            System.out.println("Servidor 1 activo...");
            System.out.println("Recibiendo...");

            while (!Thread.currentThread().isInterrupted()) {
                String string = socket.recvStr(0).trim();
                StringTokenizer sscanf = new StringTokenizer(string, " ");
                String oferta = String.valueOf(sscanf.nextToken());
                String codEmpleador = String.valueOf(sscanf.nextToken());
                String codOferta = String.valueOf(sscanf.nextToken());
                int sector = Integer.valueOf(sscanf.nextToken());

                System.out.println(
                        String.format(
                                "Recibida '%s' con código '%s' del sector '%d'",
                                oferta,
                                codOferta,
                                sector
                        )
                );


                dht.insertar(codOferta, codEmpleador, sector, 1);
                
            }
            
        }
    }

    
}
