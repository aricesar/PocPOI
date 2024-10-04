package controller;

import dao.ControleImportacaoDAO;
import dao.TabelaoDAO;
import dto.TabelaoDTO;
import model.ControleImportacao;
import model.Tabelao;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class ImportacaoController {

    private Session session;
    private TabelaoDAO tabelaoDAO;
    private ControleImportacaoDAO controleImportacaoDAO;

    public ImportacaoController(Session session) {
        this.session = session;
        this.tabelaoDAO = new TabelaoDAO(session);
        this.controleImportacaoDAO = new ControleImportacaoDAO(session);
    }

    public void importarDados(String excelFilePath) {
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Única aba
            Iterator<Row> rowIterator = sheet.iterator();

            // Ler o cabeçalho
            Row headerRow = rowIterator.hasNext() ? rowIterator.next() : null;

            // Verifica se o arquivo já foi importado e se teve erros
            List<Integer> linhasComErro = controleImportacaoDAO.buscarLinhasComErro(excelFilePath);
            Set<Integer> linhasErroSet = new HashSet<>(linhasComErro);

            int numeroLinha = 1; // Inicia em 1 após o cabeçalho

            // Aqui comeca a leitura
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                numeroLinha++;

                // Se o arquivo não teve erros anteriores, importamos todas as linhas
                // Caso contrário, importamos apenas as linhas com erro
                if (!linhasErroSet.isEmpty() && !linhasErroSet.contains(numeroLinha)) {
                    continue;
                }

                try {
                    // Cria o DTO a partir da linha
                    TabelaoDTO dto = criarDTOAPartirDaLinha(row);

                    // Converter o DTO para entidade
                    Tabelao registro = converterDTOParaEntidade(dto);

                    // Salva o registro no banco de dados
                    tabelaoDAO.salvar(registro);

                    // Se a importação for bem-sucedida, remove o registro de erro, se existir
                    controleImportacaoDAO.removerErro(excelFilePath, numeroLinha);

                } catch (Exception e) {
                    // Registra o erro na tabela de controle
                    registrarErro(excelFilePath, numeroLinha, "N/A", e.getMessage());
                    continue;
                }
            }


            System.out.println("Importação concluída com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Tabelao converterDTOParaEntidade(TabelaoDTO dto) {
        Tabelao entidade = new Tabelao();

        entidade.setAutorId(dto.getAutorId());
        entidade.setTipoRegistro(dto.getTipoRegistro());
        entidade.setNomeAutor(dto.getNomeAutor());
        entidade.setNacionalidade(dto.getNacionalidade());
        entidade.setDataNascimento(dto.getDataNascimento());
        entidade.setNomeEditora(dto.getNomeEditora());
        entidade.setEnderecoEditora(dto.getEnderecoEditora());
        entidade.setTelefoneEditora(dto.getTelefoneEditora());
        entidade.setIsbn(dto.getIsbn());
        entidade.setAnoPublicacao(dto.getAnoPublicacao());
        entidade.setQuantidade(dto.getQuantidade());
        entidade.setDisponivel(dto.getDisponivel());
        entidade.setNomeUsuario(dto.getNomeUsuario());
        entidade.setEmail(dto.getEmail());
        entidade.setTelefoneUsuario(dto.getTelefoneUsuario());
        entidade.setEnderecoUsuario(dto.getEnderecoUsuario());
        entidade.setDataCadastro(dto.getDataCadastro());
        entidade.setDataEmprestimo(dto.getDataEmprestimo());
        entidade.setDataDevolucao(dto.getDataDevolucao());
        entidade.setStatusEmprestimo(dto.getStatusEmprestimo());

        return entidade;
    }

    private TabelaoDTO criarDTOAPartirDaLinha(Row row) {
        TabelaoDTO dto = new TabelaoDTO();

        dto.setAutorId(getIntegerCellValue(row.getCell(0)));
        dto.setTipoRegistro(getStringCellValue(row.getCell(1)));
        dto.setNomeAutor(getStringCellValue(row.getCell(2)));
        dto.setNacionalidade(getStringCellValue(row.getCell(3)));
        dto.setDataNascimento(getDateCellValue(row.getCell(4)));
        dto.setNomeEditora(getStringCellValue(row.getCell(5)));
        dto.setEnderecoEditora(getStringCellValue(row.getCell(6)));
        dto.setTelefoneEditora(getStringCellValue(row.getCell(7)));
        dto.setIsbn(getStringCellValue(row.getCell(8)));
        dto.setAnoPublicacao(getIntegerCellValue(row.getCell(9)));
        dto.setQuantidade(getIntegerCellValue(row.getCell(10)));
        dto.setDisponivel(getBooleanCellValue(row.getCell(11)));
        dto.setNomeUsuario(getStringCellValue(row.getCell(12)));
        dto.setEmail(getStringCellValue(row.getCell(13)));
        dto.setTelefoneUsuario(getStringCellValue(row.getCell(14)));
        dto.setEnderecoUsuario(getStringCellValue(row.getCell(15)));
        dto.setDataCadastro(getDateCellValue(row.getCell(16)));
        dto.setDataEmprestimo(getDateCellValue(row.getCell(17)));
        dto.setDataDevolucao(getDateCellValue(row.getCell(18)));
        dto.setStatusEmprestimo(getStringCellValue(row.getCell(19)));

        return dto;
    }


    private Tabelao criarRegistroAPartirDaLinha(Row row) throws Exception {
        Tabelao registro = new Tabelao();

        try {
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
        } catch (Exception e) {
            throw new Exception("Erro ao criar registro: " + e.getMessage());
        }

        return registro;
    }

    private void registrarErro(String nomeArquivo, int numeroLinha, String campo, String mensagemErro) {
        ControleImportacao erro = new ControleImportacao();
        erro.setNomeArquivo(nomeArquivo);
        erro.setLinha(numeroLinha);
        erro.setCampo(campo);
        erro.setMensagemErro(mensagemErro);

        controleImportacaoDAO.salvar(erro);
    }

    // Métodos auxiliares para obter os valores das células

    private String getStringCellValue(Cell cell) {
        try {
            if (cell == null) return null;
            cell.setCellType(CellType.STRING);
            return cell.getStringCellValue().trim();
        } catch (Exception e) {
            return null;
        }
    }

    private Integer getIntegerCellValue(Cell cell) {
        try {
            if (cell == null) return null;
            cell.setCellType(CellType.NUMERIC);
            return (int) cell.getNumericCellValue();
        } catch (Exception e) {
            return null;
        }
    }

    private Boolean getBooleanCellValue(Cell cell) {
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

    private Date getDateCellValue(Cell cell) {
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

    private Boolean parseBoolean(String value) {
        if ("true".equals(value) || "sim".equals(value) || "yes".equals(value)) {
            return true;
        } else if ("false".equals(value) || "não".equals(value) || "no".equals(value)) {
            return false;
        }
        return null;
    }
}
