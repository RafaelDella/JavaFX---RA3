package com.biblioteca.model;

import java.io.Serializable;

public class LivroDigital extends Livro implements Serializable {
    private int quantidadeAlugado;

    public LivroDigital(String titulo, String autor, String genero, Boolean isFisico) {
        super(titulo, autor, genero, isFisico);
        this.quantidadeAlugado = 0;
    }

    @Override
    public void alugarLivro() {
        quantidadeAlugado++;
        System.out.println("Livro digital alugado com sucesso.");
    }
}
