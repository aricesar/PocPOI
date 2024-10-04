import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import util.H2Server;

import model.Tabelao;
import model.ControleImportacao;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class ImportarTabelao {

    public static void main(String[] args) {
        // Inicia o servidor TCP do H2
        H2Server.startTcpServer();

        String excelFilePath = "src/main/resources/dados_tabelao.xlsx";

        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Única aba
            Iterator<Row> rowIterator = sheet.iterator();

            // Ler o cabeçalho
            Row headerRow = rowIterator.hasNext() ? rowIterator.next() : null;

            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            // Verifica se o arquivo já foi importado e se teve erros
            String hql = "SELECT c.linha FROM ControleImportacao c WHERE c.nomeArquivo = :nomeArquivo";
            List<Integer> linhasComErro = session.createQuery(hql)
                    .setParameter("nomeArquivo", excelFilePath).list();

            Set<Integer> linhasErroSet = new HashSet<>(linhasComErro);

            int numeroLinha = 1; // Inicia em 1 após o cabeçalho

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                numeroLinha++;

                // Se o arquivo não teve erros anteriores, importamos todas as linhas
                // Caso contrário, importamos apenas as linhas com erro
                if (!linhasErroSet.isEmpty() && !linhasErroSet.contains(numeroLinha)) {
                    continue;
                }

                try {
                    Tabelao registro = new Tabelao();

                    registro.setAutorId(getIntegerCellValue(row.getCell(0)));
                    registro.setTipoRegistro(getStringCellValue(row.getCell(1)));
                    registro.setNomeAutor(getStringCellValue(row.getCell(2)));
                    registro.setNacionalidade(getStringCellValue(row.getCell(3)));
                    registro.setDataNascimento(getDateCellValue(row.getCell(4)));
                    registro.setNomeEditora(getStringCellValue(row.getCell(5)));
                    registro.setEnderecoEditora(getStringCellValue(row.getCell(6)));
                    registro.setTelefoneEditora(getStringCellValue(row.getCell(7)));
                    registro.setIsbn(getStringCellValue(row.getCell(8)));
                    registro.setAnoPublicacao(getIntegerCellValue(row.getCell(9)));
                    registro.setQuantidade(getIntegerCellValue(row.getCell(10)));
                    registro.setDisponivel(getBooleanCellValue(row.getCell(11)));
                    registro.setNomeUsuario(getStringCellValue(row.getCell(12)));
                    registro.setEmail(getStringCellValue(row.getCell(13)));
                    registro.setTelefoneUsuario(getStringCellValue(row.getCell(14)));
                    registro.setEnderecoUsuario(getStringCellValue(row.getCell(15)));
                    registro.setDataCadastro(getDateCellValue(row.getCell(16)));
                    registro.setDataEmprestimo(getDateCellValue(row.getCell(17)));
                    registro.setDataDevolucao(getDateCellValue(row.getCell(18)));
                    registro.setStatusEmprestimo(getStringCellValue(row.getCell(19)));

                    // Salva o registro no banco de dados
                    session.save(registro);

                    // Se a importação for bem-sucedida, remove o registro de erro, se existir
                    removerErro(session, excelFilePath, numeroLinha);

                } catch (Exception e) {
                    // Registra o erro na tabela de controle
                    registrarErro(session, excelFilePath, numeroLinha, "N/A", e.getMessage());
                    continue;
                }
            }

            transaction.commit();
            session.close();

            System.out.println("Importação concluída com sucesso!");
            new Scanner(System.in).nextLine();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Para o servidor TCP do H2
            H2Server.stopTcpServer();
        }
    }

    // Métodos auxiliares para obter os valores das células

    private static String getStringCellValue(Cell cell) {
        try {
            if (cell == null) return null;
            cell.setCellType(CellType.STRING);
            return cell.getStringCellValue().trim();
        } catch (Exception e) {
            return null;
        }
    }

    private static Integer getIntegerCellValue(Cell cell) {
        try {
            if (cell == null) return null;
            cell.setCellType(CellType.NUMERIC);
            return (int) cell.getNumericCellValue();
        } catch (Exception e) {
            return null;
        }
    }

    private static Boolean getBooleanCellValue(Cell cell) {
        try {
            if (cell == null) return null;
            if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
                return cell.getBooleanCellValue();
            } else if (cell.getCellTypeEnum() == CellType.STRING) {
                String value = cell.getStringCellValue().trim().toLowerCase();
                return parseBoolean(value);
            } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                return cell.getNumericCellValue() != 0;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private static Date getDateCellValue(Cell cell) {
        try {
            if (cell == null) return null;
            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue();
            } else {
                String dateStr = getStringCellValue(cell);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                return sdf.parse(dateStr);
            }
        } catch (Exception e) {
            return null;
        }
    }

    private static Boolean parseBoolean(String value) {
        if ("true".equals(value) || "sim".equals(value) || "yes".equals(value)) {
            return true;
        } else if ("false".equals(value) || "não".equals(value) || "no".equals(value)) {
            return false;
        }
        return null;
    }

    private static void registrarErro(Session session, String nomeArquivo, int numeroLinha, String campo, String mensagemErro) {
        ControleImportacao erro = new ControleImportacao();
        erro.setNomeArquivo(nomeArquivo);
        erro.setLinha(numeroLinha);
        erro.setCampo(campo);
        erro.setMensagemErro(mensagemErro);

        session.save(erro);
    }

    private static void removerErro(Session session, String nomeArquivo, int numeroLinha) {
        String hql = "DELETE FROM ControleImportacao c WHERE c.nomeArquivo = :nomeArquivo AND c.linha = :linha";
        session.createQuery(hql)
                .setParameter("nomeArquivo", nomeArquivo)
                .setParameter("linha", numeroLinha)
                .executeUpdate();
    }
}
