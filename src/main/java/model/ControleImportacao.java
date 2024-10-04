package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "controle_importacao")
public class ControleImportacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome_arquivo", nullable = false)
    private String nomeArquivo;

    @Column(nullable = false)
    private int linha;

    @Column(nullable = false)
    private String campo;

    @Column(name = "mensagem_erro", nullable = false)
    private String mensagemErro;

    @Column(name = "data_hora", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;

    // Construtor padrão
    public ControleImportacao() {
        this.dataHora = new Date(); // Define a data e hora atuais
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    // Outros getters e setters...

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }

    public Date getDataHora() {
        return dataHora;
    }

    // Não definimos um setter para dataHora, pois queremos que seja gerado automaticamente
}

