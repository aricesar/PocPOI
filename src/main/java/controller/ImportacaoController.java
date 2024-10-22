package controller;

import dao.TabelaoDAO;
import dto.TabelaoDTO;
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

    public ImportacaoController(Session session) {
        this.session = session;
        this.tabelaoDAO = new TabelaoDAO(session);
    }

    public void importarDados(String excelFilePath) {
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Única aba
            Iterator<Row> rowIterator = sheet.iterator();

            // Ler o cabeçalho
            Row headerRow = rowIterator.hasNext() ? rowIterator.next() : null;


            int numeroLinha = 1; // Inicia em 1 após o cabeçalho

            // Aqui comeca a leitura
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                numeroLinha++;

                    // Cria o DTO a partir da linha
                    TabelaoDTO dto = criarDTOAPartirDaLinha(row);

                    // Converter o DTO para entidade
                    Tabelao registro = converterDTOParaEntidade(dto);

                    // Salva o registro no banco de dados
                    tabelaoDAO.salvar(registro);

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
        entidade.setNomeUsuario(dto.getNomeUsuario());
        entidade.setEmail(dto.getEmail());
        entidade.setTelefoneUsuario(dto.getTelefoneUsuario());
        entidade.setEnderecoUsuario(dto.getEnderecoUsuario());
        entidade.setDataCadastro(dto.getDataCadastro());
        entidade.setDataEmprestimo(dto.getDataEmprestimo());
        entidade.setDataDevolucao(dto.getDataDevolucao());
        entidade.setStatusEmprestimo(dto.getStatusEmprestimo());
        entidade.setDisponivel(dto.getDisponivel());
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
        dto.setNomeUsuario(getStringCellValue(row.getCell(11)));
        dto.setEmail(getStringCellValue(row.getCell(12)));
        dto.setTelefoneUsuario(getStringCellValue(row.getCell(13)));
        dto.setEnderecoUsuario(getStringCellValue(row.getCell(14)));
        dto.setDataCadastro(getDateCellValue(row.getCell(15)));
        dto.setDataEmprestimo(getDateCellValue(row.getCell(16)));
        dto.setDataDevolucao(getDateCellValue(row.getCell(17)));
        dto.setStatusEmprestimo(getStringCellValue(row.getCell(18)));
        dto.setDisponivel(getBooleanCellValue(row.getCell(19)));


        return dto;
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