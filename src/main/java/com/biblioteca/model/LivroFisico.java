package com.biblioteca.model;

import java.io.Serializable;

public class LivroFisico extends Livro implements Serializable {
    private int quantidadeDisponivel;

    public LivroFisico(String titulo, String autor, String genero, int quantidadeDisponivel) {
        super(titulo, autor, genero);
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void decrementarQuantidade() {
        if (quantidadeDisponivel > 0) {
            quantidadeDisponivel--;
        } else {
            System.out.println("Livro indisponível para aluguel.");
        }
    }

    @Override
    public void alugarLivro() {
        if (getDisponibilidade()) {
            decrementarQuantidade();
            System.out.println("Livro alugado com sucesso.");
        } else {
            System.out.println("Livro indisponível para aluguel.");
        }
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("--------------------");
        System.out.println("Título: "  + getTitulo());
        System.out.println("Autor: "  + getAutor());
        System.out.println("Gênero: " + getGenero());
        System.out.println("Quantidade disponível: " + quantidadeDisponivel);
    }

    public boolean getDisponibilidade() {
        return quantidadeDisponivel > 0;
    }

    @Override
    public String toString() {
        return getTitulo() + " - " + getAutor() + " (" + quantidadeDisponivel + " disponíveis)";
    }
}
