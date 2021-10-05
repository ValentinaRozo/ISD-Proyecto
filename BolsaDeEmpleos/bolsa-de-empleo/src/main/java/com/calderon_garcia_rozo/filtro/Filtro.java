package com.calderon_garcia_rozo.filtro;

import java.util.StringTokenizer;

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
            int contSer1 = 0;
            int contSer2 = 0;
            int contSer3 = 0;

            //Recibe multiples ofertas
            while (true) {
                String string = subscriber.recvStr(0).trim();

                StringTokenizer sscanf = new StringTokenizer(string, " ");
                String oferta = String.valueOf(sscanf.nextToken());
                String codEmpleador = String.valueOf(sscanf.nextToken());
                String codOferta = String.valueOf(sscanf.nextToken());
                int sector = Integer.valueOf(sscanf.nextToken());


                System.out.println(
                        String.format(
                                "'%s' con código '%s' del sector '%d'",
                                oferta,
                                codOferta,
                                sector
                        )
                );
                if((contSer1 <= contSer2) && (contSer1 <= contSer3)){
                    //va al servidor 1
                    contSer1++;
                    ZMQ.Socket socket = context.createSocket(SocketType.REQ);
                    socket.connect("tcp://localhost:5555");

                    String request = String.format(
                        "%s %s %s %d", oferta, codEmpleador, codOferta, sector
                    );

                    socket.send(request.getBytes(ZMQ.CHARSET), 0);
                }else if((contSer2 <= contSer1) && (contSer2 <= contSer3)){
                    //va al servidor 2
                    contSer2++;
                    ZMQ.Socket socket = context.createSocket(SocketType.REQ);
                    socket.connect("tcp://localhost:5554");

                    String request = String.format(
                        "%s %s %s %d", oferta, codEmpleador, codOferta, sector
                    );

                    socket.send(request.getBytes(ZMQ.CHARSET), 0);
                }else if((contSer3 <= contSer1) && (contSer3 <= contSer2)){
                    //Va al servidor 3
                    contSer3++;
                    ZMQ.Socket socket = context.createSocket(SocketType.REQ);
                    socket.connect("tcp://localhost:5553");

                    String request = String.format(
                        "%s %s %s %d", oferta, codEmpleador, codOferta, sector
                    );

                    socket.send(request.getBytes(ZMQ.CHARSET), 0);
                } 
            } 
        }
    }
}