package com.biblioteca.model;

import java.io.Serializable;

public class Aluguel implements Serializable {
    private static final long serialVersionUID = 1L;
    private Aluno aluno;
    private Livro livro;

    public Aluguel(Aluno aluno, Livro livro) {
        this.aluno = aluno;
        this.livro = livro;
    }

    public Aluno getAluno() { return aluno; }
    public Livro getLivro() { return livro; }
}

