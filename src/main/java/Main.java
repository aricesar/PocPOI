import controller.ImportacaoController;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import util.H2Server;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Inicia o servidor TCP do H2
        H2Server.startTcpServer();

        String excelFilePath = "src/main/resources/dados_tabelao.xlsx";

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        ImportacaoController controller = new ImportacaoController(session);

        controller.importarDados(excelFilePath);

        transaction.commit();
        session.close();
        new Scanner(System.in).nextLine();
        // Para o servidor TCP do H2
        H2Server.stopTcpServer();
    }
}