package model;

import javax.persistence.*;

@Entity
@Table(name = "autor")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_autor;

    @Column(nullable = false)
    private String nome_autor;

    @Column(nullable = false)
    private String endereco_autor;

    // Getters e Setters

    public int getId_autor() {
        return id_autor;
    }

    public void setId_autor(int id_autor) {
        this.id_autor = id_autor;
    }

    public String getNome_autor() {
        return nome_autor;
    }

    public void setNome_autor(String nome_autor) {
        this.nome_autor = nome_autor;
    }

    public String getEndereco_autor() {
        return endereco_autor;
    }

    public void setEndereco_autor(String endereco_autor) {
        this.endereco_autor = endereco_autor;
    }
}
