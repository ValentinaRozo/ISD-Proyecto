package com.calderon_garcia_rozo.clientes;

import java.util.Scanner;
import java.util.StringTokenizer;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Aspirante {
    public static void main(String[] args) throws Exception
    {
        try (ZContext context = new ZContext()) {
            System.out.println("Bienvenido aspirante");
            ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);
            subscriber.connect("tcp://localhost:5558");

            Scanner scanner = new Scanner(System.in);
           

            System.out.println("Elija a las ofertas que quiere suscribirse");
            System.out.println("1. Gerentes y directores");
            System.out.println("2. Científicos e intelectuales");
            System.out.println("3. Agricultores");
            System.out.println("4. Personal administrativo");
            System.out.println("5. Operarios y artesanos");

            //Suscribir máximo a dos tópicos
            String susc = scanner.nextLine();
            String susc2 = scanner.nextLine();

            String filtro = (args.length > 0) ? args[0] : susc;
            String filtro2 = (args.length > 0) ? args[0] : susc2;

            //Suscribir para recibir todas las ofertas 
            subscriber.subscribe(filtro.getBytes(ZMQ.CHARSET));
            subscriber.subscribe(filtro2.getBytes(ZMQ.CHARSET));

            scanner.close();
            
            while (true) { 
                String string = subscriber.recvStr(0).trim();

                StringTokenizer sscanf = new StringTokenizer(string, " ");
                String sector = String.valueOf(sscanf.nextToken());
                String clave = String.valueOf(sscanf.nextToken());
                String codOferta = String.valueOf(sscanf.nextToken());
                String codEmpl = String.valueOf(sscanf.nextToken());
                String capacidades = String.valueOf(sscanf.nextToken());
                int servidor = Integer.valueOf(sscanf.nextToken());

                System.out.println(
                        String.format(
                                "Oferta del sector '%s' con código '%s y capacidades requeridad: %s' ",
                                sector,
                                codOferta,
                                capacidades
                        )
                );

                //Agendar cita con el empleador

            }

            
        }
    }
}
