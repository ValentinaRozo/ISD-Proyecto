package com.calderon_garcia_rozo.filtro;

import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

import com.calderon_garcia_rozo.modelo.Dht;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

//  Connects SUB socket to tcp://localhost:5556
public class Filtro
{
    public static void main(String[] args)
    {
        try (ZContext context = new ZContext()) {
            //  Socket to talk to server
            System.out.println("Recibiendo ofertas...");
            ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);
            subscriber.connect("tcp://localhost:5556");
            

            //Suscribir para recibir todas las ofertas 
            String filtro = (args.length > 0) ? args[0] : "Oferta";
            subscriber.subscribe(filtro.getBytes(ZMQ.CHARSET));

            //Recibe multiples ofertas
            while (true) { 
                

                String string = subscriber.recvStr(0).trim();

                StringTokenizer sscanf = new StringTokenizer(string, " ");
                String oferta = String.valueOf(sscanf.nextToken());
                String codEmpleador = String.valueOf(sscanf.nextToken());
                String codOferta = String.valueOf(sscanf.nextToken());
                int sector = Integer.valueOf(sscanf.nextToken());
                String capacidades = String.valueOf(sscanf.nextToken());


                System.out.println(
                        String.format(
                                "'%s' con código '%s' del sector '%d'",
                                oferta,
                                codOferta,
                                sector
                        )
                );

                //Complete the request
                String request = String.format(
                    "%d %s %s %s %d %s", 0, oferta, codEmpleador, codOferta, sector, capacidades
                );

                int max = 3;
                int min = 1;
                int a = 0;
                String reply = "";
                ArrayList <Integer> arr = new ArrayList<Integer>();

                
                while(a == 0){
                    Random rn = new Random();
                    int wichServer = rn.nextInt(max - min + min) + 1;

                    System.out.println("Tamaño: " + Dht.gety());

                    if(wichServer == 1){
                        //Server 1
                        ZMQ.Socket socket1 = context.createSocket(SocketType.REQ);
                        socket1.connect("tcp://localhost:5555");
                        String reque = String.format(
                            "%d", 1
                        );
                        socket1.send(reque.getBytes(ZMQ.CHARSET), 0);
                        socket1.setReceiveTimeOut(1000);
                        reply = socket1.recvStr(0); 
                        if(reply != null){
                            a = 1;
                            arr.add(sector);
                            arr.add(1);
                            Dht.dht.put(codEmpleador+codOferta, arr);
                            socket1.send(request.getBytes(ZMQ.CHARSET), 0);
                        }else{
                            min = 2;
                            max = 3;
                        }

                    }else if(wichServer == 2){
                        //Server 2
                        ZMQ.Socket socket2 = context.createSocket(SocketType.REQ);
                        socket2.connect("tcp://localhost:5554");
                        String reque = String.format(
                            "%d", 1
                        );
                        socket2.send(reque.getBytes(ZMQ.CHARSET), 0);
                        socket2.setReceiveTimeOut(1000);
                        reply = socket2.recvStr(); 
                        if(reply != null){
                            a = 1;
                            arr.add(sector);
                            arr.add(2);
                            Dht.dht.put(codEmpleador+codOferta, arr);
                            socket2.send(request.getBytes(ZMQ.CHARSET), 0);
                        }
                        else{
                            min = 1;
                            max = 3;
                        }
                    }else if(wichServer == 3){
                        //Server 3
                        ZMQ.Socket socket3 = context.createSocket(SocketType.REQ);
                        socket3.connect("tcp://localhost:5553");
                        String reque = String.format(
                            "%d", 1
                        );
                        socket3.send(reque.getBytes(ZMQ.CHARSET), 0);
                        socket3.setReceiveTimeOut(1000);
                        reply = socket3.recvStr(); 
                        if(reply != null){
                            a = 1;
                            arr.add(sector);
                            arr.add(3);
                            Dht.dht.put(codEmpleador+codOferta, arr);
                            socket3.send(request.getBytes(ZMQ.CHARSET), 0); 
                        }else{
                            max = 2;
                            min = 1;
                        }
                    }
                }
                
            } 
        }
    }
}