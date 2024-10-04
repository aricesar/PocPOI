package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tabelao")
public class Tabelao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Integer autorId;
    private String tipoRegistro;
    private String nomeAutor;
    private String nacionalidade;
    private Date dataNascimento;
    private String nomeEditora;
    private String enderecoEditora;
    private String telefoneEditora;
    private String isbn;
    private Integer anoPublicacao;
    private Integer quantidade;
    private Boolean disponivel; // Novo campo booleano
    private String nomeUsuario;
    private String email;
    private String telefoneUsuario;
    private String enderecoUsuario;
    private Date dataCadastro;
    private Date dataEmprestimo;
    private Date dataDevolucao;
    private String statusEmprestimo;

    public Tabelao(){}
    public Tabelao(int id, Integer autorId, String tipoRegistro, String nomeAutor, String nacionalidade, Date dataNascimento, String nomeEditora, String enderecoEditora, String telefoneEditora, String isbn, Integer anoPublicacao, Integer quantidade, Boolean disponivel, String nomeUsuario, String email, String telefoneUsuario, String enderecoUsuario, Date dataCadastro, Date dataEmprestimo, Date dataDevolucao, String statusEmprestimo) {
        this.id = id;
        this.autorId = autorId;
        this.tipoRegistro = tipoRegistro;
        this.nomeAutor = nomeAutor;
        this.nacionalidade = nacionalidade;
        this.dataNascimento = dataNascimento;
        this.nomeEditora = nomeEditora;
        this.enderecoEditora = enderecoEditora;
        this.telefoneEditora = telefoneEditora;
        this.isbn = isbn;
        this.anoPublicacao = anoPublicacao;
        this.quantidade = quantidade;
        this.disponivel = disponivel;
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.telefoneUsuario = telefoneUsuario;
        this.enderecoUsuario = enderecoUsuario;
        this.dataCadastro = dataCadastro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.statusEmprestimo = statusEmprestimo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getAutorId() {
        return autorId;
    }

    public void setAutorId(Integer autorId) {
        this.autorId = autorId;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNomeEditora() {
        return nomeEditora;
    }

    public void setNomeEditora(String nomeEditora) {
        this.nomeEditora = nomeEditora;
    }

    public String getEnderecoEditora() {
        return enderecoEditora;
    }

    public void setEnderecoEditora(String enderecoEditora) {
        this.enderecoEditora = enderecoEditora;
    }

    public String getTelefoneEditora() {
        return telefoneEditora;
    }

    public void setTelefoneEditora(String telefoneEditora) {
        this.telefoneEditora = telefoneEditora;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefoneUsuario() {
        return telefoneUsuario;
    }

    public void setTelefoneUsuario(String telefoneUsuario) {
        this.telefoneUsuario = telefoneUsuario;
    }

    public String getEnderecoUsuario() {
        return enderecoUsuario;
    }

    public void setEnderecoUsuario(String enderecoUsuario) {
        this.enderecoUsuario = enderecoUsuario;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public String getStatusEmprestimo() {
        return statusEmprestimo;
    }

    public void setStatusEmprestimo(String statusEmprestimo) {
        this.statusEmprestimo = statusEmprestimo;
    }
}
