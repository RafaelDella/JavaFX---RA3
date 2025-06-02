package com.biblioteca.model;

import java.io.Serial;
import java.io.Serializable;

public class Aluno implements Pessoa, Serializable {
    private String nome;
    private String cpf;
    private int livrosAlugados = 0;
    private final int limiteLivros = 3;
    private String curso;

    public Aluno(String nome, String cpf, String curso) {
        this.nome = nome;
        this.cpf = cpf;
        this.curso = curso;
    }

    @Override
    public void alugarLivro(Livro livro) {
        if (livrosAlugados < limiteLivros && livro instanceof LivroFisico livroFisico && livroFisico.getQuantidadeDisponivel() > 0) {
            livrosAlugados++;
            livroFisico.decrementarQuantidade();
            System.out.println(nome + " alugou o livro: " + livro.getTitulo());
        } else {
            System.out.println("Não foi possível alugar o livro.");
        }
    }

    @Override
    public String toString() {
        return nome; // ou: return nome + " (" + cpf + ")";
    }

    public String getNome() {
        return nome;
    }

    public String getCurso() {
        return curso;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    // Getters e setters, se necessário
}

