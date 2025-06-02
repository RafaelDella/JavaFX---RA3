package com.biblioteca.model;

import java.io.Serializable;

public class LivroDigital extends Livro implements Serializable {
    private int quantidadeAlugado;

    public LivroDigital(String titulo, String autor, String genero) {
        super(titulo, autor, genero);
        this.quantidadeAlugado = 0;
    }

    @Override
    public void alugarLivro() {
        quantidadeAlugado++;
        System.out.println("Livro digital alugado com sucesso.");
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("--------------------");
        System.out.println("Título: " + getTitulo());
        System.out.println("Autor: " + getAutor());
        System.out.println("Gênero: " + getGenero());
        System.out.println("Total de vezes alugado: " + quantidadeAlugado);
    }

    public int getQuantidadeAlugado() {
        return quantidadeAlugado;
    }
}
