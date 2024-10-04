package util;

import org.h2.tools.Server;

public class H2Server {
    private static Server tcpServer;

    public static void startTcpServer() {
        try {
            tcpServer = Server.createTcpServer(
                    "-tcpPort", "9092",
                    "-tcpAllowOthers",
                    "-ifNotExists"
            ).start();
            System.out.println("Servidor TCP do H2 iniciado na porta 9092.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void stopTcpServer() {
        if (tcpServer != null) {
            tcpServer.stop();
            System.out.println("Servidor TCP do H2 parado.");
        }
    }
}
