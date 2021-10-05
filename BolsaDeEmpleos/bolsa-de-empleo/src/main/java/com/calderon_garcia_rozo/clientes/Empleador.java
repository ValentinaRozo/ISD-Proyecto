package com.calderon_garcia_rozo.clientes;

import java.util.Scanner;
import java.util.UUID;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

//  Binds PUB socket to tcp://*:5556

public class Empleador
{
    public static void main(String[] args) throws Exception
    {
        //  Prepare our context and publisher
        try (ZContext context = new ZContext()) {
            ZMQ.Socket publisher = context.createSocket(SocketType.PUB);
            System.out.println("Bienvenido empleador");
            publisher.bind("tcp://*:5556");

            
            
            while (!Thread.currentThread().isInterrupted()) {
                //Preguntar si es un empleador nuevo
                //Buscar empleador en la base de datos
                //Crear nuevo empleador

                
                //Preguntar si desea crear ofertas de empleo
                System.out.println("¿Desea crear ofertas de empleo? Si - No");
                Scanner crear = new Scanner(System.in);
                String crea = crear.nextLine();
                String topico = "Oferta";
                String codOferta = UUID.randomUUID().toString();
                String codEmpleador = String.valueOf(Math.random()) + "emp";

                System.out.println("¿A qué sector pertenece su oferta?");
                System.out.println("1. Gerentes y directores");
                System.out.println("2. Científicos e intelectuales");
                System.out.println("3. Agricultores");
                System.out.println("4. Personal administrativo");
                System.out.println("5. Operarios y artesanos");

                Scanner sector = new Scanner(System.in);
                int sec = sector.nextInt();
                

                //  Send message to all subscribers
                String update = String.format(
                        "%s %s %s %d", topico, codEmpleador, codOferta, sec
                );
                publisher.send(update, 0);
            }
        }
    }
}