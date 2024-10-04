package util;

import org.h2.tools.Server;

public class H2Console {
public static void iniciarH2Console(){
    try {
        // Inicia a console web na porta 8082
        Server server = Server.createWebServer("-webPort", "8082").start();
        System.out.println("Console web do H2 iniciada em: " + server.getURL());
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
