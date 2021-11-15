package com.calderon_garcia_rozo.clientes;

import java.util.Random;
import java.util.Scanner;

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
            Random rn = new Random();
            String codEmpleador = "Emp" + String.valueOf(rn.nextInt(999 - 1+ 999) + 1);
            Scanner scanner = new Scanner(System.in);
           

            
            
            while (!Thread.currentThread().isInterrupted()) {
                //Preguntar si desea crear ofertas de empleo
                
                System.out.println("¿Desea crear ofertas de empleo? 1. Si - 0. No");
                int crea = scanner.nextInt();

                if(crea == 1){
                    String topico = "Oferta";
                    String codOferta = "Oferta" + String.valueOf(rn.nextInt(9999 - 1000 + 1000) + 1);
                    
    
                    System.out.println("¿A qué sector pertenece su oferta?");
                    System.out.println("1. Gerentes y directores");
                    System.out.println("2. Científicos e intelectuales");
                    System.out.println("3. Agricultores");
                    System.out.println("4. Personal administrativo");
                    System.out.println("5. Operarios y artesanos");
    
                    
                    int sec = scanner.nextInt();
                    scanner.nextLine();
    
                    System.out.println("¿Qué capacidades requiere?");
                    String cap = scanner.nextLine();
    
                    
    
                    //  Send message to all subscribers
                    String update = String.format(
                            "%s %s %s %d %s", topico, codEmpleador, codOferta, sec, cap
                    );
                    publisher.send(update, 0);
                    
                }else{
                    break;
                }
                
            }
            scanner.close();
        }
    }
}