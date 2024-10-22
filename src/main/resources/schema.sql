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
    nome_usuario VARCHAR(255),
    email VARCHAR(255),
    telefone_usuario VARCHAR(20),
    endereco_usuario VARCHAR(255),
    data_cadastro DATE,
    data_emprestimo DATE,
    data_devolucao DATE,
    status_emprestimo VARCHAR(50),
    disponivel BOOLEAN -- Novo campo booleano
);

