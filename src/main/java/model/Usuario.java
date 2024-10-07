package model;

import javax.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_usuario;

    @Column(nullable = false)
    private String nome_usuario;

    @Column(nullable = false)
    private String endereco_usuario;

    // Getters e Setters

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    public String getEndereco_usuario() {
        return endereco_usuario;
    }

    public void setEndereco_usuario(String endereco_usuario) {
        this.endereco_usuario = endereco_usuario;
    }
}
