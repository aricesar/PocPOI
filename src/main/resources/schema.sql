-- schema.sql

DROP TABLE IF EXISTS tabelao;

CREATE TABLE tabelao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    autor_id INT,
    tipo_registro VARCHAR(50),
    nome_autor VARCHAR(255),
    nacionalidade VARCHAR(100),
    data_nascimento DATE,
    nome_editora VARCHAR(255),
    endereco_editora VARCHAR(255),
    telefone_editora VARCHAR(20),
    isbn VARCHAR(20),
    ano_publicacao INT,
    quantidade INT,
    disponivel BOOLEAN, -- Novo campo booleano
    nome_usuario VARCHAR(255),
    email VARCHAR(255),
    telefone_usuario VARCHAR(20),
    endereco_usuario VARCHAR(255),
    data_cadastro DATE,
    data_emprestimo DATE,
    data_devolucao DATE,
    status_emprestimo VARCHAR(50)
);

DROP TABLE IF EXISTS controle_importacao;

CREATE TABLE controle_importacao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_arquivo VARCHAR(255) NOT NULL,
    linha INT NOT NULL,
    campo VARCHAR(100) NOT NULL,
    mensagem_erro VARCHAR(255) NOT NULL,
    data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela Autor
DROP TABLE IF EXISTS autor;

CREATE TABLE autor (
    id_autor INT AUTO_INCREMENT PRIMARY KEY,
    nome_autor VARCHAR(255) NOT NULL,
    endereco_autor VARCHAR(255) NOT NULL
);

-- Tabela Usuario
DROP TABLE IF EXISTS usuario;

CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nome_usuario VARCHAR(255) NOT NULL,
    endereco_usuario VARCHAR(255) NOT NULL
);
